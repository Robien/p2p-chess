package lo23.communication.connection;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import lo23.communication.ComManager;
import lo23.communication.handle.ConnectionListener;
import lo23.communication.handle.HandleMessage;
import lo23.communication.handle.HandleReceiveUDPMessage;
import lo23.communication.handle.HandleSendMessageUDP;
import lo23.communication.handle.HandleServerConnection;
import lo23.communication.message.AnswerMsg;
import lo23.communication.message.ChatMsg;
import lo23.communication.message.ConstantMsg;
import lo23.communication.message.GameEnded;
import lo23.communication.message.GameStarted;
import lo23.communication.message.InvitMsg;
import lo23.communication.message.Message;
import lo23.communication.message.MoveMsg;
import lo23.communication.message.MulticastAnswer;
import lo23.communication.message.MulticastInvit;
import lo23.data.ApplicationModel;
import lo23.data.Constant;
import lo23.data.Invitation;
import lo23.data.Move;
import lo23.data.PublicProfile;

/**
 * This class manage the socket connections.
 */
public class ConnectionManager implements ConnectionListener {

    private ComManager comManager;
    // Multicast Socket
    private MulticastSocket multicastSocket; //server
    private HandleReceiveUDPMessage handleMulticast;
    private DatagramSocket datagramSocket; //client
    // TCP
    private ServerSocket serverSocket;
    private HandleServerConnection serverConnection;
    private ConcurrentHashMap<Socket, HandleMessage> handleMessageMap;
    // Other
    private ConcurrentHashMap<InetAddress, Socket> socketDirectory;
    private AtomicBoolean readInvitation;
    private Socket socketSession;
    private ConcurrentHashMap<Socket, Invitation> invitationMap;

    /**
     * Constructor of ConnectionManager.
     * @param comManager the comManager
     */
    public ConnectionManager(ComManager comManager) {
        this.comManager = comManager;
        handleMessageMap = new ConcurrentHashMap<Socket, HandleMessage>();
        socketDirectory = new ConcurrentHashMap<InetAddress, Socket>();
        invitationMap = new ConcurrentHashMap<Socket, Invitation>();
        readInvitation = new AtomicBoolean(true);
        socketSession = null;

        try {
            multicastSocket = new MulticastSocket(ConnectionParams.multicastPort);
            multicastSocket.joinGroup(InetAddress.getByName(ConnectionParams.multicastAddress));
            handleMulticast = new HandleReceiveUDPMessage(multicastSocket, this);
            new Thread(handleMulticast).start();
            datagramSocket = new DatagramSocket();

            serverSocket = new ServerSocket(ConnectionParams.unicastPort);
            serverConnection = new HandleServerConnection(serverSocket, this);
            new Thread(serverConnection).start();
            serverConnection.waitStarted();
        } catch (IOException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, "Error for the initialisation of the server sockets", ex);
        }
    }

    /**
     * Function which allow us to warn every players on the network that there
     * is a new connection.
     */
    public void sendMulticast() {
        if (comManager.getCurrentUserProfile() != null) {
            PublicProfile profile = comManager.getCurrentUserProfile();
            MulticastInvit message = new MulticastInvit(profile);
            HandleSendMessageUDP handler = new HandleSendMessageUDP(datagramSocket);
            handler.sendMulticast(message);
        } else {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, "Send multicast without publicProfile");
        }
    }

    /**
     * Function which answer to sendMultiCast().
     */
    private void replyMulticast(String ipAddress) {
        if ( comManager.getCurrentUserProfile() != null) {
            //FIXME mettre ici le resizedImage
            PublicProfile profile = comManager.getCurrentUserProfile();
            MulticastAnswer message = new MulticastAnswer(profile);
            HandleSendMessageUDP handler = new HandleSendMessageUDP(datagramSocket);
            handler.send(message, ipAddress);
        }
    }

    /**
     * Send a invitation to a user.
     * @param invitation the invitation from a user
     */
    public void sendInvitation(Invitation invitation) {
        PublicProfile guestProfile = invitation.getGuest();
        InvitMsg invitMsg = new InvitMsg(invitation);
        try {
            InetAddress address = InetAddress.getByName(guestProfile.getIpAddress());
            connect(address);
            Socket socket = socketDirectory.get(address);
            HandleMessage handleMessage = handleMessageMap.get(socket);
            handleMessage.send(invitMsg);
        } catch (UnknownHostException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Send a response to a invitation.
     * @param invitation the invitation from a user
     * @param answer the answer to the invitation
     */
    public void sendInvitationAnswer(Invitation invitation, boolean answer) {
        PublicProfile hostProfile = invitation.getHost();
        AnswerMsg answerMsg = new AnswerMsg(invitation, answer);
        InetAddress adress;
        try {
            adress = InetAddress.getByName(hostProfile.getIpAddress());
            Socket socket = socketDirectory.get(adress);
            HandleMessage handleMessage = handleMessageMap.get(socket);
            handleMessage.send(answerMsg);
        } catch (UnknownHostException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Start a game session with a user.
     * @param userProfile the user who will be the opponent
     */
    public void sendGameStarted(PublicProfile userProfile) {
        try {
            GameStarted message = new GameStarted(userProfile);
            InetAddress distantIpAddr = InetAddress.getByName(userProfile.getIpAddress());
            HandleMessage handleMessage = handleMessageMap.get(socketDirectory.get(distantIpAddr));
            handleMessage.send(message);

            //Ne pas oublier de fermer les autres connexions ouvertes sur l'app locale. (la méthode sendInvitationAnswer ferme deja celles ouvertess sur l'aap distante)
            readInvitation.set(false);
            socketSession = socketDirectory.get(distantIpAddr);
            disconnectOthers();
        } catch (UnknownHostException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Send a negative answer to each invitations excepted the one which has been accepted.
     */
    private void disconnectOthers() {
        //Envoie d'une reponse négative à toute les demandes précédement reçues
        HashMap<Socket, Invitation> copyInvitMap = new HashMap<Socket, Invitation>(invitationMap);
        for (Socket invitSock : invitationMap.keySet()) {
            if (!invitSock.equals(socketSession)) {
                AnswerMsg disconnectionMessage = new AnswerMsg(copyInvitMap.get(invitSock), false);
                HandleMessage handleMessageReceivedInvit = handleMessageMap.get(invitSock);
                handleMessageReceivedInvit.send(disconnectionMessage);
            }
        }
    }

    /**
     * Send a chat message. (available only when a game session is started)
     * @param message the chat message
     */
    public void sendChatMessage(lo23.data.Message message) {
        ChatMsg chatMsg = new ChatMsg(message);
        HandleMessage handleMessage = handleMessageMap.get(socketSession);
        handleMessage.send(chatMsg);
    }

    /**
     * Send a movement of a piece. (available only when a game session is
     * started)
     * @param move the movement of a piece
     */
    public void sendMovement(Move move) {
        MoveMsg moveMsg = new MoveMsg(move);
        HandleMessage handleMessage = handleMessageMap.get(socketSession);
        handleMessage.send(moveMsg);
    }

    /**
     * Send a constant message.
     * @param constant the constant
     */
    public void sendConstantMessage(Constant constant) {
        ConstantMsg constantMsg = new ConstantMsg(constant);
        HandleMessage handleMessage = handleMessageMap.get(socketSession);
        handleMessage.send(constantMsg);
    }

    /**
     * End the current game session.
     * (available only when a game session is started)
     */
    public void sendGameEnded() {
        GameEnded message = new GameEnded();
        HandleMessage handleMessage = handleMessageMap.get(socketSession);
        handleMessage.send(message);
        readInvitation.set(true);
        socketSession = null;
    }

    /**
     * Method which notifies the connection on the server socket.
     * @param socket the new client socket
     */
    @Override
    public synchronized void receivedConnection(Socket socket) {
        HandleMessage handleMessage = new HandleMessage(socket, this);
        handleMessage.startHandleReceive();

        socketDirectory.put(socket.getInetAddress(), socket);
        handleMessageMap.put(socket, handleMessage);
    }

    /**
     * Allow to connect to a distant user.
     * (Update socketDirectory and handleMessageMap)
     * @param inetAddress 
     */
    private synchronized void connect(InetAddress inetAddress) {
        try {
            Socket socket = new Socket(inetAddress, ConnectionParams.unicastPort);
            HandleMessage handleMessage = new HandleMessage(socket, this);
            handleMessage.startHandleReceive();

            socketDirectory.put(socket.getInetAddress(), socket);
            handleMessageMap.put(socket, handleMessage);
        } catch (IOException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, "Error on connection", ex);
        }
    }

    /**
     * Method which notifies the closure of a socket.
     * @param socket the closed socket
     */
    @Override
    public synchronized void closedConnection(Socket socket) {
        handleMessageMap.get(socket).closeHandle();
        if (!socket.isClosed()) {
            try {
                socket.close();
            } catch (IOException ex) {
                Logger.getLogger(ConnectionManager.class.getName()).log(Level.INFO, "Socket close", ex);
            }
        }
        socketDirectory.remove(socket.getInetAddress());
        handleMessageMap.remove(socket);

        //On met a jour le socket lié a la session active
        if (socket.equals(socketSession)) {
            readInvitation.set(true);
            socketSession = null;
        }
    }

    /**
     * Allow to disconnect from a socket.
     * @param socket the socket will be closed
     */
    private void disconnect(Socket socket) {
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.WARNING, "Error on disconnection", ex);
        }
    }

    /**
     * Method which be notified when we receive a message
     * @param socket the socket which sended the message
     * @param message the received message
     */
    @Override
    public synchronized void receivedMessage(Socket socket, final Message message) {
        if (message instanceof InvitMsg) {
            InvitMsg invitMsg = (InvitMsg) message;
            if (readInvitation.get()) {
                invitationMap.put(socket, invitMsg.getInvitation());
                notifyMessage(message);
            } else {
                AnswerMsg answerMsg = new AnswerMsg(invitMsg.getInvitation(), false);
                HandleMessage handleMessage = handleMessageMap.get(socket);
                handleMessage.send(answerMsg);
            }

        } else if (message instanceof AnswerMsg) {
            if (!((AnswerMsg) message).isAnswer()) {
                disconnect(socket);
            }
            notifyMessage(message);

        } else if (message instanceof GameStarted) {
            readInvitation.set(false);
            socketSession = socket;
            disconnectOthers();
            notifyMessage(message);

        } else if (message instanceof ChatMsg) {
            notifyMessage(message);

        } else if (message instanceof MoveMsg) {
            notifyMessage(message);

        } else if (message instanceof ConstantMsg) {
            notifyMessage(message);

        } else if (message instanceof GameEnded) {
            disconnect(socketSession);
            notifyMessage(message);
            
        }
    }

    /**
     * Function which manage received message.
     * @param message message received
     */
    @Override
    public synchronized void receivedUDPMessage(Message message) {
        if (message instanceof MulticastInvit) {
            String ipAddress = ((MulticastInvit) message).getProfile().getIpAddress();
            if ( comManager.getCurrentUserProfile() != null &&
                !ipAddress.equals(comManager.getCurrentUserProfile().getIpAddress())) {
                replyMulticast(ipAddress);
            }    
        } else if (message instanceof MulticastAnswer) {
            notifyMessage(message);
        }
    }

    /**
     * Notify messages to the Model in the swing thread.
     * SwingUtilities.invokeLater
     * @param message message received
     */
    private void notifyMessage(Message message) {
        SwingUtilities.invokeLater(new NotifyMessageLater(message));
    }

    /**
     * Class which allow to notify a message later.
     */
    private class NotifyMessageLater implements Runnable {

        private Message message;

        public NotifyMessageLater(Message message) {
            this.message = message;
        }

        @Override
        public void run() {
            ApplicationModel model = comManager.getApplicationModel();
            if (message instanceof InvitMsg) {
                model.getPManager().notifyInvitation(((InvitMsg) message).getInvitation());
            } else if (message instanceof AnswerMsg) {
                model.getPManager().notifyInvitAnswer(((AnswerMsg) message).getInvitation(), ((AnswerMsg) message).isAnswer());
            } else if (message instanceof GameStarted) {
                model.getGManager().notifyGameStarted(((GameStarted) message).getGuest());
            } else if (message instanceof ChatMsg) {
                model.getGManager().notifyChatMessage(((ChatMsg) message).getMessage());
            } else if (message instanceof MoveMsg) {
                model.getGManager().notifyMovement(((MoveMsg) message).getMove());
            } else if (message instanceof ConstantMsg) {
                model.getGManager().notifyConstantMessage(((ConstantMsg) message).getConstant());
            } else if (message instanceof GameEnded) {
                model.getGManager().notifyGameEnded();
            } else if (message instanceof MulticastAnswer) {
                model.getPManager().notifyAddProfile(((MulticastAnswer) message).getGuest());
            }
        }
   }
    
}

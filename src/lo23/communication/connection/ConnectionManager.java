package lo23.communication.connection;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import lo23.communication.ComManager;
import lo23.communication.handle.ConnectionListener;
import lo23.communication.handle.HandleMessage;
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
    private DatagramSocket datagramSocket; //client
    
    // TCP
    private ServerSocket serverSocket;
    private HandleServerConnection serverConnection;
    private HashMap<Socket, HandleMessage> handleMessageMap;

    // Other
    private HashMap<InetAddress, Socket> socketDirectory;
    private AtomicBoolean readInvitation;
    private Socket socketSession;
    private HashMap<Socket, Invitation> invitationMap;

    /**
     * Constructor of ConnectionManager.
     * @param comManager the comManager
     */
    public ConnectionManager(ComManager comManager) {
        this.comManager = comManager;
        handleMessageMap = new HashMap<Socket, HandleMessage>();
        readInvitation = new AtomicBoolean(true);
        socketSession = null;

        try {
            multicastSocket = new MulticastSocket(ConnectionParams.multicastPort);
            multicastSocket.joinGroup(InetAddress.getByName(ConnectionParams.multicastAddress));
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
        PublicProfile profile = this.comManager.getCurrentUserProfile();
        // Message creation
        MulticastInvit message = new MulticastInvit(profile);
        HandleSendMessageUDP handler = new HandleSendMessageUDP(this.datagramSocket);
        handler.send(message);
    }

    public void replyMulticast() {
       PublicProfile profile = this.comManager.getCurrentUserProfile();
       MulticastAnswer message = new MulticastAnswer(profile);
       HandleSendMessageUDP handler = new HandleSendMessageUDP(this.datagramSocket);
       handler.send(message);
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

    public void sendGameEnded() {
        GameEnded message = new GameEnded();
        HandleMessage handleMessage = handleMessageMap.get(socketSession);
        handleMessage.send(message);
        readInvitation.set(true);
        socketSession = null;
    }

    /**
     * Mettre un commentaire.
     * @param socket socket
     */
    @Override
    public synchronized void receivedConnection(Socket socket) {
        HandleMessage handleMessage = new HandleMessage(socket, this);
        handleMessage.startHandleReceive();
        
        socketDirectory.put(socket.getInetAddress(), socket);
        handleMessageMap.put(socket, handleMessage);
    }
    
    /**
     * Mettre un commentaire.
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
     * Mettre un commentaire.
     * @param socket socket
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
     * Mettre un commentaire.
     * @param socket 
     */
    private void disconnect(Socket socket) {
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.WARNING, "Error on disconnection", ex);
        }
    }

    /**
     * Mettre un commentaire.
     * @param socket
     * @param message
     */
    @Override
    public synchronized void receivedMessage(Socket socket, final Message message) {
         if (message instanceof InvitMsg) {
            InvitMsg invitMsg = (InvitMsg) message;
            //On stock les invitations reçus afin de pouvoir les libérer quand on lancera la partie
            invitationMap.put(socket, invitMsg.getInvitation());
            notifyMessage(message);
            //TODO réponse automatique si readInvitation = false;
            
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
     * Mettre un commentaire.
     * @param socket
     * @param message 
     */
    @Override
    public synchronized void receivedUDPMessage(Message message) {
        if (message instanceof MulticastInvit){
            this.replyMulticast();
        }
        else if (message instanceof MulticastAnswer){
            this.comManager.getApplicationModel().getPManager().notifyAddProfile(((MulticastAnswer)message).getGuest());
        }
    }

    /**
     * Mettre un commentaire.
     * @param message 
     */
    private void notifyMessage(Message message) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
    }
    
}

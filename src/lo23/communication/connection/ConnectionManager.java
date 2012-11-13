package lo23.communication.connection;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import lo23.communication.message.MulticastInvit;
import lo23.data.Constant;
import lo23.data.Invitation;
import lo23.data.Move;
import lo23.data.NewInvitation;
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
    private Socket socketSession;
    private HashMap<Socket, Invitation> invitationMap;
    
    
    /**
     * Constructor of ConnectionManager.
     * @param comManager the comManager
     */
    public ConnectionManager(ComManager comManager) {
        this.comManager = comManager;
        handleMessageMap = new HashMap<Socket, HandleMessage>();
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
     * Function which allow us to warn every players on the network that there is a new connection.
     */
    public void sendMulticast() {
       PublicProfile profile = this.comManager.getCurrentUserProfile();
       // Message creation
       MulticastInvit message = new MulticastInvit(profile);
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
            InetAddress adress = InetAddress.getByName(guestProfile.getIpAddress());
            connect(adress);
            Socket socket = socketDirectory.get(adress);
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
    public void sendInvitationAnswer(Invitation invitation, boolean answer){
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
    
    public void sendGameStarted(PublicProfile userProfile){
        try {
            GameStarted message = new GameStarted(userProfile);
            InetAddress distantIpAddr = InetAddress.getByName(userProfile.getIpAddress());
            HandleMessage handleMessage = handleMessageMap.get(socketDirectory.get(distantIpAddr));
            handleMessage.send(message);
            
            //Ne pas oublier de fermer les autres connexions ouvertes sur l'app locale. (la méthode sendInvitationAnswer ferme deja celles ouvertess sur l'aap distante)
            socketSession = socketDirectory.get(distantIpAddr);
            
            for(Invitation invit : invitationMap.values()){
                AnswerMsg disconnectionMessage = new AnswerMsg(invit,false);
                //envoyer a tout le monde en prenant les IP dans les invit

                HashMap<InetAddress, Socket> copyOfServerSocketDirectory = new HashMap<InetAddress, Socket>();
                copyOfServerSocketDirectory = socketDirectory; // copy necessaire pour ne pas modifier l'element sur lequel on travaille
                for (Socket socket : copyOfServerSocketDirectory.values()){
                    if(!socket.getInetAddress().equals(distantIpAddr)) {
                        disconnect(socket);
                    }
                }
            }
            
        } catch (UnknownHostException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    /**
     * Send a chat message.
     * (available only when a game session is started)
     * @param message the chat message
     */
    public void sendChatMessage(lo23.data.Message message){
        ChatMsg chatMsg = new ChatMsg(message);
        HandleMessage handleMessage = handleMessageMap.get(socketSession);
        handleMessage.send(chatMsg);
        // TODO receive
    }
    
    /**
     * Send a movement of a piece.
     * (available only when a game session is started)
     * @param move the movement of a piece
     */
    public void sendMovement(Move move){
       MoveMsg moveMsg = new MoveMsg(move);
       HandleMessage handleMessage = handleMessageMap.get(socketSession);
       handleMessage.send(moveMsg);
       // TODO receive
    }
    
    /**
     * Send a constant message.
     * @param constant the constant
     */
    public void sendConstantMessage(Constant constant){
        ConstantMsg constantMsg = new ConstantMsg(constant);
        HandleMessage handleMessage = handleMessageMap.get(socketSession);
        handleMessage.send(constantMsg);
        // TODO receive
    }

    
    public void sendGameEnded(){
        GameEnded message = new GameEnded();
        HandleMessage handleMessage = handleMessageMap.get(socketSession);
        handleMessage.send(message);
 
        socketSession = null;
    }
    

    /**
     * Ne pas toucher ici.
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
     * Ne pas toucher ici.
     * @param socket socket
     * Gestion des erreurs réseaux
     */
    @Override
    public synchronized void closedConnection(Socket socket) {
        handleMessageMap.get(socket).closeHandle();
        
        if(!socket.isClosed()) {
            try {
                socket.close();
            } catch (IOException ex) {
                Logger.getLogger(ConnectionManager.class.getName()).log(Level.INFO, "Socket close", ex);
            }
        }
        
        socketDirectory.remove(socket.getInetAddress());
        handleMessageMap.remove(socket);
        
        //On met a jour le socket lié a la session active
        if(socket.equals(socketSession)) {
            socketSession = null;
        }
    }
    
    /**
     * Mettre un commentaire.
     * @param socket
     * @param message
     */
    @Override
    public synchronized void receivedMessage(Socket socket, Message message) {
        //Mini Exemple : répondre à la réception d'un message
        // handleMessageMap.get(socket).send(new Message());
        
        //TODO Attention il manque la fonction notifyInvitAnswer() elle a été remplacer par notifyGameStarted(invitation)
        
        if (message instanceof GameStarted) {
            try {
                this.comManager.getApplicationModel().getGManager().notifyGameStarted(((GameStarted)message).getGuest());
                InetAddress distantServerIpAddr = InetAddress.getByName(((GameStarted)message).getGuest().getIpAddress());
                //penser a mettre a jour l'app distante -> socketSession...
                
                //copy necessaire pour ne pas modifier l'element sur lequel on travaille
                HashMap<InetAddress, Socket> copyOfClientSocketDirectory = new HashMap<InetAddress, Socket>(socketDirectory);
                for (Socket tmpSocket : copyOfClientSocketDirectory.values()){
                if(tmpSocket.getInetAddress().equals(distantServerIpAddr)) {
                    socketSession = tmpSocket;
                }
            }
                
            } catch (UnknownHostException ex) {
                Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, "Error during GameStarted Sent", ex);
            }
        }
        else if (message instanceof InvitMsg) {
            try {
                this.comManager.getApplicationModel().getPManager().notifyInvitation(((InvitMsg)message).getInvitation());
                InetAddress distantServerIpAddr = InetAddress.getByName(((InvitMsg)message).getInvitation().getHost().getIpAddress());
                Socket invitSock = null;
                
                 //copy necessaire pour ne pas modifier l'element sur lequel on travaille
                HashMap<InetAddress, Socket> copyOfClientSocketDirectory = new HashMap<InetAddress, Socket>(socketDirectory);
                for (Socket tmpSocket : copyOfClientSocketDirectory.values()){
                    if(tmpSocket.getInetAddress().equals(distantServerIpAddr)) {
                        invitSock = tmpSocket;
                    }
                }
                
                //On stock les invitations reçus afin de pouvoir les libérer quand on lancera la partie
                invitationMap.put(invitSock, ((InvitMsg)message).getInvitation());
            } catch (UnknownHostException ex) {
                Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if (message instanceof AnswerMsg) {
            this.comManager.getApplicationModel().getPManager().notifyInvitAnswer(((AnswerMsg)message).getInvitation(), ((AnswerMsg)message).isAnswer());
                
        }
        else if (message instanceof GameEnded) {
            disconnect(socketSession);                
        }
        
    }
    
    @Override
    public synchronized void receivedUDPMessage(DatagramSocket socket, Message message) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

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
    
    private synchronized void disconnect(Socket socket) {
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.WARNING, "Error on disconnection", ex);
        }
    }
}

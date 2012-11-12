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
import lo23.communication.handle.HandleServerConnection;
import lo23.communication.message.Message;
import lo23.communication.message.MulticastInvit;
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
    
    
    /**
     * Constructor of ConnectionManager.
     * @param comManager the comManager
     */
    public ConnectionManager(ComManager comManager) {
        this.comManager = comManager;
        handleMessageMap = new HashMap<Socket, HandleMessage>();
        
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
     * This function manage messages serialization.
     * @param message to serialize
     * @return : byte[] of the message
     */

    private byte[] serialize(Message message){
       ByteArrayOutputStream b_out = new ByteArrayOutputStream();
       ObjectOutputStream o_out = null;
       try {
           o_out = new ObjectOutputStream(b_out);
       } catch (IOException ex) {
           Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
       }

       try {
           // Message serialization
           o_out.writeObject(message);
       } catch (IOException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, "Error during the message serialization", ex);
       }
       byte[] b = b_out.toByteArray();

       return b;
    }

    /**
     * Function which allow us to warn every players on the network that there is a new connection.
     */
    public void sendMulticast() {
       PublicProfile Profile = this.comManager.getCurrentUserProfile();
       // Message creation
       MulticastInvit Message = new MulticastInvit(Profile);
       byte b[] = this.serialize(Message);
       DatagramPacket dgram = null;
       try {
           dgram = new DatagramPacket(b, b.length, InetAddress.getByName(ConnectionParams.multicastAddress), ConnectionParams.multicastPort); // multicast
       } catch (UnknownHostException ex) {
       Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, "Error during the datagramme creation", ex);
       }

       try {
           this.datagramSocket.send(dgram);
       } catch (IOException ex) {
           Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, "Error: Cannot sent datagramme on Multicast server", ex);
       }
    }

    /**
     * Ne pas toucher ici.
     * @param socket socket
     */
    @Override
    public void receivedConnection(Socket socket) {
        HandleMessage handleMessage = new HandleMessage(socket, this);
        handleMessage.startHandleReceive();
        
        socketDirectory.put(socket.getInetAddress(), socket);
        handleMessageMap.put(socket, handleMessage);
    }

    /**
     * Ne pas toucher ici.
     * @param socket socket
     */
    @Override
    public void closedConnection(Socket socket) {
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
    }
    
    /**
     * Mettre un commentaire.
     * @param socket
     * @param message
     */
    @Override
    public void receivedMessage(Socket socket, Message message) {
        //Mini Exemple : répondre à la réception d'un message
        // handleMessageMap.get(socket).send(new Message());
        
    }
    
    @Override
    public void receivedUDPMessage(DatagramSocket socket, Message message) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void connect(InetAddress inetAddress) {
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
    
    private void disconnect(Socket socket) {
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.WARNING, "Error on disconnection", ex);
        }
    }
}

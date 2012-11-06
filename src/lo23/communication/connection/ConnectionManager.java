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
import lo23.communication.handle.HandleMessage;
import lo23.communication.handle.HandleServerConnection;
import lo23.communication.handle.ReceivedConnectionListener;
import lo23.communication.handle.ReceivedMessageListener;
import lo23.communication.message.Message;
import lo23.communication.message.MulticastInvit;
import lo23.data.PublicProfile;

/**
 * This class manage the socket connections.
 */
public class ConnectionManager implements ReceivedConnectionListener, ReceivedMessageListener {

    private ComManager comManager;
    
    // Multicast
    private MulticastSocket multicastSocket;
    private DatagramSocket datagramSocket;
    
    // Server TCP
    private ServerSocket serverSocket;
    private HandleServerConnection serverConnection;
    private HashMap<Socket, HandleMessage> serverHandleMessageMap;
   
    // Client TCP
    private HashMap<Socket, HandleMessage> clientHandleMessageMap;

    // Other
    // mettre les autres variables ici
    
    
    /**
     * Constructor of ConnectionManager.
     * @param comManager the comManager
     */
    public ConnectionManager(ComManager comManager) {
        this.comManager = comManager;
        serverHandleMessageMap = new HashMap<Socket, HandleMessage>();
        clientHandleMessageMap = new HashMap<Socket, HandleMessage>();
        
        try {
            multicastSocket = new MulticastSocket(ConnectionParams.multicastPort);
            multicastSocket.joinGroup(InetAddress.getByName(ConnectionParams.multicastAddress));
            datagramSocket = new DatagramSocket();

            serverSocket = new ServerSocket(ConnectionParams.unicastPort);
            serverConnection = new HandleServerConnection(serverSocket, this);

        } catch (IOException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, "Error for the initialisation of the server sockets", ex);
        }
    }

    /**
     * Mettre un commentaire.
     */
    public void sendMulticast() {
       // Send Datagramme
       ByteArrayOutputStream b_out = new ByteArrayOutputStream();
       ObjectOutputStream o_out = null;
       try {
           o_out = new ObjectOutputStream(b_out);
       } catch (IOException ex) {
           Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
       }
       PublicProfile Profile = this.comManager.getCurrentUserProfile();
       // Message creation
       MulticastInvit Message = new MulticastInvit(Profile);
       try {
           // Message serialization
           o_out.writeObject(Message);
       } catch (IOException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, "Error during the message serialization", ex);
       }
       byte[] b = b_out.toByteArray();
       // Datagramme Creation
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
     * Mettre un commentaire.
     * @param socket
     * @param message
     */
    @Override
    public void receivedMessage(Socket socket, Message message) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Mettre un commentaire.
     * @param clientSocket
     */
    @Override
    public void receivedConnection(Socket clientSocket) {
        HandleMessage handleMessage = new HandleMessage(clientSocket, this);
        serverHandleMessageMap.put(clientSocket, handleMessage);
    }
}

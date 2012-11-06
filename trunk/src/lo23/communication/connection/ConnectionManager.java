package lo23.communication.connection;

import java.io.ByteArrayOutputStream;
import java.net.SocketException;
import java.net.UnknownHostException;
import lo23.data.PublicProfile;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import lo23.communication.ComManager;
import lo23.communication.message.MulticastInvit;
import lo23.communication.handle.HandleReceiveMessage;
import lo23.communication.handle.HandleSendMessage;

/**
 * This class manage the socket connections.
 */
public class ConnectionManager {

    private ComManager comManager;
    
    private MulticastSocket multicastSocket;
    private DatagramSocket datagramSocket;
    
    private ServerSocket serverSocket;
    private HandleSendMessage serverSendMessage;
    private HandleReceiveMessage serverReceiveMessage;
    
    private Socket clientSocket;
    
    public ConnectionManager(ComManager comManager) throws SocketException {
        this.comManager = comManager;
        
        try {
            multicastSocket = new MulticastSocket(ConnectionParams.multicastPort);
            multicastSocket.joinGroup(InetAddress.getByName(ConnectionParams.multicastAddress));
            datagramSocket = new DatagramSocket();
            serverSocket = new ServerSocket(ConnectionParams.unicastPort); 
            
        } catch (IOException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, "Error for the initialisation of the server sockets", ex);
        }
    }
    
    public void sendMulticast() {

        // Send Datagramme
        try {
            this.datagramSocket = new DatagramSocket();
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
        } catch (SocketException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


}

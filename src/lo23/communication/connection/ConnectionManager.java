package lo23.communication.connection;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import lo23.communication.ComManager;
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
    
    public ConnectionManager(ComManager comManager) {
        this.comManager = comManager;
        
        try {
            multicastSocket = new MulticastSocket(ConnectionParams.multicastPort);
            multicastSocket.joinGroup(InetAddress.getByName(ConnectionParams.multicastAddress));
            
            serverSocket = new ServerSocket(ConnectionParams.unicastPort); 
            
        } catch (IOException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, "Error for the initialisation of the server sockets", ex);
        }
    }
    
    public void sendMulticast() {
       // DatagramSocket = new
         //       datagramSocket.send
    }
    
}

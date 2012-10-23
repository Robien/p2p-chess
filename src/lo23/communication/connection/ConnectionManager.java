package lo23.communication.connection;

import java.io.IOException;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;
import lo23.communication.ComManager;

public class ConnectionManager {

    private ComManager comManager;
    private MulticastSocket multicastSocket;
    private ServerSocket serverSocket;
    
    public ConnectionManager(ComManager comManager) {
        this.comManager = comManager;
        
        try {
            multicastSocket = new MulticastSocket(ConnectionParams.multicastPort);
            multicastSocket.joinGroup(InetAddress.getByName(ConnectionParams.multicastAddress));
            // TODO ajouter les handles puis continuer ici
        } catch (IOException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void sendMulticast() {
       // DatagramSocket = new
         //       datagramSocket.send
    }
    
}

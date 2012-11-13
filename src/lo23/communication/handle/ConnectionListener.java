package lo23.communication.handle;

import java.net.DatagramSocket;
import java.net.Socket;
import lo23.communication.message.Message;

/**
 * Interface which notifies the ConnectionManager of
 * the network event (connection and reception of a message).
 */
public interface ConnectionListener {
    
    /**
     * Method which notifies the connection on the server socket.
     * @param socket the new client socket
     */
    public void receivedConnection(Socket socket);
    
    /**
     * Method which notifies the closure of a socket.
     * @param socket the closed socket
     */
    public void closedConnection(Socket socket);
    
    /**
     * Method which notifies the reception of a message.
     * @param socket the socket which receives the message
     * @param message the message
     */
    public void receivedMessage(Socket socket, Message message);
    
    /**
     * Method which notifies the reception of a UDP message.
     * @param socket the socket which receives the message
     * @param message the message
     */
    public void receivedUDPMessage(DatagramSocket socket, Message message);
}
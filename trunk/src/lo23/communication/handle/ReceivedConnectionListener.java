package lo23.communication.handle;

import java.net.Socket;

/**
 * Interface which allows to notify the connection on the server socket.
 * (Use by HandleServerConnection)
 */
public interface ReceivedConnectionListener {
    
    /**
     * Method which notifies the connection on the server socket.
     * @param socket the new client socket
     */
    public void receivedConnection(Socket clientSocket);
    
}

package lo23.communication.handle;

import java.net.Socket;
import lo23.communication.message.Message;

/**
 * Interface which allows to notify the reception of a message.
 * (Use by HandleReceiveMessage)
 */
public interface ReceivedMessageListener {
    
    /**
     * Method which notifies the reception of a message.
     * @param socket the socket which receives the message
     * @param message the message
     */
    public void receivedMessage(Socket socket, Message message);
    
}

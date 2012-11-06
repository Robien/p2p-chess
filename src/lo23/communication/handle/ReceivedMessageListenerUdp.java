package lo23.communication.handle;

import java.net.DatagramSocket;
import lo23.communication.message.Message;

/**
 * Interface which allows to notify the reception of a message.
 * (Use by HandleReceiveMessage)
 */
public interface ReceivedMessageListenerUdp {

    /**
     * Method which notifies the reception of a message.
     * @param socket the socket which receives the message
     * @param message the message
     */
    public void receivedMessage(DatagramSocket socket, Message message);


}

package lo23.communication.handle;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import lo23.communication.message.Message;

/**
 * Handle to send a message on a TCP Socket.
 */
public class HandleSendMessage {

    private ObjectOutputStream objectOutput;

    /**
     * Constructor of HandleSendMessage
     * @param socket the socket
     */
    public HandleSendMessage(Socket socket) {
        try {
            objectOutput = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Send a message.
     * @param message the message
     */
    public void send(Message message) {
        try {
            objectOutput.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Close the handle.
     */
    public void closeHandle() {
        try {
            objectOutput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

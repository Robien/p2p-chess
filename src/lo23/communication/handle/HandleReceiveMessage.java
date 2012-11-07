package lo23.communication.handle;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import lo23.communication.message.Message;

/**
 * This class handle the reception of a message for Socket. (TCP Socket)
 */
public class HandleReceiveMessage extends HandleRunnable {

    private Socket socket;
    private ObjectInputStream objectInput;
    private ConnectionListener connListener;

    /**
     * Constructor of HandleReceiveMessage.
     * @param socket the socket concerned
     * @param listener the Listener which will be notified
     */
    public HandleReceiveMessage(Socket socket, ConnectionListener connListener) {
        this.socket = socket;
        this.connListener = connListener;
    }

    /**
     * This method is launched when the Thread is started.
     */
    @Override
    public void run() {
        try {
            objectInput = new ObjectInputStream(socket.getInputStream());
            notifyStart();
            
            while (getStart()) {
                Message message = (Message) objectInput.readObject();
                connListener.receivedMessage(socket, message);
            }

        } catch (Exception e) {
            // TODO mieux gerer les erreurs
            connListener.closedConnection(socket);
            //Logger.getLogger(HandleReceiveMessage.class.getName()).log(Level.SEVERE, "Error for the reception of a message", e);
        } finally {
            stopHandle();
            try {
                objectInput.close();
            } catch (IOException ex) {
                Logger.getLogger(HandleReceiveMessage.class.getName()).log(Level.SEVERE, "Error for closing the Handle", ex);
            }
        }
    }
    
}

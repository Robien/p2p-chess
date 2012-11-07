package lo23.communication.handle;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import lo23.communication.message.ConnectionMessage;

/**
 * This class handle the reception of a message for Socket. (UDP Socket)
 */
public class HandleReceiveUDPMessage extends HandleRunnable {

    private DatagramSocket socket;
    private ObjectInputStream objectInput;
    private ConnectionListener connListener;

    /**
     * Constructor of HandleReceiveMessage.
     *
     * @param socket the socket concerned
     * @param listener the Listener which will be notified
     */
    public void HandleReceiveMessage(DatagramSocket socket, ConnectionListener connListener) {
        this.socket = socket;
        this.connListener = connListener;
    }

    /**
     * This method is launched when the Runnable is started.
     */
    @Override
    public void run() {
        try {
            byte[] b = new byte[65535];
            ByteArrayInputStream b_in = new ByteArrayInputStream(b);
            notifyStart();
            while (getStart()) {
                DatagramPacket dgram = new DatagramPacket(b, b.length);
                this.socket.receive(dgram); // blocks
                ObjectInputStream o_in = new ObjectInputStream(b_in);
                ConnectionMessage message = (ConnectionMessage) o_in.readObject();
                dgram.setLength(b.length); // must reset length field!
                b_in.reset(); // reset so next read is from start of byte[] again
                connListener.receivedUDPMessage(socket, message);
            }
        } catch (SocketException se) {
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                objectInput.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
}

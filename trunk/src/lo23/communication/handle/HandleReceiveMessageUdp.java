package lo23.communication.handle;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.concurrent.atomic.AtomicBoolean;
import lo23.communication.message.ConnectionMessage;

/**
 * This class handle the reception of a message for Socket.
 * (UDP Socket)
 */
public class HandleReceiveMessageUdp implements Runnable {

    private DatagramSocket socket;
    private AtomicBoolean started;
    private ObjectInputStream objectInput;
    private ReceivedMessageListenerUdp listener;

    /**
     * Constructor of  HandleReceiveMessage.
     * @param socket the socket concerned
     * @param listener the Listener which will be notified
     */
    public void HandleReceiveMessage(DatagramSocket socket, ReceivedMessageListenerUdp listener) {
        this.socket = socket;
        this.listener = listener;
        this.started = new AtomicBoolean(false);
    }

    /**
     * This method is launched when the Runnable is started.
     */
    @Override
    public void run() {
        try {
            byte[] b = new byte[65535];
            ByteArrayInputStream b_in = new ByteArrayInputStream(b);

            synchronized (started) {
                started.set(true);
                started.notify();
            }
            while (true) {
                DatagramPacket dgram = new DatagramPacket(b, b.length);
                this.socket.receive(dgram); // blocks
                ObjectInputStream o_in = new ObjectInputStream(b_in);
                ConnectionMessage message = (ConnectionMessage)o_in.readObject();
                dgram.setLength(b.length); // must reset length field!
                b_in.reset(); // reset so next read is from start of byte[] again
                listener.receivedMessage(socket, message);
            }
        } catch (SocketException se) {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Close the Handle.
     * (launch this method before closing the socket)
     */
    public void closeHandle() {
        try {
            objectInput.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * When this method is launched, the current thread is waiting for
     * the start of the Runnable (HandleReceiveMessage).
     */
    public void waitStarted() {
        synchronized (started) {
            while (!started.get()) {
                try {
                    started.wait(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}


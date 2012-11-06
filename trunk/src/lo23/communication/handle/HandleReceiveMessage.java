package lo23.communication.handle;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.atomic.AtomicBoolean;
import lo23.communication.message.Message;

/**
 * This class handle the reception of a message for Socket.
 * (TCP Socket)
 */
public class HandleReceiveMessage implements Runnable {

    private Socket socket;
    private AtomicBoolean started;
    private ObjectInputStream objectInput;
    private ReceivedMessageListener listener;

    /**
     * Constructor of  HandleReceiveMessage.
     * @param socket the socket concerned
     * @param listener the Listener which will be notified
     */
    public HandleReceiveMessage(Socket socket, ReceivedMessageListener listener) {
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
            objectInput = new ObjectInputStream(socket.getInputStream());
            synchronized (started) {
                started.set(true);
                started.notify();
            }
            while (true) {
                Message message = (Message) objectInput.readObject();
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

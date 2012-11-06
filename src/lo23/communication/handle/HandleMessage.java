/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lo23.communication.handle;

import java.net.Socket;

/**
 * Class which contains the handle for send and receive a message.
 * HandleReceiveMessage and HandleSendMessage
 */
public class HandleMessage {
    
    private HandleReceiveMessage handleReceiveMessage;
    private HandleSendMessage handleSendMessage;
    
    public HandleMessage (Socket socket, ReceivedMessageListener listener) {
        handleReceiveMessage = new HandleReceiveMessage(socket, listener);
        handleSendMessage = new HandleSendMessage(socket);
    }

    public HandleReceiveMessage getHandleReceiveMessage() {
        return handleReceiveMessage;
    }

    public void setHandleReceiveMessage(HandleReceiveMessage handleReceiveMessage) {
        this.handleReceiveMessage = handleReceiveMessage;
    }

    public HandleSendMessage getHandleSendMessage() {
        return handleSendMessage;
    }

    public void setHandleSendMessage(HandleSendMessage handleSendMessage) {
        this.handleSendMessage = handleSendMessage;
    }
    
}

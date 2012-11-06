/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lo23.communication.message;

/**
 *
 * @author penotvin
 */
public class MessageMsg extends GameMessage {

    private Message message;

    public MessageMsg(Message msg){
        this.message = msg;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }
}

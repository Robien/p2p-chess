package lo23.communication.message;

/**
 * This message is sent while someone chat with someone else.
 */
public class MessageMsg extends GameMessage {

    private Message message;

    /**
    * Contructor of the MessageMsg.
    */
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

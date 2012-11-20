package lo23.data;

import lo23.utils.Enums;
import lo23.utils.Enums.CONSTANT_TYPE;

/**
 * This class is used for communication between two players.
 * Messages in this class are all messages that aren't Message or Move objects
 */
public class Constant extends Event
{
    private Enums.CONSTANT_TYPE constant;
    private Player receiver;
    private Player sender;
    
    /**
     * Default constructor
     * 
     * @param constant The concerned constant
     * @param receiver The receiver
     * @param sender The sender (simply...)
     */
    public Constant(Enums.CONSTANT_TYPE constant, Player receiver, Player sender)
    {
        this.constant = constant;
        this.receiver = receiver;
        this.sender = sender;
    }

    public CONSTANT_TYPE getConstant() {
        return constant;
    }

    public Player getReceiver() {
        return receiver;
    }

    public Player getSender() {
        return sender;
    }
}

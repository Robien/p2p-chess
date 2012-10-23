package lo23.communication.message;

import lo23.data.Invitation;

public class AnswerMsg {

    private Invitation invitation;
    private boolean answer;

    /**
     * Contructor of the AnswerMsg.
     * @param invitation the invitation received by the current user which refers to a new game or a loaded game.
     * @param answer the answer provided by the current user. 
     */
    public AnswerMsg(Invitation invitation, boolean answer) {
        this.invitation = invitation;
        this.answer = answer;
    }
}

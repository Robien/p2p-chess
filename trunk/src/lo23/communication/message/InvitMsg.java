package lo23.communication.message;

import lo23.data.Invitation;

public class InvitMsg extends ConnectionMessage {

    private Invitation invitation;

    /**
     * Contructor of the InvitMsg.
     * @param invitation the invitation sent by the current user which refers to a new game or a loaded game.
     */
    public InvitMsg(Invitation invitation) {
        this.invitation = invitation;
    }
}

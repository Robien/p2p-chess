/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lo23.ui.login.mockManager;
import lo23.communication.ISender;
import lo23.data.Constant;
import lo23.data.Invitation;
import lo23.data.Message;
import lo23.data.Move;
import lo23.data.PublicProfile;

/**
 *
 * @author lo23a009
 */
public class CommManagerMock extends Manager implements ISender{

    public CommManagerMock(ApplicationModel model){
        super(model);
    }

    public void sendMulticast() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void sendInvitation(Invitation invitation) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void sendInvitationAnswer(Invitation invitation, boolean answer) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void sendGameStarted(PublicProfile userProfile) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void sendChatMessage(Message message) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void sendMovement(Move move) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void sendConstantMessage(Constant constant) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void sendGameEnded() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
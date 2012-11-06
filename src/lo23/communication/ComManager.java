package lo23.communication;

import lo23.communication.connection.ConnectionManager;
import lo23.data.Constant;
import lo23.data.Invitation;
import lo23.data.Message;
import lo23.data.Move;
import lo23.data.PublicProfile;

/**
 * This is the main class of the communication module.
 */
public class ComManager implements ISender {

    // TODO faire les classes Messages
    // TODO finir les Handle
    
    private PublicProfile currentUserProfile;
    private IReceiveListener receiveListener;
    private ConnectionManager connectionManager;
    
    /**
     * Contructor of the ComManager.
     * @param profile the profile of the current user
     * @param receiveListener the listener who will be notified
     */
    public void ComManager(PublicProfile profile, IReceiveListener receiveListener) {
        this.currentUserProfile = profile;
        this.receiveListener = receiveListener;
        this.connectionManager = new ConnectionManager(this);
    }
    
    public PublicProfile getCurrentUserProfile() {
        return currentUserProfile;
    }
    
    public IReceiveListener getReceiveListener() {
        return receiveListener;
    }
    
    @Override
    public void sendMulticast() {
       // connectionManager.sendMulticast();
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void sendInvitation(Invitation invitation) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void sendInvitationAnswer(Invitation invitation, boolean answer) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void sendGameStarted(PublicProfile userProfile) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void sendChatMessage(Message message) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void sendMovement(Move move) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void sendConstantMessage(Constant constant) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void sendGameEnded() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}

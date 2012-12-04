package lo23.communication;

import java.awt.Image;
import javax.swing.ImageIcon;
import lo23.communication.connection.ConnectionManager;
import lo23.data.ApplicationModel;
import lo23.data.Constant;
import lo23.data.Invitation;
import lo23.data.Message;
import lo23.data.Move;
import lo23.data.PublicProfile;
import lo23.data.managers.Manager;

/**
 * This is the main class of the communication module.
 */
public class ComManager extends Manager implements ISender {

    private ConnectionManager connectionManager;
    private ApplicationModel applicationModel;
    
    /**
     * Contructor of the ComManager.
     * @param profile the profile of the current user
     * @param receiveListener the listener who will be notified
     */
    public ComManager(ApplicationModel applicationModel) throws Exception {
        super(applicationModel);
        this.applicationModel = applicationModel;
        this.connectionManager = new ConnectionManager(this);
    }
    
    public PublicProfile getCurrentUserProfile() {
        return applicationModel.getPManager().getCurrentProfile().getPublicProfile();
    }
    
    @Override
    public void sendMulticast() {
       connectionManager.sendMulticast();
    }

    @Override
    public void sendInvitation(Invitation invitation) {
        connectionManager.sendInvitation(invitation);
    }

    @Override
    public void sendInvitationAnswer(Invitation invitation, boolean answer) {
        connectionManager.sendInvitationAnswer(invitation, answer);
    }

    @Override
    public void sendGameStarted(PublicProfile userProfile) {
        connectionManager.sendGameStarted(userProfile);
    }

    @Override
    public void sendChatMessage(Message message) {
        connectionManager.sendChatMessage(message);
    }

    @Override
    public void sendMovement(Move move) {
        connectionManager.sendMovement(move);
    }

    @Override
    public void sendConstantMessage(Constant constant) {
        connectionManager.sendConstantMessage(constant);
    }
    
    @Override
    public void sendGameEnded() {
        connectionManager.sendGameEnded();
    }
    
}

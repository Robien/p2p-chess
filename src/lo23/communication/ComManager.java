package lo23.communication;

import lo23.communication.connection.ConnectionManager;
import lo23.data.ApplicationModel;
import lo23.data.Constant;
import lo23.data.Invitation;
import lo23.data.Message;
import lo23.data.Move;
import lo23.data.PublicProfile;
import lo23.data.managers.Manager;
import lo23.ui.login.mockManager.*;
import lo23.utils.Enums.STATUS;

/**
 * This is the main class of the communication module.
 */
public class ComManager extends Manager implements ISender {

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
    public ComManager(PublicProfile profile, ApplicationModel applicationModel) {
        super(applicationModel);
        this.currentUserProfile = profile;
        this.connectionManager = new ConnectionManager(this);
    }

    public static void main(String args[]){

        //Instantiate DataManager
        PublicProfile profile = new PublicProfile("1", "penotvin", STATUS.INGAME, "172.22.2.3", null,"PENOT","Vincent",22);
        ApplicationModel appModel = new ApplicationModel();
        appModel.setComManager(new ComManager(profile,appModel));
        appModel.setGameManager(new GameManagerMock((appModel)));
        appModel.setProfileManager(new ProfileManagerMock(appModel));
    }
    
    public PublicProfile getCurrentUserProfile() {
        return currentUserProfile;
    }
    
    public IReceiveListener getReceiveListener() {
        return receiveListener;
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

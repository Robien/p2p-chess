/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lo23.ui.login.mockManager;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Collection;
import lo23.data.ApplicationModel;
import lo23.data.Invitation;
import lo23.data.Profile;
import lo23.data.PublicProfile;
import lo23.data.managers.Manager;
import lo23.data.managers.ProfileManagerInterface;
import lo23.utils.Enums.STATUS;

/**
 *
 * @author lo23a009
 */
public class ProfileManagerMock extends Manager implements ProfileManagerInterface{

    public ProfileManagerMock(ApplicationModel model){
        super(model);
    }

    public Profile createProfile(String profileId, String pseudo, String password, STATUS status, String ipAddress, Image avatar, String name, String firstName, int age) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    public PublicProfile getPublicProfile(String profileId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public boolean login(String pseudo, String password) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void saveProfile(String profileId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Profile loadProfile(String profileId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void receiveInvitation(Invitation invitation) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void sendInvitation(Invitation invitation) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ArrayList<Profile> getProfilesList() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}

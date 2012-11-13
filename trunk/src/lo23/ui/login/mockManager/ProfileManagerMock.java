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
import lo23.utils.Enums;
import lo23.utils.Enums.COLOR;
import lo23.utils.Enums.STATUS;

/**
 *
 * @author lo23a009
 */
public class ProfileManagerMock extends Manager implements ProfileManagerInterface{

    private Profile profileAdmin;
    private Profile currProfil = null;
    private ArrayList<Profile> profiles = new ArrayList<Profile>();
    
    public ProfileManagerMock(ApplicationModel model){
        super(model);
        profileAdmin = new Profile("1234","admin","admin",Enums.STATUS.CONNECTED,"127.0.0.1",null,"Admin","Admin",25);
        profiles.add(profileAdmin);
        profiles.add(new Profile("1234","john","john",Enums.STATUS.CONNECTED,"127.0.0.1",null,"John","Smith",23));
        
    }

    public Profile createProfile(String profileId, String pseudo, String password, STATUS status, String ipAddress, Image avatar, String name, String firstName, int age) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    public PublicProfile getPublicProfile(String profileId) {
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

    public ArrayList<PublicProfile> createProfilesList() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void saveProfile() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void notifyInvitation(Invitation invitation) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Invitation createInvitation(PublicProfile guest, COLOR color, long duration) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void notifyAddProfile(PublicProfile publicProfile) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void notifyInvitAnswer(Invitation invitation, boolean answer) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void exportProfile(String filePath) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void importProfile(String filePath) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Profile getCurrentProfile(){
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList<PublicProfile> getLocalPublicProfiles() {
        ArrayList<PublicProfile> profilesPublic = new ArrayList<PublicProfile>();
        for(Profile p : profiles){
            profilesPublic.add(p.getPublicProfile());
        }
        return profilesPublic;
    }

    @Override
    public boolean connection(String profileId, String password){
        for(Profile p : profiles){
            if(p.getProfileId().equals(profileId)){
                currProfil = p;
                break;
            }
        }
        if(currProfil != null)
            if(currProfil.getPassword().equals(password))
                return true;
        return false;
    }

}

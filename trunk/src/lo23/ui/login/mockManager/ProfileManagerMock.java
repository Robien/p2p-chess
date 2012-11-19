/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lo23.ui.login.mockManager;
import java.util.ArrayList;
import javax.swing.ImageIcon;
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
        char[] passwordAdmin = "admin".toCharArray();
        profileAdmin = new Profile("1","admin",passwordAdmin,Enums.STATUS.CONNECTED,"127.0.0.1",null,"Admin","Admin",25);

        profiles.add(profileAdmin);

        profiles.add(new Profile("2","john","john".toCharArray(),Enums.STATUS.OFFLINE,"127.0.0.1",null,"Smith","John",23));
        profiles.add(new Profile("3","dave","dave".toCharArray(),Enums.STATUS.CONNECTED,"127.0.0.1",null,"Dave","Dave",50));
        profiles.add(new Profile("4","johnny","johnny".toCharArray(),Enums.STATUS.CONNECTED,"127.0.0.1",null,"Halliday","Johnny",80));
        profiles.add(new Profile("5","mick","mick".toCharArray(),Enums.STATUS.OFFLINE,"127.0.0.1",null,"Mouse","Mickey",100));

    }

    @Override
    public Profile createProfile(String profileId, String pseudo, char[] password, STATUS status, String ipAddress, ImageIcon avatar, String name, String firstName, int age) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    public PublicProfile getPublicProfile(String profileId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    public void saveProfile(String profileId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Profile loadProfile(String profileId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void receiveInvitation(Invitation invitation) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void sendInvitation(Invitation invitation) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ArrayList<Profile> getProfilesList() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ArrayList<PublicProfile> createProfilesList() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void saveProfile() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void notifyInvitation(Invitation invitation) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Invitation createInvitation(PublicProfile guest, COLOR color, long duration) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void notifyAddProfile(PublicProfile publicProfile) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void notifyInvitAnswer(Invitation invitation, boolean answer) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void exportProfile(String filePath) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void importProfile(String filePath) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Profile getCurrentProfile(){
        return currProfil;
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
    public boolean connection(String profileId, char[] password){
        for(Profile p : profiles){
            if(p.getProfileId().equals(profileId)){
                currProfil = p;
                break;
            }
        }
        if(currProfil != null) {
            System.out.println(new String(currProfil.getPassword()));
            System.out.println(new String(password));
            if((new String(currProfil.getPassword())).equals(new String(password))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void startProfilesDiscovery() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}

package lo23.data.managers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import lo23.data.ApplicationModel;
import lo23.data.Invitation;
import lo23.data.NewInvitation;
import lo23.data.Profile;
import lo23.data.PublicProfile;
import lo23.data.exceptions.*;
import lo23.data.serializer.Constants;
import lo23.data.serializer.Serializer;
import lo23.ui.login.IhmLoginModel;
import lo23.utils.Configuration;
import lo23.utils.Enums.COLOR;
import lo23.utils.Enums.STATUS;
import org.apache.commons.io.FileUtils;

/**
 * Implementation of the PublicManagerInterface interface.
 *
 * TODO : implémenter notifyInvitAnswer.
 * TODO : absorption des exceptions du serializer ?
 *
 */
public class ProfileManager extends Manager implements ProfileManagerInterface {

    private Profile currentProfile;
    private Timer timer;

    public ProfileManager(ApplicationModel app) {
        super(app);
        this.currentProfile = null;
    }

    @Override
    public Profile getCurrentProfile() {
        return currentProfile;
    }

    @Override
    public Profile createProfile(String profileId, String pseudo, char[] password, STATUS status, String ipAddress, ImageIcon avatar, String name, String firstName, int age) {
        Profile p = new Profile(profileId, pseudo, password, status, ipAddress, avatar, name, firstName, age);
        try {
            Serializer.saveProfile(p);
        } catch (NoIdException ex) {
            Logger.getLogger(ProfileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return p;
    }

    @Override
    public void startProfilesDiscovery() {
        // internal class needed to use timer.schedule()
        class Discoverer extends TimerTask {

            private ApplicationModel am;

            Discoverer(ApplicationModel am) {
                this.am = am;
            }

            @Override
            public void run() {
                this.am.getComManager().sendMulticast();
            }
        }

        this.timer = new Timer();
        this.timer.schedule(new Discoverer(this.getApplicationModel()), 0, Configuration.PROFILES_DISCOVERY_REFRESH_RATE);
    }

    @Override
    public boolean connection(String profileId, char[] password) {
        try {
            Profile p = Serializer.readProfile(profileId);
            if (p == null) {
                return false;
            } else {
                if (!Arrays.equals(p.getPassword(), password)) {
                    return false;
                } else {
                    this.currentProfile = p;
                    return true;
                }
            }
        } catch (FileNotFoundException ex) {
            return false;
        }
    }

    @Override
    public ArrayList<PublicProfile> getLocalPublicProfiles() {
        ArrayList<PublicProfile> publicProfiles = new ArrayList<PublicProfile>();
        try {
            for (String name : Serializer.getProfileIds()) {
                publicProfiles.add(Serializer.readProfile(name).getPublicProfile());
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ProfileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return publicProfiles;
    }

    @Override
    public void saveProfile() {
        try {
            Serializer.saveProfile(this.currentProfile);
        } catch (NoIdException ex) {
            Logger.getLogger(ProfileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Profile loadProfile(String profileId) {
        try {
            Profile p = Serializer.readProfile(profileId);
            if (p != null) {
                this.currentProfile = p;
            } else {
                System.out.println("Serializer a absorbé une exception concernant le chargement du profil.");
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ProfileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.currentProfile;
    }

    @Override
    public void sendInvitation(Invitation invitation) {
        getApplicationModel().getComManager().sendInvitation(invitation);
    }

    @Override
    public void notifyInvitation(Invitation invitation) {
        publish(IhmLoginModel.INVIT_RECEIVE, invitation);
    }

    @Override
    public Invitation createInvitation(PublicProfile guest, COLOR color, long duration) {
        return new NewInvitation(color, duration, this.getCurrentProfile().getPublicProfile(), guest);
    }

    @Override
    public void notifyAddProfile(PublicProfile publicProfile) {
        publish(IhmLoginModel.ADD_PLAYER_CONNECTED, publicProfile);
    }

    @Override
    public void notifyInvitAnswer(Invitation invitation, boolean answer) {
        publish(IhmLoginModel.REQUEST_GAME_RESPONSE, invitation, answer);
    }

    @Override
    public void exportProfile(String filePath) {
        try {
            Serializer.saveProfile(this.getCurrentProfile(), filePath);
        } catch (NoIdException ex) {
            Logger.getLogger(ProfileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void importProfile(String filePath) throws FileNotFoundException, ProfileIdAlreadyExistException, ProfilePseudoAlreadyExistException, IOException {
        Profile p = Serializer.readProfile2(filePath);

        ArrayList<String> profileIdsInDirectory = Serializer.getProfileIds();
        String profileId = p.getProfileId();
        if (profileIdsInDirectory.contains(profileId)) {
            throw new ProfileIdAlreadyExistException("A profile with ID " + profileId + "already exists !");
        }

        String pseudo = p.getPseudo();
        if (isPseudoAlreadyExist(pseudo)) {
            throw new ProfilePseudoAlreadyExistException("A profile with pseudo " + pseudo + "already exists !");
        }

        FileUtils.copyFile(new File(filePath), new File(Constants.PROFILES_PATH + p.getProfileId() + Constants.PROFILE_SUFFIXE));
    }

    private boolean isPseudoAlreadyExist(String pseudo) {
        ArrayList<PublicProfile> publicProfiles = this.getLocalPublicProfiles();

        for (PublicProfile publicProfile : publicProfiles) {
            if (publicProfile.toString().equals(pseudo)) {
                return true;
            }
        }
        return false;
    }
}

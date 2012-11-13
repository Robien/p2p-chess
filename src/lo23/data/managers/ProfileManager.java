package lo23.data.managers;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import lo23.data.ApplicationModel;
import lo23.data.Invitation;
import lo23.data.Profile;
import lo23.data.PublicProfile;
import lo23.data.exceptions.FileNotFoundException;
import lo23.data.exceptions.NoIdException;
import lo23.data.serializer.Serializer;
import lo23.utils.Enums.COLOR;
import lo23.utils.Enums.STATUS;
import lo23.utils.Configuration;
import java.lang.Thread;
import java.util.TimerTask;

/**
 * Implementation of the PublicManagerInterface interface
 * @author Pierre-Alexandre FONTA et Louis PONTOISE
 */
public class ProfileManager extends Manager implements ProfileManagerInterface {

    private Profile currentProfile;
    private Timer timer;

    public ProfileManager(ApplicationModel app) {
        super(app);
        this.currentProfile = null;
    }

    public Profile getCurrentProfile() {
        return currentProfile;
    }

    @Override
    public Profile createProfile(String profileId, String pseudo, String password, STATUS status, String ipAddress, Image avatar, String name, String firstName, int age) {
        return new Profile(profileId, pseudo, password, status, ipAddress, avatar, name, firstName, age);
    }

    @Override
    public void startProfilesDiscovery() {
        // internal class needed to use timer.schedule()
        class Discoverer extends TimerTask {
            private ApplicationModel am;

            Discoverer (ApplicationModel am) {
                this.am = am;
            }

            public void run() {
                this.am.getComManager().sendMulticast();
            }
        }
        
        this.timer = new Timer();
        this.timer.schedule(new Discoverer(this.getApplicationModel()), 0, Configuration.PROFILES_DISCOVERY_REFRESH_RATE);
    }

    @Override
    public boolean connection(String profileId, String password) {
        try {
            Profile p = Serializer.readProfile(profileId);
            if (p == null) {
                return false;
            } else {
                if (!p.getPassword().equals(password)) {
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
            this.currentProfile = Serializer.readProfile(profileId);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ProfileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return this.currentProfile;
    }

    @Override
    public void sendInvitation(Invitation invitation) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void notifyAddUser(PublicProfile userProfile) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void notifyInvitation(Invitation invitation) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void notifyInvitationAnswer(Invitation invitation, boolean answer) {
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

    public ArrayList<PublicProfile> getLocalPublicProfiles() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

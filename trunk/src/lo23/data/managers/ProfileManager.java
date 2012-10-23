package lo23.data.managers;

import java.awt.Image;
import java.util.Collection;
import lo23.data.Invitation;
import lo23.data.Profile;
import lo23.data.PublicProfile;
import lo23.utils.Enums.STATUS;

/**
 * Implementation of the PublicManagerInterface interface
 * @author Pierre-Alexandre FONTA et Louis PONTOISE
 */
public class ProfileManager implements ProfileManagerInterface {

    private Profile currentProfile;
    private Collection<Profile> profiles;

    @Override
    public Profile createProfile(String profileId, String pseudo, String password, STATUS status, String ipAddress, Image avatar, String name, String firstName, int age) {
        return new Profile(profileId, pseudo, password, status, ipAddress, avatar, name, firstName, age);
    }

    @Override
    public Collection<Profile> getProfilesList() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public PublicProfile getPublicProfile(String profileId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean login(String pseudo, String password) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void saveProfile(String profileId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Profile loadProfile(String profileId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void receiveInvitation(Invitation invitation) {
        throw new UnsupportedOperationException("Not supported yet.");
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
}

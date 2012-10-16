/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lo23.data;

import java.util.Collection;

/**
 *
 * @author Pierre-Alexandre FONTA et Louis PONTOISE
 */
class ProfileManager implements ProfileManagerInterface {

    private Profile currentProfile;
    private Collection<Profile> profiles;

    public void createProfile(String pseudo) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Profile createProfile(String pseudo, String password) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Collection<Profile> getProfilesList() {
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
}

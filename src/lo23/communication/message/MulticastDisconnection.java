/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lo23.communication.message;

import lo23.data.PublicProfile;

/**
 *
 * @author Esteban
 */
public class MulticastDisconnection extends ConnectionMessage{
    private PublicProfile Profile;

    /**
     * Contructor of the MulticastInvit.
     */
    public MulticastDisconnection(PublicProfile Profile){
        this.Profile = Profile;
    }

    public void setProfile(PublicProfile Profile) {
        this.Profile = Profile;
    }
    
    public PublicProfile getProfile() {
        return Profile;
    }
}

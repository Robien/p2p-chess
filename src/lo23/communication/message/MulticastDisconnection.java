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
    
    private PublicProfile profile;

    /**
     * Constructor of the MulticastDisconnection.
     */
    public MulticastDisconnection(PublicProfile Profile){
        this.profile = Profile;
    }

    public void setProfile(PublicProfile Profile) {
        this.profile = Profile;
    }
    
    public PublicProfile getProfile() {
        return profile;
    }
}

package lo23.communication.message;

import lo23.data.PublicProfile;

/**
 * This message is sent while multicasting.
 */
public class MulticastInvit extends ConnectionMessage{
    
    private PublicProfile Profile;

    /**
     * Constructor of the MulticastInvit.
     */
    public MulticastInvit(PublicProfile Profile){
        this.Profile = Profile;
    }

    public void setProfile(PublicProfile Profile) {
        this.Profile = Profile;
    }
    
    public PublicProfile getProfile() {
        return Profile;
    }
}

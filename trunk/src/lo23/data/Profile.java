package lo23.data;

import java.awt.Image;
import java.io.Serializable;


/**
 * The profile class
 * 
 * This class is almost empty for now, as it was created just in order
 * to perform some test
 */
public class Profile implements Serializable
{
    // Enumeration
    public enum STATUS {OFFLINE, CONNECTED, INGAME};
    
    // Attributes
    private String profileId;
    private String pseudo;
    private String password;
    private STATUS status;
    private String ipAddress;
    private Image avatar;
    
    
    // Constructor
    public Profile(String profileId, String pseudo, String password, STATUS status, String ipAddress, Image avatar)
    {
        this.profileId = profileId;
        this.pseudo = pseudo;
        this.password = password;
        this.status = status;
        this.ipAddress = ipAddress;
        this.avatar = avatar;
    }
    
    
    // Methods
    public String getProfileId()
    {
        return profileId;
    }
}

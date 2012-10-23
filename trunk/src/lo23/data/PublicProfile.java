package lo23.data;

import java.awt.Image;
import java.io.Serializable;
import java.util.ArrayList;
import lo23.utils.Enums.STATUS;

/**
 * PublicProfile is a copy of a Profile without the password attribute. Instances of PublicProfiles SHOULD BE generated using the Profile.getPublicProfile() method.
 * @author Pierre-Alexandre FONTA et Louis PONTOISE
 */
public class PublicProfile implements Serializable {

    private String profileId;
    private String pseudo;
    private STATUS status;
    private String ipAddress;
    private Image avatar;
    private String name;
    private String firstName;
    private int age;
    private ArrayList<Invitation> invitations;
    private ArrayList<Player> players;

    /**
     * Constructor for the PublicProfile class. This constructor SHOULD NOT be called directly. Instead the Profile.getPublicProfile() method SHOULD be used.
     * @param profileId the unique profile identifier
     * @param pseudo the player's nickname
     * @param status the player's status
     * @param ipAddress the player's IP address
     * @param avatar the player's avatar (an image)
     * @param name the player's lastname
     * @param firstName the player's firstname
     * @param age the player's age
     */
    public PublicProfile(String profileId, String pseudo, STATUS status, String ipAddress, Image avatar, String name, String firstName, int age) {
        this.profileId = profileId;
        this.pseudo = pseudo;
        this.status = status;
        this.ipAddress = ipAddress;
        this.avatar = avatar;
        this.name = name;
        this.firstName = firstName;
        this.age = age;
        this.invitations = new ArrayList<Invitation>();
        this.players = new ArrayList<Player>();
    }

    /**
     * Getter for the player's age
     * @return the player's age
     */
    public int getAge() {
        return age;
    }

    /**
     * Setter for the player's age
     * @param age the player's new age
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Getter for the player's avatar
     * @return the player's avatar
     */
    public Image getAvatar() {
        return avatar;
    }

    /**
     * Setter for the player's avatar
     * @param avatar the new player's avatar
     */
    public void setAvatar(Image avatar) {
        this.avatar = avatar;
    }

    /**
     * Getter for the player's firstname
     * @return the player's firstname
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Setter for the player's firstname
     * @param firstName the player's new firstname
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Getter for the player's invitations
     * @return the player's invitations
     */
    public ArrayList<Invitation> getInvitations() {
        return invitations;
    }

    /**
     * Getter for the player's IP address
     * @return the player's IP address
     */
    public String getIpAddress() {
        return ipAddress;
    }

    /**
     * Setter for the player's IP address
     * @param ipAddress the player's new IP address
     */
    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * Getter for the player's lastname
     * @return the player's lastname
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the player's lastname
     * @param name the player's new lastname
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for the player's players list
     * @return the player's players list
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * Getter for the player's profile ID
     * @return the player's profile ID
     */
    public String getProfileId() {
        return profileId;
    }

    /**
     * Setter for the player's unique profile ID
     * @param profileId the player's new unique profile ID
     */
    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    /**
     * Getter for the player's nickname
     * @return the player's nickname
     */
    public String getPseudo() {
        return pseudo;
    }

    /**
     * Setter for the player's nickname
     * @param pseudo the player's new nickname
     */
    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    /**
     * Getter for the player's status
     * @return the player's status
     */
    public STATUS getStatus() {
        return status;
    }

    /**
     * Setter for the player's status
     * @param status the player's new status
     */
    public void setStatus(STATUS status) {
        this.status = status;
    }
}

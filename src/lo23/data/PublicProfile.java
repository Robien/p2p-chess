package lo23.data;

import java.awt.Image;
import java.io.Serializable;

/**
 * The profile class
 *
 * This class is almost empty for now, as it was created just in order
 * to perform some test
 */
public class PublicProfile implements Serializable {

    public enum STATUS {

        OFFLINE, CONNECTED, INGAME
    };
    private String profileId;
    private String pseudo;
    private STATUS status;
    private String ipAddress;
    private Image avatar;
    private String name;
    private String firstName;
    private int age;

    public PublicProfile(String profileId, String pseudo, STATUS status, String ipAddress, Image avatar, String name, String firstName, int age) {
        this.profileId = profileId;
        this.pseudo = pseudo;
        this.status = status;
        this.ipAddress = ipAddress;
        this.avatar = avatar;
        this.name = name;
        this.firstName = firstName;
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Image getAvatar() {
        return avatar;
    }

    public void setAvatar(Image avatar) {
        this.avatar = avatar;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public STATUS getStatus() {
        return status;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }
}
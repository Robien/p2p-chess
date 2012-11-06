package lo23.data.managers;

import java.awt.Image;
import java.util.ArrayList;
import lo23.data.Invitation;
import lo23.data.Profile;
import lo23.data.PublicProfile;
import lo23.utils.Enums.COLOR;
import lo23.utils.Enums.STATUS;

/**
 * Interface for the ProfileManager class. This interface specialize in Profile management operations.
 * @author Pierre-Alexandre FONTA et Louis PONTOISE
 */
public interface ProfileManagerInterface {

    /**
     * Generates a Profile with all the given information
     * @param profileId the profile's unique identifier {@link Profile#getProfileId}
     * @param pseudo the profile's pseudo
     * @param password the profile's password
     * @param status the profile's status
     * @param ipAddress the profile's IP address
     * @param avatar the profile's avatar
     * @param name the profile's last name
     * @param firstName the profile's first name
     * @param age the profile's age
     * @return the new Profile instance
     */
    public Profile createProfile(String profileId, String pseudo, String password, STATUS status, String ipAddress, Image avatar, String name, String firstName, int age);

    /**
     * Getter for the profiles list
     * @return the profiles list
     */
    public ArrayList<PublicProfile> createProfilesList();

    /**
     *
     * @param pseudo
     * @param password
     * @return
     */
    public boolean connection(String pseudo, String password);

    /**
     *
     */
    public void saveProfile();

    /**
     *
     * @param profileId
     * @return
     */
    public Profile loadProfile(String profileId);

    /**
     *
     * @param invitation
     */
    public void notifyInvitation(Invitation invitation);

    /**
     *
     * Utiliser à la place les setteurs de la classe Profile.
     * @param guest 
     * @param color 
     * @param duration
     */
    public Invitation createInvitation(PublicProfile guest, COLOR color, long duration);

    /**
     *
     * @param invitation
     */
    public void sendInvitation(Invitation invitation);

    /**
     *
     * @param publicProfile
     */
    public void notifyAddProfile(PublicProfile publicProfile);

    /**
     *
     * @param invitation
     * @param answer
     */
    public void notifyInvitAnswer(Invitation invitation, boolean answer);

    /**
     *
     * @param filePath
     */
    public void exportProfile(String filePath);

    /**
     *
     * @param filePath
     */
    public void importProfile(String filePath);
}

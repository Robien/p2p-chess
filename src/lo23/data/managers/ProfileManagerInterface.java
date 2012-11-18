package lo23.data.managers;

import lo23.data.exceptions.FileNotFoundException;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import lo23.data.Invitation;
import lo23.data.Profile;
import lo23.data.PublicProfile;
import lo23.utils.Enums.COLOR;
import lo23.utils.Enums.STATUS;

/**
 * Interface for the ProfileManager class. This interface specialize in Profile
 * management operations.
 */
public interface ProfileManagerInterface {

    public Profile getCurrentProfile();

    /**
     * Generates a Profile with all the given information
     *
     * @param profileId the profile's unique identifier
     * {@link Profile#getProfileId}
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
    public Profile createProfile(String profileId, String pseudo, char[] password, STATUS status, String ipAddress, ImageIcon avatar, String name, String firstName, int age);

    /**
     * Start a periodic timer which discovers Profiles on the network every
     * {@link Configuration#PROFILES_DISCOVERY_REFRESH_RATE} milliseconds
     */
    public void startProfilesDiscovery();

    /**
     * Try to log the user with the given profileId/password
     *
     * @param profileId the profile's unique identifier
     * {@link Profile#getProfileId}
     * @param password the profile's password
     * @return true if the connection succeeded
     */
    public boolean connection(String profileId, char[] password);

    /**
     * Get PublicProfiles for the local Profiles
     *
     * @return the local PublicProfiles list
     */
    public ArrayList<PublicProfile> getLocalPublicProfiles();

    /**
     * Save the current profile on the local computer
     */
    public void saveProfile();

    /**
     * Load a local profile from the local computer
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
     * Export the current Profile in a local file
     *
     * @param filePath desired path for the local export file
     */
    public void exportProfile(String filePath);

    /**
     * Import a Profile from a local file
     *
     * @param filePath path to the local file
     */
    public void importProfile(String filePath);
}

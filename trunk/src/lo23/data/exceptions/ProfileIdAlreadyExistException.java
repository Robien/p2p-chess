package lo23.data.exceptions;

import lo23.data.Profile;

public class ProfileIdAlreadyExistException extends Exception
{
    private Profile profile = null;

    public ProfileIdAlreadyExistException()
    {
        super();
    }

    public ProfileIdAlreadyExistException(String message)
    {
        super(message);
    }

    public ProfileIdAlreadyExistException(String message, Profile profile)
    {
        super(message);
        this.profile = profile;
    }

    /**
     * @return the profile with the existing profileID or null if not specified
     */
    public Profile getProfileWithExistingID() {
        return profile;
    }
}

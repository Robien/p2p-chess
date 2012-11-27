package lo23.data.exceptions;

import lo23.data.Profile;

public class ProfilePseudoAlreadyExistException extends Exception
{
    private Profile profile = null;

    public ProfilePseudoAlreadyExistException()
    {
        super();
    }

    public ProfilePseudoAlreadyExistException(String message)
    {
        super(message);
    }

    public ProfilePseudoAlreadyExistException(String message, Profile profile)
    {
        super(message);
        this.profile = profile;
    }

    /**
     * @return the profile with the existing pseudo or null if not specified
     */
    public Profile getProfileWithExistingPseudo() {
        return profile;
    }
}

package lo23.data.serializer;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import lo23.data.Profile;


/**
 * This is the util class used for every serialization
 * This class has to contain static methods and attributes only
 */
public class Serializer
{
    /**
     * This method serializes a given Profile object to the correct path
     * 
     * @param profile The object to serialize
     * 
     * @throws IOException This exception is thrown in case of problem while writing the object to a file
     * @throws NoIdException  This exception is thrown if profile argument doesn't have a correct profileId
     */
    static public void saveProfile(Profile profile) throws IOException, NoIdException
    {
        // Checks the profileId attribute validity
        if(profile.getProfileId() == null || profile.getProfileId().equals(""))
        {
            throw new NoIdException("The object you're trying to serialize handle a null or empty profileId attribute.");
        }
        
        try
        {
            ObjectOutputStream out;
            out = new ObjectOutputStream(new FileOutputStream(Constants.PROFILES_PATH + profile.getProfileId() + ".profile"));
            out.writeObject(profile);
            out.close();
        }
        catch(IOException expt)
        {
            throw expt;
        }
    }
}

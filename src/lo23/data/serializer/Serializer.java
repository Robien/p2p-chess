package lo23.data.serializer;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import lo23.data.Profile;
import lo23.data.exceptions.NoIdException;
import lo23.data.exceptions.FileNotFoundException;


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
     * @throws NoIdException  This exception is thrown if profile argument doesn't have a correct profileId
     */
    static public void saveProfile(Profile profile) throws NoIdException
    {
        // Checks the profileId attribute validity
        if(profile.getProfileId() == null || profile.getProfileId().equals(""))
        {
            throw new NoIdException("The object you're trying to serialize handle a null or empty profileId attribute.");
        }
        
        try
        {
            ObjectOutputStream out;
            out = new ObjectOutputStream(new FileOutputStream(Constants.PROFILES_PATH + profile.getProfileId() + Constants.PROFILE_SUFFIXE));
            out.writeObject(profile);
            out.close();
        }
        catch(IOException expt)
        {
            System.out.println(expt.getMessage());
            System.out.println(expt.getStackTrace());
        }
    }
    
    
    /**
     * This method tries to read a profile whom id is given as a paramater
     * 
     * @param profileId The profileId for the expected profile
     * 
     * @return Either a Profil object, either a null value if something went wrong (IOException or file not found)
     * 
     * @throws FileNotFoundException This exception is thrown when this method can't have access to an expected file
     */
    static public Profile readProfile(String profileId) throws FileNotFoundException
    {
        // Checks if the profileId associated file exists
        
        File profileFile = new File(Constants.PROFILES_PATH + profileId + Constants.PROFILE_SUFFIXE);
        if(profileFile.exists())
        {
            try
            {
                ObjectInputStream in = new ObjectInputStream(new FileInputStream(profileFile));
                Profile profile = (Profile) in.readObject();
                in.close();
                
                return profile;
            }
            catch(ClassNotFoundException expt)
            {
                System.out.println(expt.getMessage());
                System.out.println(expt.getStackTrace());
                return null;
            }
            catch(IOException expt)
            {
                System.out.println(expt.getMessage());
                System.out.println(expt.getStackTrace());
                return null;
            }
        }
        else
        {
            throw new FileNotFoundException("Couldn't find the file " + Constants.PROFILES_PATH + profileId + Constants.PROFILE_SUFFIXE);
        }
    }
}

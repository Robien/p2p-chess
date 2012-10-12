package lo23.data.serializer.tests;

import java.io.IOException;
import lo23.data.Profile;
import lo23.data.serializer.NoIdException;
import lo23.data.serializer.Serializer;

/**
 * This class is used to test the Serializer class
 */
public class TestSerializer
{
    static public void main(String[] args)
    {
        serializeProfile();
    }
    
    
    /**
     * This method creates a Profile object and tries to serialize it
     */
    static private void serializeProfile()
    {
        Profile p = new Profile("test1", null, null, Profile.STATUS.INGAME, null, null);
        try
        {
            Serializer.saveProfile(p);
            System.out.println("Serialization OK");
        }
        catch(IOException | NoIdException expt)
        {
            System.out.println(expt.getMessage());
        }
    }
}

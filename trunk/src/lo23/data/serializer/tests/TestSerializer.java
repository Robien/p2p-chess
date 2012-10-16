package lo23.data.serializer.tests;

import lo23.data.Profile;
import lo23.data.serializer.FileNotFoundException;
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
        readProfile();
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
        catch(NoIdException expt)
        {
            System.out.println(expt.getMessage());
        }
    }
    
    
    static private void readProfile()
    {
        try
        {
            Profile p = Serializer.readProfile("test1");
            System.out.println("Object read: " + p);
        }
        catch (FileNotFoundException ex)
        {
            System.out.println(ex.getMessage());
            System.out.println(ex.getStackTrace());
        }
    }
}

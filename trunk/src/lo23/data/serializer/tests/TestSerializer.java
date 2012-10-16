package lo23.data.serializer.tests;

import lo23.data.Game;
import lo23.data.Profile;
import lo23.data.exceptions.FileNotFoundException;
import lo23.data.exceptions.NoIdException;
import lo23.data.serializer.Serializer;
import lo23.utils.Enums.STATUS;

/**
 * This class is used to test the Serializer class
 */
public class TestSerializer
{
    static public void main(String[] args)
    {
        serializeProfile();
        readProfile();
        
        serializeGame();
        readGame();
    }
    
    
    /**
     * This method creates a Profile object and tries to serialize it
     */
    static private void serializeProfile()
    {
        Profile p = new Profile("test1", null, null, STATUS.INGAME, null, null, null, null, Profile.DEFAULT_AGE);
        try
        {
            Serializer.saveProfile(p);
            System.out.println("Profile serialization OK");
        }
        catch(NoIdException expt)
        {
            System.out.println(expt.getMessage());
        }
    }
    
    /**
     * This method tries to read a file generated by the serializeProfile() method
     */
    static private void readProfile()
    {
        try
        {
            Profile p = Serializer.readProfile("test1");
            System.out.println("Profile object read: " + p);
        }
        catch (FileNotFoundException ex)
        {
            System.out.println(ex.getMessage());
            System.out.println(ex.getStackTrace());
        }
    }
    
    
    /**
     * This method creates a Game object and tries to serialize it
     */
    static private void serializeGame()
    {
        Game g = new Game("test");
        try
        {
            Serializer.saveGame(g);
            System.out.println("Game serialization OK");
        }
        catch(NoIdException expt)
        {
            System.out.println(expt.getMessage());
        }
    }
    
    /**
     * This method tries to read a file generated by the serializeGame() method
     */
    static private void readGame()
    {
        try
        {
            Game g = Serializer.readGame("test");
            System.out.println("Game object read: " + g);
        }
        catch (FileNotFoundException ex)
        {
            System.out.println(ex.getMessage());
            System.out.println(ex.getStackTrace());
        }
    }
}

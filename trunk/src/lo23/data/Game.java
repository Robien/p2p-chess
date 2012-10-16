package lo23.data;

import java.io.Serializable;
import java.util.Date;



public class Game implements Serializable
{
    private String gameId;
    private Date start;
    private Date end;
    private float duration;     // Time in seconds
    

    /**
     * Constructor
     * 
     * @param id The identifier which will be associated to the created object
     */
    public Game(String id)
    {
        gameId = id;
        start = new Date();
        end = null;
        duration = 0;
    }
    
    
    /**
     * Getter for the gameId attribute
     * 
     * @return The gameId attribute
     */
    public String getGameId()
    {
        return gameId;
    }
    
    
    public void start()
    {
    }
    
    public void stop()
    {
    }
    
    public void resume()
    {
    }
}

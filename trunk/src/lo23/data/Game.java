package lo23.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;



public class Game implements Serializable
{
    private String gameId;
    private Date start;
    private Date end;
    private float duration;     // Time in seconds
    private ArrayList<GamePiece> pieces;
    private GamePiece[][] board;

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
        board = new GamePiece[32][32];
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

    public GamePiece getPieceAtXY(int x, int y)
    {
        if(x >= 0 && x < 32 && y >= 0 && y < 32) {
            return board[x][y];
        } else {
            return null;
        }
    }
}

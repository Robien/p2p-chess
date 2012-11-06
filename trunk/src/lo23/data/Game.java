package lo23.data;

import lo23.data.pieces.GamePiece;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;



/**
 *
 * @author khamidou
 */
public class Game implements Serializable
{
    private String gameId;
    private Date start;
    private Date end;
    private float duration;     // Time in seconds
    private ArrayList<GamePiece> pieces;
    private GamePiece[][] board;
    private Player localPlayer;
    private Player remotePlayer;
    private ArrayList<Event> events;

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
        board = new GamePiece[8][8];
        pieces = new ArrayList<GamePiece>();
        events = new ArrayList<Event>();
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
    
    
    /**
     * start game
     */
    public void start()
    {
    }
    
    /**
     * stop game
     */
    public void stop()
    {
    }
    
    /**
     * resume game
     */
    public void resume()
    {
    }

    /**
     *
     * @param x x position on the board
     * @param y y position on the board
     * @return GamePiece at (x, y)
     *
     */
    public GamePiece getPieceAtXY(int x, int y)
    {
        if(x >= 0 && x < 8 && y >= 0 && y < 8) {
            return board[x][y];
        } else {
            return null;
        }
    }

    /**
     *
     * @return the object representing the local player
     */
    public Player getLocalPlayer() {
        return localPlayer;
    }

    /**
     *
     * @return the object representing the local player
     */
    public Player getRemotePlayer() {
        return remotePlayer;
    }

    public ArrayList<Event> getEvents() {
        return events;
    }

    public void pushEvent(Event e) {
        events.add(e);
    }

    public void playMove(Move move) {
        //FIXME: add the rest
        //move.to.getX()
        events.add(move);
    }

    public ArrayList<GamePiece> getPieces() {
        return pieces;
    }
}

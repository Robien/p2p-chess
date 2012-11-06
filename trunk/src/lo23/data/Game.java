package lo23.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import lo23.data.exceptions.IllegalMoveException;
import lo23.data.pieces.GamePiece;



/**
 *
 * @author khamidou
 */
public class Game implements Serializable
{
    private long gameId;
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
     */
    public Game()
    {
        gameId = (new Date()).getTime();
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
    public long getGameId()
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

    public void playMove(Move move) throws IllegalMoveException {
        //FIXME: add the rest
        int xfrom = move.from.getX();
        int yfrom = move.from.getY();
        int xto = move.to.getX();
        int yto = move.to.getY();

        if(xfrom < 0 || xfrom > 7 || yfrom < 0 || yfrom > 7 ||
           xto < 0 || xto > 7 || yto < 0 || yto > 7)
            throw new IllegalMoveException();

        GamePiece piece = board[xfrom][yfrom];
        board[xfrom][yfrom] = null;

        /*
        if(board[xto][yto] != null)
            piece
         */
        events.add(move);
    }

    public ArrayList<GamePiece> getPieces() {
        return pieces;
    }
    public Date getEndDate() {
        return end;
    }
}

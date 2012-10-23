package lo23.data.pieces;

import java.io.Serializable;
import java.util.List;
import lo23.data.Game;
import lo23.data.Player;
import lo23.data.Position;

/**
 *
 * @author khamidou
 */
public abstract class GamePiece implements Serializable {

    private boolean outOfBorder;
    private Position position;
    private Player owner;
    private Game game;
    
    
    /**
     * Creates a new GamePiece object with a given position
     * 
     * @param position The position of the created object
     * @param owner The current piece's owner
     * @param game The game containig the current piece
     */
    public GamePiece(Position position, Player owner, Game game)
    {
        this.position = position;
        outOfBorder = false;
        this.owner = owner;
        this.game = game;
    }

    public boolean isOutOfBorder() {
        return outOfBorder;
    }
    
    /**
     * This method simply returns the GamePiece position
     * 
     * @return A Position object
     */
    public Position getPosition() {
        return position;
    }
    
    /**
     * This method returns the game where the current GamePiece is contained
     * 
     * @return A Game object
     */
    public Game getGame() {
        return game;
    }
    
    /**
     * This method returns the current game piece's owner
     * 
     * @return The current piece's owner
     */
    public Player getOwner() {
        return owner;
    }
    
    public abstract List<Position> getPossibleMoves();

}

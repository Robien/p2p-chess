package lo23.data.pieces;

import java.io.Serializable;
import java.util.ArrayList;
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

    public void movePiece(Position to) {
        position = to;
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

    /**
     * This method return true if at x, y there is a piece and it's an enemy
     * 
     * @return true if enemy, false in oters cases
     * @author Romain ui-gird
     */
    public boolean thereIsAnEnemyAt(int x, int y)
    {
        return getGame().getPieceAtXY(x, y) != null //there is a piece at x, y
                &&
                getGame().getPieceAtXY(x, y).getOwner().getColor() != getOwner().getColor(); //it's an enemy
    }

    /**
     * This method return true if at x, y there no piece or an enemy one's
     *
     * @return true if it's an empty case or an enemy piece
     * @author Romain ui-gird
     */
    public boolean isAValidMove(int x, int y)
    {
        if (x >= 0 && y >= 0 && x < 8 && y < 8 && getGame().getPieceAtXY(x, y) == null)
        {
            return true;
        }
        else
        {
            if (thereIsAnEnemyAt(x, y))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * This method add in list a new Position if it's a valid move /!\ care to return value
     *
     * @return true if there no obstacle (piece (friend of foe) or end of board)
     * @author Romain ui-gird
     */
    public boolean addIfValid(List<Position> list, int x, int y)
    {
        if (x >= 0 && y >= 0 && x < 8 && y < 8 && getGame().getPieceAtXY(x, y) == null)
        {
            list.add(new Position(x, y));
            return true;
        }
        else
        {
            if (thereIsAnEnemyAt(x, y))
            {
                list.add(new Position(x, y));
            }
            return false;
        }

    }

    public abstract List<Position> getPossibleMoves();


    public boolean isOncheck()
    {
        ArrayList<GamePiece> gamePieces = getGame().getPieces();

        for (int i = 0; i < gamePieces.size(); ++i)
        {
            GamePiece piece = gamePieces.get(i);
            if (piece.getOwner() != getGame().getLocalPlayer())
            {
                List<Position> pos = piece.getPossibleMoves();
                if (pos.contains(getPosition())) //TODO change to king's position
                {
                    return true;
                }
            }

        }
        return false;

    }

}

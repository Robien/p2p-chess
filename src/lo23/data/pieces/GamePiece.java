package lo23.data.pieces;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lo23.data.Game;
import lo23.data.Move;
import lo23.data.Player;
import lo23.data.Position;

/**
 *
 * @author khamidou
 */
public abstract class GamePiece implements Serializable {

    private boolean outOfBorder;
    protected Position position;
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

        try
        {
            getGame().playMove(new Move(getPosition(), to, this));
        }
        catch(Exception e)
        {
            System.out.println("Erreur !");
        }
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
     * This method return true if at x, y there no piece or an enemy one's. verify check too
     *
     * @return true if it's an empty case or an enemy piece
     * @author Romain ui-grid
     */
    public boolean isAValidMove(int x, int y)
    {
        if (x >= 0 && y >= 0 && x < 8 && y < 8 && getGame().getPieceAtXY(x, y) == null)
        {

            return (!(isOnCheckWithAMove(getPosition(), new Position(x, y))));
            
        }
        else
        {
            if (thereIsAnEnemyAt(x, y))
            {
                 return (!(isOnCheckWithAMove(getPosition(), new Position(x, y))));
            }
        }
        return false;
    }

    /**
     * This method add in list a new Position if it's a valid move /!\ care to return value
     *
     * @return true if there no obstacle (piece (friend of foe) or end of board)
     * @author Romain ui-grid
     */
    public boolean addIfValid(List<Position> list, int x, int y)
    {
        if (x >= 0 && y >= 0 && x < 8 && y < 8 && getGame().getPieceAtXY(x, y) == null)
        {
             if (!(isOnCheckWithAMove(getPosition(), new Position(x, y))))
             {
              list.add(new Position(x, y));
             }

             return true;
 
        }
        else
        {
            if (thereIsAnEnemyAt(x, y) && (!(isOnCheckWithAMove(getPosition(), new Position(x, y)))))
            {
                list.add(new Position(x, y));
            }
            return false;
        }

    }

     /**
     * This method test si une piece est sur cette case, en prenant en compte un déplacement
     *
     * @return true si y'a une piece
     * @author Romain ui-grid
     */
    public boolean isThereSomebodyHere(int x, int y, Position from, Position to)
    {
        return (getGame().getPieceAtXY(x , y) == null) || (from.getX() == x && from.getY() == y) && (!(to.getX() == x && to.getY() == y));
    }

      /**
     * This method test si une piece enemis est sur cette case, en prenant en compte un déplacement
     *
     * @return true si y'a une piece enemis
     * @author Romain ui-grid
     */
    public boolean isThereAnEnemyHere(int x, int y, Position from)
    {
        return (thereIsAnEnemyAt(x, y)) && (!(from.getX() == x && from.getY() == y));
    }

     /**
     * This method test si une piece enemis est sur cette case, en prenant en compte un déplacement
     *
     * @return true si y'a une piece enemis
     * @author Romain ui-grid
     */
    public abstract List<Position> getPossibleMoves();
    public abstract boolean isResponsableOfCheck(King king, Position from, Position to);


    public boolean isOncheck()
    {
        ArrayList<GamePiece> gamePieces = getGame().getPieces();

        for (int i = 0; i < gamePieces.size(); ++i)
        {
            GamePiece piece = gamePieces.get(i);
            if (piece.getOwner() != getGame().getLocalPlayer())
            {
                List<Position> pos = piece.getPossibleMoves();
                if (pos.contains(getOwner().getKing().getPosition()))
                {
                    return true;
                }
            }

        }
        return false;

    }
    
    public boolean isOnCheckWithAMove(Position from, Position to)
    {
        ArrayList<GamePiece> gamePieces = getGame().getPieces();

        for (int i = 0; i < gamePieces.size(); ++i)
        {
            GamePiece piece = gamePieces.get(i);
            if (piece.getOwner() != getGame().getLocalPlayer())
            {

                if (piece.isResponsableOfCheck(getOwner().getKing(), from, to)) //TODO change to king's position
                {
                    return true;
                }
            }

        }
        return false;

    }

    public boolean isCheckAndMat()
    {
        return getOwner().getKing().getPossibleMoves().isEmpty() && isOncheck();
    }



    public List<Position> removeCheckingMove(List<Position> listPossibleMove)
    {
        List<Position> listFinal = new ArrayList<Position>();
        int i = 0;
        while (i < listPossibleMove.size())
        {
            if (!(isOnCheckWithAMove(getPosition(), listPossibleMove.get(i))))
            {
                listFinal.add(listPossibleMove.get(i));
            }
            i++;
        }

        return listFinal;
    }


public boolean isPawnTop()
{
    return (false);
}
}


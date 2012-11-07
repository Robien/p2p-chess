package lo23.data.pieces;

import java.util.ArrayList;
import java.util.List;
import lo23.data.Game;
import lo23.data.Player;
import lo23.data.Position;

/**
 *
 * @author khamidou
 */
public class Bishop extends GamePiece
{

    /**
     * Creates a new Bishop object with a given position
     * 
     * @param position The position of the created object
     * @param owner The current piece's owner
     * @param game The game containig the current piece
     */
    public Bishop(Position position, Player owner, Game game)
    {
        super(position, owner, game);
    }

    /**
     *
     * @author Romain et Guilhem
     */
    @Override
    public List<Position> getPossibleMoves()
    {
        //TODO : add echec and clean

        ArrayList<Position> positions = new ArrayList<Position>();

        boolean xpyp, xmyp, xpym, xmym;
        xpyp = true; //can move x+ and y+ ?
        xmyp = true; //can move x- and y+ ?
        xpym = true; //can move x+ and y- ?
        xmym = true; //can move x- and y- ?

        int x = getPosition().getX();
        int y = getPosition().getY();

        Game game = getGame();

        for (int i = 1; i < 8 && (xpyp || xmyp || xpym || xmym); i++)
        {

            if (xpyp && x + i < 8 && y + i < 8 && game.getPieceAtXY(x + i, y + i) == null)
            {
                positions.add(new Position(x + i, y + i));
            }
            else
            {
                if (thereIsAnEnemyAt(x + i, y + i))
                {
                    positions.add(new Position(x + i, y + i));
                }
                xpyp = false;
            }

            if (xmyp && x - i >= 0 && y + i < 8 && game.getPieceAtXY(x - i, y + i) == null)
            {
                positions.add(new Position(x - i, y + i));
            }
            else
            {
                if (thereIsAnEnemyAt(x - i, y + i))
                {
                    positions.add(new Position(x - i, y + i));
                }
                xmyp = false;
            }

            if (xpym && x + i < 8 && y - i >= 0 && game.getPieceAtXY(x + i, y - i) == null)
            {
                positions.add(new Position(x + i, y - i));
            }
            else
            {
                if (thereIsAnEnemyAt(x + i, y - i))
                {
                    positions.add(new Position(x + i, y - i));
                }
                xpym = false;
            }

              if (xmym && x - i >= 0 && y - i >= 0 && game.getPieceAtXY(x - i, y - i) == null)
            {
                positions.add(new Position(x - i, y - i));
            }
            else
            {
                if (thereIsAnEnemyAt(x - i, y - i))
                {
                    positions.add(new Position(x - i, y - i));
                }
                xmym = false;
            }

        }

        return positions;
    }
 /**
     *
     * @author Romain et Guilhem
     */
    @Override
    public boolean isResponsableOfCheck(King king, Position from, Position to)
    {
        //TODO : add echec and clean


        boolean xpyp, xmyp, xpym, xmym;
        xpyp = true; //can move x+ and y+ ?
        xmyp = true; //can move x- and y+ ?
        xpym = true; //can move x+ and y- ?
        xmym = true; //can move x- and y- ?

        int x = getPosition().getX();
        int y = getPosition().getY();

        Game game = getGame();

        for (int i = 1; i < 8 && (xpyp || xmyp || xpym || xmym); i++)
        {

            if (xpyp && x + i < 8 && y + i < 8 && (isThereSomebodyHere(x + i, y + i, from, to)))
            {

            }
            else
            {
                if (isThereAnEnemyHere(x + i, y + i, to) && game.getPieceAtXY(x + i, y + i) == king)
                {
                    return true;
                }
                xpyp = false;
            }

            if (xmyp && x - i >= 0 && y + i < 8 && isThereSomebodyHere(x - i, y + i, from, to))
            {

            }
            else
            {
                if (isThereAnEnemyHere(x - i, y + i, to) && game.getPieceAtXY(x + i, y + i) == king)
                {
                    return true;
                }
                xmyp = false;
            }

            if (xpym && x + i < 8 && y - i >= 0 && isThereSomebodyHere(x + i, y - i, from, to))
            {
     
            }
            else
            {
                if (isThereAnEnemyHere(x + i, y - i, to) && game.getPieceAtXY(x + i, y + i) == king)
                {
                    return true;
                }
                xpym = false;
            }

              if (xmym && x - i >= 0 && y - i >= 0 && isThereSomebodyHere(x - i, y - i, from, to))
            {

            }
            else
            {
                if (isThereAnEnemyHere(x - i, y - i, to) && game.getPieceAtXY(x + i, y + i) == king)
                {
                    return true;
                }
                xmym = false;
            }

        }

        return false;
    }
  
}


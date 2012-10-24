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
     * @author Romain
     */
    @Override
    public List<Position> getPossibleMoves()
    {
        //TODO : add echec and clean

        ArrayList<Position> positions = new ArrayList<Position>();

        boolean xpyp, xmyp, xpym, xmpm;
        xpyp = true; //can move x+ and y+ ?
        xmyp = true; //can move x- and y+ ?
        xpym = true; //can move x+ and y- ?
        xmpm = true; //can move x- and y- ?

        int x = getPosition().getX();
        int y = getPosition().getY();

        Game game = getGame();

        for (int i = 0; i < 8 && (xpyp || xmyp || xpym || xmpm); i++)
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

            if (xmpm && x - i >= 0 && y - i >= 8 && game.getPieceAtXY(x + i, y + i) == null)
            {
                positions.add(new Position(x - i, y - i));
            }
            else
            {
                if (thereIsAnEnemyAt(x - i, y - i))
                {
                    positions.add(new Position(x - i, y - i));
                }
                xmpm = false;
            }

        }

        return positions;
    }
}


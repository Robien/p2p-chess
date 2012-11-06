/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lo23.data.pieces;

import java.util.ArrayList;
import java.util.List;
import lo23.data.Game;
import lo23.data.Player;
import lo23.data.Position;

/**
 *
 * @author Guilhem
 */
public class Rook extends GamePiece {

    /**
     * Creates a new Rook object with a given position
     * 
     * @param position The position of the created object
     * @param owner The current piece's owner
     * @param game The game containig the current piece
     */

    private Boolean alreadyMoved = false;

    public Rook(Position position, Player owner, Game game) 
    {
        super(position, owner, game);
    }

    @Override
    public List<Position> getPossibleMoves() 
    {
       ArrayList<Position> positions = new ArrayList<Position>();

        
        boolean xp, xm, yp, ym;
        xp = true; //can move x+?
        xm = true; //can move x-  ?
        yp = true; //can move y+ ?
        ym = true; //can move y- ?

        int x = getPosition().getX();
        int y = getPosition().getY();

        Game game = getGame();

        for (int i = 1; i < 8 && (xp || xm || yp || ym); i++)
        {

            if (xp && x + i < 8 && game.getPieceAtXY(x + i, y) == null)
            {
                positions.add(new Position(x + i, y));
            }
            else
            {
                if (thereIsAnEnemyAt(x + i, y))
                {
                    positions.add(new Position(x + i, y));
                }
                xp = false;
            }

            if (xm && x - i >= 0 && game.getPieceAtXY(x - i, y) == null)
            {
                positions.add(new Position(x - i, y));
            }
            else
            {
                if (thereIsAnEnemyAt(x - i, y))
                {
                    positions.add(new Position(x - i, y));
                }
                xm = false;
            }

            if (yp  && y + i < 8 && game.getPieceAtXY(x, y + i) == null)
            {
                positions.add(new Position(x, y + i));
            }
            else
            {
                if (thereIsAnEnemyAt(x, y + i))
                {
                    positions.add(new Position(x, y + i));
                }
                yp = false;
            }
            
            if (ym && y - i >= 0 && game.getPieceAtXY(x, y - i) == null)
            {
                positions.add(new Position(x, y - i));
            }
            else
            {
                if (thereIsAnEnemyAt(x, y - i))
                {
                    positions.add(new Position(x, y - i));
                }
                ym = false;
            }

        }

        return positions;
    }

}

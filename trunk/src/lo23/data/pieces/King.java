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
public class King extends GamePiece {
    
    /**
     * Creates a new King object with a given position
     * 
     * @param position The position of the created object
     * @param owner The current piece's owner
     * @param game The game containig the current piece
     */
    public King(Position position, Player owner, Game game) {
        super(position, owner, game);
    }

    @Override
    public List<Position> getPossibleMoves() 
    {
           //TODO : add echec and clean

        ArrayList<Position> positions = new ArrayList<Position>();

      
//        xpyp = true; //can move x+ and y+ ?
//        xmyp = true; //can move x- and y+ ?
//        xpym = true; //can move x+ and y- ?
//        xmym = true; //can move x- and y- ?
//        xp = true;   //can move x+ ?
//        xm = true;   //can move x- ?
//        yp = true;   //can move y+ ?
//        ym = true;   //can move y- ?
        
        int x = getPosition().getX();
        int y = getPosition().getY();

        Game game = getGame();

           if(x + 1 < 8 && y + 1 < 8 && game.getPieceAtXY(x + 1, y + 1) == null)
            {
                positions.add(new Position(x + 1, y + 1));
            }
            else
            {
                if (thereIsAnEnemyAt(x + 1, y + 1))
                {
                    positions.add(new Position(x + 1, y + 1));
                }
            }

            if (x - 1 >= 0 && y + 1 < 8 && game.getPieceAtXY(x - 1, y + 1) == null)
            {
                positions.add(new Position(x - 1, y + 1));
            }
            else
            {
                if (thereIsAnEnemyAt(x - 1, y + 1))
                {
                    positions.add(new Position(x - 1, y + 1));
                }
              
            }

            if (x + 1 < 8 && y - 1 >= 0 && game.getPieceAtXY(x + 1, y - 1) == null)
            {
                positions.add(new Position(x + 1, y - 1));
            }
            else
            {
                if (thereIsAnEnemyAt(x + 1, y - 1))
                {
                    positions.add(new Position(x + 1, y - 1));
                }
                
            }

            if (x - 1 >= 0 && y - 1 >= 0 && game.getPieceAtXY(x - 1, y - 1) == null)
            {
                positions.add(new Position(x - 1, y - 1));
            }
            else
            {
                if (thereIsAnEnemyAt(x - 1, y - 1))
                {
                    positions.add(new Position(x - 1, y - 1));
                }
            }

        
            if (x + 1 < 8 && game.getPieceAtXY(x + 1, y) == null)
            {
                positions.add(new Position(x + 1, y));
            }
            else
            {
                if (thereIsAnEnemyAt(x + 1, y))
                {
                    positions.add(new Position(x + 1, y));
                }
            }

            if (x - 1 >= 0 && game.getPieceAtXY(x - 1, y) == null)
            {
                positions.add(new Position(x - 1, y));
            }
            else
            {
                if (thereIsAnEnemyAt(x - 1, y))
                {
                    positions.add(new Position(x - 1, y));
                }
            }

            if (y + 1 < 8 && game.getPieceAtXY(x, y + 1) == null)
            {
                positions.add(new Position(x, y + 1));
            }
            else
            {
                if (thereIsAnEnemyAt(x, y + 1))
                {
                    positions.add(new Position(x, y + 1));
                }
            }
            
            if (y - 1 >= 0 && game.getPieceAtXY(x, y - 1) == null)
            {
                positions.add(new Position(x, y - 1));
            }
            else
            {
                if (thereIsAnEnemyAt(x, y - 1))
                {
                    positions.add(new Position(x, y - 1));
                }
            }
        return positions;
    }

}

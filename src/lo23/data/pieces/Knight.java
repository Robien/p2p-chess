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
public class Knight extends GamePiece {
    
    /**
     * Creates a new Knight object with a given position
     * 
     * @param position The position of the created object
     * @param owner The current piece's owner
     * @param game The game containig the current piece
     */
    public Knight(Position position, Player owner, Game game) {
        super(position, owner, game);
    }
    
    @Override
    public List<Position> getPossibleMoves() {
         //TODO : add echec and clean

        ArrayList<Position> positions = new ArrayList<Position>();

    

        int x = getPosition().getX();
        int y = getPosition().getY();

        Game game = getGame();

        //6 cases
        
            if (x + 1 < 8 && y + 2 < 8 && game.getPieceAtXY(x + 1, y + 2) == null)
            {
                positions.add(new Position(x + 1, y + 2));
            }
            else
            {
                if (thereIsAnEnemyAt(x + 1, y + 2))
                {
                    positions.add(new Position(x + 1, y + 2));
                }
            }

            if (x - 1 >= 0 && y + 2 < 8 && game.getPieceAtXY(x - 1, y + 2) == null)
            {
                positions.add(new Position(x - 1, y + 2));
            }
            else
            {
                if (thereIsAnEnemyAt(x - 1, y + 2))
                {
                    positions.add(new Position(x - 1, y + 2));
                }
            }
            
            if (x - 2 >=0 && y + 1 < 8 && game.getPieceAtXY(x - 2, y + 1) == null)
            {
                positions.add(new Position(x - 2, y + 1));
            }
            else
            {
                if (thereIsAnEnemyAt(x - 2, y + 1))
                {
                    positions.add(new Position(x - 2, y + 1));
                }
            }
            
            if (x + 2 < 8 && y + 1 < 8 && game.getPieceAtXY(x + 2, y + 1) == null)
            {
                positions.add(new Position(x + 2, y + 1));
            }
            else
            {
                if (thereIsAnEnemyAt(x + 2, y + 1))
                {
                    positions.add(new Position(x + 1, y + 1));
                }
            }
            
            if (x - 2 >=0 && y - 1 >=0 && game.getPieceAtXY(x - 2, y - 1) == null)
            {
                positions.add(new Position(x - 2, y - 1));
            }
            else
            {
                if (thereIsAnEnemyAt(x - 2, y - 1))
                {
                    positions.add(new Position(x - 2, y - 1));
                }
            }
            
            if (x + 2 < 8 && y - 1 >=0 && game.getPieceAtXY(x + 2, y - 1) == null)
            {
                positions.add(new Position(x + 2, y - 1));
            }
            else
            {
                if (thereIsAnEnemyAt(x + 2, y - 1))
                {
                    positions.add(new Position(x + 2, y - 1));
                }
            }

        return positions;    
    }

}

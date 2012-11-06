package lo23.data.pieces;

import java.util.ArrayList;
import java.util.List;
import lo23.data.Game;
import lo23.data.Player;
import lo23.data.Position;

/**
 *
 * @author  Guilhem
 */
public class Pawn extends GamePiece {
    
    /**
     * Creates a new Pawn object with a given position
     * 
     * @param position The position of the created object
     * @param owner The current piece's owner
     * @param game The game containig the current piece
     */
    public Pawn(Position position, Player owner, Game game) {
        super(position, owner, game);
    }

    @Override
    public List<Position> getPossibleMoves() {
        
        
        ArrayList<Position> positions = new ArrayList<Position>();


        int x = getPosition().getX();
        int y = getPosition().getY();

        Game game = getGame();

        
        
        //first time (better to use a boolean)
           if(y==1)
           {
                //nobody (no a friend or an enemy) you can move
                if (game.getPieceAtXY(x, y + 2) == null && !thereIsAnEnemyAt(x, y + 2))
                {
                    positions.add(new Position(x, y + 2));
                }
           }
           
           
           
         // every cases, just one move possible
           
           //nobody (no a friend or an enemy) you can move
                 if (game.getPieceAtXY(x, y + 1) == null && !thereIsAnEnemyAt(x, y + 1))
                {
                    positions.add(new Position(x, y + 1));
                }
     
             //if you can kill someone 
                if (thereIsAnEnemyAt(x - 1, y + 1))
                {

                    positions.add(new Position(x - 1, y + 1));
                }
             //if you can kill someone  (2)
                if (thereIsAnEnemyAt(x + 1, y + 1))
                {

                    positions.add(new Position(x + 1, y + 1));
                }
            
        

        return positions;
    }

}
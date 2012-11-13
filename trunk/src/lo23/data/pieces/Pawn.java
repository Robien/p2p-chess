package lo23.data.pieces;

import java.util.ArrayList;
import java.util.List;
import lo23.data.Game;
import lo23.data.Player;
import lo23.data.Position;
import lo23.utils.Enums.COLOR;

/**
 *
 * @author  Guilhem
 */
public class Pawn extends GamePiece {


     private boolean firstMove; // is the pawn never move ?

    /**
     * Creates a new Pawn object with a given position
     * 
     * @param position The position of the created object
     * @param owner The current piece's owner
     * @param game The game containig the current piece
     */
    public Pawn(Position position, Player owner, Game game) {
        super(position, owner, game);
            firstMove = true;
    }

    @Override
        public void movePiece(Position to) {
        super.position = to;
        firstMove = false;
    }

    @Override
    public List<Position> getPossibleMoves() {
        
        
        ArrayList<Position> positions = new ArrayList<Position>();


        int x = getPosition().getX();
        int y = getPosition().getY();

        Game game = getGame();
    //test de la couleur pour trouver le sens de déplacement du pion
        if (getOwner().getColor() == COLOR.WHITE)
        {
        

           if(firstMove)
           {
                //nobody (not a friend or an enemy) you can move
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
            
        }
        else //a black Pawn
        {

           if(firstMove)
           {
                //nobody (no a friend or an enemy) you can move
                if (game.getPieceAtXY(x, y - 2) == null && !thereIsAnEnemyAt(x, y - 2))
                {
                    positions.add(new Position(x, y - 2));
                }
           }



         // every cases, just one move possible

           //nobody (no a friend or an enemy) you can move
                 if (game.getPieceAtXY(x, y - 1) == null && !thereIsAnEnemyAt(x, y - 1))
                {
                    positions.add(new Position(x, y - 1));
                }

             //if you can kill someone
                if (thereIsAnEnemyAt(x - 1, y - 1))
                {

                    positions.add(new Position(x - 1, y - 1));
                }
             //if you can kill someone  (2)
                if (thereIsAnEnemyAt(x + 1, y - 1))
                {

                    positions.add(new Position(x + 1, y - 1));
                }
        }

        return positions;
    }

       @Override
    public boolean isResponsableOfCheck(King king, Position from, Position to)
    {
                //même fonctionnement que pour getPosibleMove, mais on ne vérifie pas les échecs et on ne regarde que si le roi est en échec.
        // prend en compte la grille + un déplacement (permet de tester un Move sans modifier la grille)

System.out.println(king.getPosition().getX() +  " - " + king.getPosition().getY());
        int x = getPosition().getX();
        int y = getPosition().getY();

        Game game = getGame();

        //test de la couleur pour trouver le sens de déplacement du pion
        if (getOwner().getColor() == COLOR.WHITE)
        {
 
     
             //if you can kill someone 
                if (isThereAnEnemyHere(x - 1, y + 1, to) && game.getPieceAtXY(x -1, y +1) == king)
                {
                    return true;
                }
             //if you can kill someone  (2)
                if (isThereAnEnemyHere(x + 1, y + 1, to) && game.getPieceAtXY(x +1, y +1) == king)
                {

                    return true;
                }
            
        }
        else //a black Pawn
        {

        
             //if you can kill someone
                if (isThereAnEnemyHere(x - 1, y - 1, to) && game.getPieceAtXY(x -1, y -1) == king)
                {

                    return true;
                }
             //if you can kill someone  (2)
                if (isThereAnEnemyHere(x + 1, y - 1, to) && game.getPieceAtXY(x +1, y -1) == king)
                {

                    return true;
                }
        }

        return false;   
       }




    @Override
public boolean isPawnTop()
{
    return (getPosition().getY() == 7 || getPosition().getY() == 0);
}

}


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lo23.data.pieces;

import java.util.List;
import lo23.data.Game;
import lo23.data.Player;
import lo23.data.Position;

/**
 *
 * @author khamidou
 */
public class Queen extends GamePiece {
    
    /**
     * Creates a new Queen object with a given position
     * 
     * @param position The position of the created object
     * @param owner The current piece's owner
     * @param game The game containig the current piece
     */
    public Queen(Position position, Player owner, Game game) {
        super(position, owner, game);
    }

    @Override
    public List<Position> getPossibleMoves() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}

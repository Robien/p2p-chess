/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lo23.data.pieces;

import java.util.List;
import lo23.data.Position;

/**
 *
 * @author khamidou
 */
public class Rook extends GamePiece {

    /**
     * Creates a new Rook object with a given position
     * 
     * @param position The position of the created object
     */
    public Rook(Position position) {
        super(position);
    }

    @Override
    public List<Position> getPossibleMoves() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}

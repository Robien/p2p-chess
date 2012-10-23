package lo23.data.pieces;

import java.util.List;
import lo23.data.Position;

/**
 *
 * @author khamidou
 */
public class King extends GamePiece {
    
    /**
     * Creates a new King object with a given position
     * 
     * @param position The position of the created object
     */
    public King(Position position) {
        super(position);
    }

    @Override
    public List<Position> getPossibleMoves() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}

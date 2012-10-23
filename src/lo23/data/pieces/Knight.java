package lo23.data.pieces;

import java.util.List;
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
     */
    public Knight(Position position) {
        super(position);
    }
    
    @Override
    public List<Position> getPossibleMoves() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}

package lo23.data.pieces;

import java.util.List;
import lo23.data.Position;

/**
 *
 * @author khamidou
 */
public class Bishop extends GamePiece {

    /**
     * Creates a new Bishop object with a given position
     * 
     * @param position The position of the created object
     */
    public Bishop(Position position) {
        super(position);
    }
    
    @Override
    public List<Position> getPossibleMoves() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}

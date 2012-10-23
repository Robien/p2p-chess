package lo23.data.pieces;

import java.util.List;
import lo23.data.Game;
import lo23.data.Player;
import lo23.data.Position;

/**
 *
 * @author khamidou
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
        throw new UnsupportedOperationException("Not supported yet.");
    }

}

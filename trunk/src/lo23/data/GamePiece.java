/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lo23.data;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author khamidou
 */
public abstract class GamePiece implements Serializable {

    private boolean outOfBorder;
    private Position position;
    
    
    /**
     * Creates a new GamePiece object with a given position
     * 
     * @param position The position of the created object
     */
    public GamePiece(Position position)
    {
        this.position = position;
    }

    public boolean isOutOfBorder() {
        return outOfBorder;
    }
    
    /**
     * This method simply returns the GamePiece position
     * 
     * @return A Position object
     */
    public Position getPosition() {
        return position;
    }
    
    public abstract List<Position> getPossibleMoves();

}

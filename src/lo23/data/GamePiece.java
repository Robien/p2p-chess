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

    public boolean isOutOfBorder() {
        return outOfBorder;
    }
    
    public abstract List<Position> getPossibleMoves();

}

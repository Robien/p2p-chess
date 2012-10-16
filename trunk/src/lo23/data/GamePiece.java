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
public class GamePiece implements Serializable {

    private boolean outOfBorder;

    public boolean isOutOfBorder() {
        return outOfBorder;
    }
    public List<Position> getPossibleMoves() {
        return null;
    }
}

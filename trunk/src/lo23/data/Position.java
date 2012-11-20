/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lo23.data;

import lo23.ui.grid.PositionOnBoard;

/**
 *
 * @author khamidou
 */
public class Position {
    private int x;
    private int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
	@Override
	public boolean equals(Object P) {
		if (((PositionOnBoard) P).getX() == x && ((PositionOnBoard) P).getY() == y) return true;
		else return false;
	}
	
	@Override
	public int hashCode() {
		String temp = String.valueOf(x) + String.valueOf(y);
		return Integer.valueOf(temp);
	}
}

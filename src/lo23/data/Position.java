/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lo23.data;

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

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public boolean equals(Object P) {
        if (((Position) P).getX() == x && ((Position) P).getY() == y) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        String temp = String.valueOf(x) + String.valueOf(y);
        return Integer.valueOf(temp);
    }
}

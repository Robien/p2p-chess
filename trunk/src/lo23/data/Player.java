/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lo23.data;

/**
 *
 * @author khamidou
 */
public class Player {
    public enum COLOR {BLACK, WHITE};
    private COLOR playerColor;
    private long time = 0;
    private long startTime = 0;
    private long endTime = 0;
    private long remainingTime = 0;

    public Player(COLOR playerColor, long remainingTime) {
        this.playerColor = playerColor;
        this.remainingTime = remainingTime;
    }
   
    public void startTime() {
        startTime = System.currentTimeMillis();
    }

    public void stopTime() {
        endTime = System.currentTimeMillis();
        time += endTime - startTime;
    }

    public long getRemainingTime() {
        return remainingTime - time;
    }
}

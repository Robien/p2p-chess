/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lo23.data;

import java.util.ArrayList;
import lo23.data.pieces.GamePiece;
import lo23.data.pieces.King;
import lo23.utils.Enums.COLOR;

/**
 *
 * @author khamidou
 */
public class Player {

    private COLOR playerColor;
    private long time = 0;
    private long startTime = 0;
    private long endTime = 0;
    private long remainingTime = 0;
    private ArrayList<GamePiece> pieces;
    private PublicProfile publicProfile;
    
    public Player(COLOR playerColor, long remainingTime, PublicProfile publicProfile ) {
        this.playerColor = playerColor;
        this.remainingTime = remainingTime;
        this.pieces = new ArrayList<GamePiece>();
        this.publicProfile=publicProfile;
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

    public King getKing() {
        return null;
    }

    /**
     * This method gives the current player's color
     * 
     * @return The current player's color
     */
    public COLOR getColor() {
        return playerColor;
    }

    /**
     * Used by the Game constructor to provide to the Player
     * a list of the gamepieces.
     * @param pc piece to add
     */
    public void addPiece(GamePiece pc) {
        pieces.add(pc);
    }
    
    
    public PublicProfile getPublicProfile() {
        return publicProfile;
    }
}

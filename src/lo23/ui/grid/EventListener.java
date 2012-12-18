/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lo23.ui.grid;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;
import lo23.data.ApplicationModel;
import lo23.data.Constant;
import lo23.data.Move;
import lo23.data.Player;
import lo23.data.Position;
import lo23.data.exceptions.IllegalMoveException;
import lo23.data.managers.Manager;
import lo23.utils.Enums;
import lo23.utils.Enums.CONSTANT_TYPE;



/**
 *
 * @author Karim
 */
public class EventListener implements PropertyChangeListener {
    private ApplicationModel myModel;
    public static final String NEW_EVENT_ADDED = "new_event_added";
    
    private GamePanel gamePanel;
    private ChatPanel2 chatPanel;
    private TimerPanel timerPanel;
    
    public EventListener(GamePanel panel, ApplicationModel model){
        gamePanel = panel;
        myModel = model;
       ((Manager)myModel.getGManager()).subscribe(this,NEW_EVENT_ADDED);
    }
    
    public EventListener(ChatPanel2 panel, ApplicationModel model){
        chatPanel = panel;
        myModel = model;
        ((Manager)myModel.getGManager()).subscribe(this, NEW_EVENT_ADDED);
    }
    
    public EventListener(TimerPanel panel, ApplicationModel model){
        timerPanel = panel;
        myModel = model;
        ((Manager)myModel.getGManager()).subscribe(this, NEW_EVENT_ADDED);
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("EVENT DETECTED ; " + evt.getOldValue() + " ; " + evt.getNewValue());
        
         if(evt.getNewValue() instanceof Move) {
             System.out.println("EVENT DETECTED 3");
             if(gamePanel != null){
                 System.out.println("EVENT DETECTED 4");


                 if (((Move)evt.getNewValue()).getPiece().haveDoneARook())
                 {
                    System.out.println("EVENT DETECTED 4 => rook");
                    if (((Move)evt.getNewValue()).getTo().getX() == 1)
                    {
                        Move move = new Move(new Position(0,((Move)evt.getNewValue()).getTo().getY()), new Position(2,((Move)evt.getNewValue()).getTo().getY()), null );
                        try
                        {
                            gamePanel.majDataBoard(move);
                        } catch (IllegalMoveException ex)
                        {
                            Logger.getLogger(EventListener.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        gamePanel.updateBoardWithoutChangeColor(move);

                    }
                    else
                    {
                        Move move = new Move(new Position(7,((Move)evt.getNewValue()).getTo().getY()), new Position(5,((Move)evt.getNewValue()).getTo().getY()), null );
                        try
                        {
                            gamePanel.majDataBoard(move);
                        } catch (IllegalMoveException ex)
                        {
                            Logger.getLogger(EventListener.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        gamePanel.updateBoardWithoutChangeColor(move);
                    }
                 }
               gamePanel.updateBoard((Move)evt.getNewValue());
                 
             } else if(chatPanel != null){
                     try {
                         chatPanel.gameMsg((Move) evt.getNewValue());
                     } catch (BadLocationException ex) {
                         Logger.getLogger(EventListener.class.getName()).log(Level.SEVERE, null, ex);
                     }
             } else if(timerPanel != null){
                 if(timerPanel.playerTimer.isRunning()){
                     timerPanel.playerTimer.pauseTimer();
                     timerPanel.player.stopTime();
                 } else {
                     timerPanel.playerTimer.startTimer();
                     timerPanel.player.startTime();
                 }
             }
             
         } else if(evt.getNewValue() instanceof lo23.data.Message){
             if(chatPanel != null){
                try {

                    chatPanel.receivedMsg((lo23.data.Message)evt.getNewValue());
                } catch (BadLocationException ex) {
                    Logger.getLogger(EventListener.class.getName()).log(Level.SEVERE, null, ex);
                }
             }
         } else if(evt.getNewValue() instanceof Constant){
             Constant cst = (Constant)evt.getNewValue();
             CONSTANT_TYPE type = cst.getConstant();
             
             if(type == CONSTANT_TYPE.OUT_OF_TIME){
                 if(gamePanel != null){
                     gamePanel.endOfGame(cst.getSender());
                 }
             }
            
            if(type == CONSTANT_TYPE.SURRENDER){
                 if(gamePanel != null && cst.getSender() != myModel.getGManager().getCurrentGame().getLocalPlayer()){
                     gamePanel.surrender(cst.getSender());
                 }
             }
            
            if(type == CONSTANT_TYPE.DRAW_ASKED){
                 if(gamePanel != null && cst.getSender() != myModel.getGManager().getCurrentGame().getLocalPlayer()){
                     gamePanel.drawRequest(cst.getSender());
                 }
             }
            
              if(type == CONSTANT_TYPE.DRAW_ACCEPTED){
                 if(gamePanel != null && cst.getSender() != myModel.getGManager().getCurrentGame().getLocalPlayer()){
                     gamePanel.drawAccepted(cst.getSender());
                 }
             }           
             if(gamePanel != null){
                 if(evt.getNewValue() == myModel.getGManager().getCurrentGame().getLocalPlayer()){
                     gamePanel.endOfGame(myModel.getGManager().getCurrentGame().getRemotePlayer());
                 } else {
                     gamePanel.endOfGame(myModel.getGManager().getCurrentGame().getLocalPlayer());
                 }     
             }
         } else {
             System.out.println("EVENT DETECTED BUT INSTANCE FAILED");
         }
        }
}

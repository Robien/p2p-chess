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
import lo23.communication.message.Message;
import lo23.data.ApplicationModel;
import lo23.data.Move;
import lo23.data.Position;
import lo23.data.managers.Manager;



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
                 gamePanel.updateBoard((Move)evt.getNewValue());

                 if (((Move)evt.getNewValue()).getPiece().haveDoneARook())
                 {
                    System.out.println("EVENT DETECTED 4 => rook");
                    if (((Move)evt.getNewValue()).getTo().getX() == 1)
                    {
                         gamePanel.updateBoardWithoutChangeColor(new Move(new Position(0,((Move)evt.getNewValue()).getTo().getY()), new Position(2,((Move)evt.getNewValue()).getTo().getY()), null ));

                    }
                    else
                    {
                         gamePanel.updateBoardWithoutChangeColor(new Move(new Position(7,((Move)evt.getNewValue()).getTo().getY()), new Position(5,((Move)evt.getNewValue()).getTo().getY()), null ));
                    }
                 }
                 
             } else if(chatPanel != null){
                 try {
                     chatPanel.gameMsg((Move)evt.getNewValue());
                 } catch (BadLocationException ex) {
                     Logger.getLogger(EventListener.class.getName()).log(Level.SEVERE, null, ex);
                 }
             } else if(timerPanel != null){
                 if(timerPanel.playerTimer.isRunning()){
                     timerPanel.playerTimer.pauseTimer();
                 } else {
                     timerPanel.playerTimer.startTimer();
                 }
             }
             
         } else if(evt.getNewValue() instanceof Message){
                try {
                    chatPanel.receivedMsg((lo23.data.Message)evt.getNewValue());
                } catch (BadLocationException ex) {
                    Logger.getLogger(EventListener.class.getName()).log(Level.SEVERE, null, ex);
                }
         } else {
             System.out.println("EVENT DETECTED BUT INSTANCE FAILED");
         }
        }
}

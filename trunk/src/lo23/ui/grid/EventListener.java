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
    
    public EventListener(GamePanel panel, ApplicationModel model){
        gamePanel = panel;
        myModel = model;
//        ((Manager)myModel.getGManager()).subscribe(this,NEW_EVENT_ADDED);
    }
    
    public EventListener(ChatPanel2 panel, ApplicationModel model){
        chatPanel = panel;
        myModel = model;
   //     ((Manager)myModel.getGManager()).subscribe(this, NEW_EVENT_ADDED);
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (NEW_EVENT_ADDED.equals(evt.getPropertyName())) {
         if(evt.getNewValue() instanceof Move) {
             if(gamePanel != null){
                 gamePanel.updateBoard((Move)evt.getNewValue());
             } else if(chatPanel != null){
                 //chatPanel.gameMsg((Move)evt.getNewValue());
             }
             
         } else if(evt.getNewValue() instanceof Message){
//                try {
//                    chatPanel.receivedMsg((lo23.data.Message)evt.getNewValue());
//                } catch (BadLocationException ex) {
//                    Logger.getLogger(EventListener.class.getName()).log(Level.SEVERE, null, ex);
//                }
         }
        }
    }
}

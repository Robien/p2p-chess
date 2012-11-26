/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lo23.ui.grid;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import lo23.data.ApplicationModel;
import lo23.data.Move;
import lo23.data.managers.Manager;

/**
 *
 * @author Karim
 */
public class EventListener implements PropertyChangeListener {
    public static final String NEW_EVENT_ADDED = "new-move-added";
    private ApplicationModel myModel;
    
    private GamePanel gamePanel;
    
    public EventListener(GamePanel panel, ApplicationModel model){
        gamePanel = panel;
        myModel = model;
//        ((Manager)myModel.getGManager()).subscribe(this,NEW_EVENT_ADDED);
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (NEW_EVENT_ADDED.equals(evt.getPropertyName())) {
          
        }
    }
}

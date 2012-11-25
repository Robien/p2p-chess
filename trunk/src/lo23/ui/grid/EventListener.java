/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lo23.ui.grid;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import lo23.data.Move;

/**
 *
 * @author Karim
 */
public class EventListener implements PropertyChangeListener {
    private GamePanel gamePanel;
    
    public EventListener(GamePanel panel){
        gamePanel = panel;
    }
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("move".equals(evt.getPropertyName())) {
            gamePanel.updateBoard((Move)evt.getNewValue());
        }
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lo23.ui.grid;

/**
 *
 * @author Karim
 */

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JPanel;

public class GamePanel extends JPanel {
    
    public GamePanel() {
     super();
     build();
    }
    
    private void build() {
        setSize(680, 500); //On donne une taille à notre fenêtre
        setBackground(Color.BLACK);
       
    }
        
}

 

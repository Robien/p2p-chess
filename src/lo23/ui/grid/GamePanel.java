/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lo23.ui.grid;

/**
 *
 * @author Karim
 */


import java.awt.ComponentOrientation;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GamePanel extends JPanel {
    
    
    
    public GamePanel() {
     super();
     build();
    }
    
    private void build() {
       GridBagLayout gameBoard = new GridBagLayout();
       setSize(680, 500); //On donne une taille à notre fenêtre
       setLayout(gameBoard);
       applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
       
       GridBagConstraints constraints = new GridBagConstraints();
       constraints.fill = GridBagConstraints.CENTER;
       constraints.anchor = GridBagConstraints.CENTER;
       
       
       for(int i=0; i<8; i++){
           for(int j=0; j<8; j++){
               constraints.gridx = i;
               constraints.gridy = j;
               
               
                    
               if((i+j)%2 != 0){
                    String path = getClass().getClassLoader().getResource(".").getPath();
                    ImageIcon image = new ImageIcon(path + "lo23/ui/resources/caseB.JPG");
                    JLabel label = new JLabel("", image, JLabel.CENTER);
                    add(label, constraints);
               } else {
                    String path = getClass().getClassLoader().getResource(".").getPath();
                    System.out.println(path);
                    ImageIcon image = new ImageIcon(path + "lo23/ui/resources/caseN.JPG");
                    JLabel label = new JLabel("", image, JLabel.CENTER);
                    add(label, constraints);
               }
              
              
          
            }    

         }
       
    }
        
}

 

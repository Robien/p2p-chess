/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lo23.ui.grid;

import javax.swing.SwingUtilities;
import lo23.data.ApplicationModel;

/**
 *
 * @author Karim
 */
public class Main {
        static ApplicationModel myModel;


     private static ApplicationModel getModel(){
         return myModel;
     }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            public void run() {
                myModel = new ApplicationModel();
                myModel.setGameManager(new TMP_GameManager(myModel));
//                myModel.setProfileManager(new TMP_ProfileManager(myModel));

                MainWindow fenetre = new MainWindow(getModel());
                fenetre.setVisible(true);
            }
        });
    }
}

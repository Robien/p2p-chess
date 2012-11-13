/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lo23.ui.grid;

import javax.swing.SwingUtilities;
import lo23.communication.ComManager;
import lo23.data.ApplicationModel;

/**
 *
 * @author Karim
 */
public class GridMain {
        static ApplicationModel myModel;


     private static ApplicationModel getModel(){
         return myModel;
     }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            public void run() {
                /*
                 * Important : All of yours interactions with managers will be develop like that :
                 *
                 * You have to create a constructor which takes parameters ApplicationModel
                 *
                 * And all your interactions will work like that :
                 *
                 * myModel.getManageYouWant().callFunctionYouWant()
                 *
                 * You can find an example in ChatPanel2 or GamePanel
                 *
                 */
                myModel = new ApplicationModel();
                myModel.setGameManager(new TMP_GameManager(myModel));
             //  myModel.setProfileManager(new TMP_ProfileManager(myModel));
                //TODO : comment a cause d'un problème de compil

               //myModel.setComManager(new TMP_ComManager(null, myModel))
                MainWindow fenetre = new MainWindow(getModel());
                fenetre.setVisible(true);
            }
        });
    }
}

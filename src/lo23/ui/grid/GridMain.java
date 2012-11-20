/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lo23.ui.grid;


//import ui.grid.TMP_GameManager;
import javax.swing.SwingUtilities;
import lo23.communication.ComManager;
import lo23.data.ApplicationModel;
import lo23.data.Game;
import lo23.data.NewInvitation;
import lo23.data.Player;
import lo23.data.Profile;
import lo23.data.managers.GameManager;
import lo23.data.managers.ProfileManager;
import lo23.utils.Enums.COLOR;
import lo23.utils.Enums.STATUS;

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


                //du code tout moche de data pour faire les tests !
                        ApplicationModel app = new ApplicationModel();

        app.setGameManager(new GameManager(app));
        app.setProfileManager(new ProfileManager(app));
        Player p1 = new Player(COLOR.WHITE, 0, null);
        Player p2 = new Player(COLOR.BLACK, 0, null);

        char[] fakePassword = {};

        Profile pHost = new Profile("", "host", fakePassword, STATUS.INGAME, "", null, "", "", 21);
        Profile pGuest = new Profile("", "host", fakePassword, STATUS.INGAME, "", null, "", "", 21);
        NewInvitation inv = new NewInvitation(COLOR.BLACK, 0, pHost.getPublicProfile(), pGuest.getPublicProfile());
        Game gm = app.getGManager().createGame(inv);


                myModel = new ApplicationModel();
   //             myModel.setGameManager(new TMP_GameManager(myModel));
             //  myModel.setProfileManager(new TMP_ProfileManager(myModel));
                //TODO : comment a cause d'un problème de compil

               //myModel.setComManager(new TMP_ComManager(null, myModel))
                MainWindow fenetre = new MainWindow(getModel());
                fenetre.setVisible(true);
            }
        });
    }
}

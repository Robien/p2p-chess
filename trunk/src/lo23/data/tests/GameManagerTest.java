/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lo23.data.tests;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import lo23.data.ApplicationModel;
import lo23.data.Game;
import lo23.data.Move;
import lo23.data.NewInvitation;
import lo23.data.Position;
import lo23.data.Profile;
import lo23.data.exceptions.FileNotFoundException;
import lo23.data.exceptions.IllegalMoveException;
import lo23.data.exceptions.NoIdException;
import lo23.data.managers.GameManager;
import lo23.data.managers.ProfileManager;
import lo23.utils.Enums;

/**
 *
 * @author ksadorf
 */
public class GameManagerTest {

    static public void main(String[] args) throws IOException, NoIdException {
        getListAllGame();
    }

    static private void getListAllGame() throws IOException, NoIdException {
        ApplicationModel app;
        Profile pGuest;
        NewInvitation inv;
        Game gm;

        app = new ApplicationModel();
        app.setGameManager(new GameManager(app));
        app.setProfileManager(new ProfileManager(app));

        char[] fakePassword = {};
        String profileId = "profileid";
        Profile p;
        try {
            p = app.getPManager().createProfile(profileId, "toto", fakePassword, Enums.STATUS.CONNECTED, "", null, "michel", "titi", 22);

            if (app.getPManager().connection(profileId, fakePassword)) {
                System.out.println("Connection.");
                pGuest = new Profile("", "host", fakePassword, Enums.STATUS.INGAME, "", null, "", "", 21);
                inv = new NewInvitation(Enums.COLOR.WHITE, 300, app.getPManager().getCurrentProfile().getPublicProfile(), pGuest.getPublicProfile());

                gm = app.getGManager().createGame(inv);
                long gid = gm.getGameId(); 
                
                //
                try {
                    Move m = new Move(new Position(0, 0), new Position(0, 2), gm.getPieceAtXY(0, 0));
                    try {
                        gm.playMove(m);
                    } catch (IllegalMoveException ex) {
                        Logger.getLogger(TestInit.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    app.getGManager().save();
                    System.out.println(gid);
                    app.getGManager().getListAllGames();
                    app.getGManager().load(gid);
                    
                } catch (NoIdException expt) {
                    System.out.println(expt.getMessage());
                    System.out.println(expt.getStackTrace());
                }
            } else {
                System.out.println("Probleme lors de la connection.");
            }
        } catch (IOException ex) {
            Logger.getLogger(GameManagerTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoIdException ex) {
            Logger.getLogger(GameManagerTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GameManagerTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GameManagerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
};

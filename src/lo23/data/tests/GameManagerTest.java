/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lo23.data.tests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import lo23.data.ApplicationModel;
import lo23.data.Constant;
import lo23.data.Game;
import lo23.data.Move;
import lo23.data.NewInvitation;
import lo23.data.Position;
import lo23.data.Profile;
import lo23.data.exceptions.FileNotFoundException;
import lo23.data.exceptions.IllegalMoveException;
import lo23.data.exceptions.NoIdException;
import lo23.data.exceptions.WrongInvitation;
import lo23.data.exceptions.WrongInvitation;
import lo23.data.managers.GameManager;
import lo23.data.managers.ProfileManager;
import lo23.utils.Enums;
import lo23.utils.Enums.PLAYER_RESULT;

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
        String profileId = "MIchel";
        Profile p;
        try {
//            p = app.getPManager().createProfile(profileId, "toto", fakePassword, Enums.STATUS.CONNECTED, "", null, "michel", "titi", 22);

            if (app.getPManager().connection(profileId, fakePassword)) {

                pGuest = new Profile("idprofile", "host", fakePassword, Enums.STATUS.INGAME, "", null, "", "", 21);
                Profile phost = new Profile("idple", "host", fakePassword, Enums.STATUS.INGAME, "", null, "", "", 21);
                inv = new NewInvitation(Enums.COLOR.WHITE, 300, app.getPManager().getCurrentProfile().getPublicProfile(), pGuest.getPublicProfile());
                try {
                    gm = app.getGManager().createGame(inv);
                    long gid = gm.getGameId();
                    
                    
                    Constant c=app.getGManager().createConstant(Enums.CONSTANT_TYPE.SURRENDER);
                    app.getGManager().saveConstant(c);   
                    try {
                        PLAYER_RESULT reponse = gm.isWinner("idprofile");
                        System.out.println(reponse+" "+PLAYER_RESULT.WIN+" "+PLAYER_RESULT.LOST);
                    } catch (Exception ex) {
                        Logger.getLogger(GameManagerTest.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    app.getGManager().save();

                    ArrayList<Game> list = app.getGManager().getListAllGames();
                    for (Game g : list) {
                        g.dumpBoard();
                    }
                    System.out.println(list.size());

                } catch (NoIdException expt) {
                    System.out.println(expt.getMessage());
                    System.out.println(expt.getStackTrace());
                } catch (WrongInvitation expt) {
                    System.out.println(expt.getMessage());
                    System.out.println(expt.getStackTrace());
                }
            } else {
                System.out.println("Probleme lors de la connection.");
            }
        } catch (IOException ex) {
            Logger.getLogger(GameManagerTest.class.getName()).log(Level.SEVERE, null, ex);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GameManagerTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GameManagerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
};

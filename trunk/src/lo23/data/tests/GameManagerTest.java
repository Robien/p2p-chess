/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lo23.data.tests;

import java.io.IOException;
import lo23.data.ApplicationModel;
import lo23.data.Game;
import lo23.data.NewInvitation;
import lo23.data.Profile;
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
        Profile pHost, pGuest;
        NewInvitation inv;
        Game gm;

        app = new ApplicationModel();
        app.setGameManager(new GameManager(app));
        app.setProfileManager(new ProfileManager(app));

        char[] fakePassword = {};
        String profileId = "profileid";
        Profile p = app.getPManager().createProfile(profileId, "toto", fakePassword, Enums.STATUS.CONNECTED, "", null, "michel", "titi", 22);
        /*if (app.getPManager().connection(profileId, fakePassword)) {

            pGuest = new Profile("", "host", fakePassword, Enums.STATUS.INGAME, "", null, "", "", 21);
            inv = new NewInvitation(Enums.COLOR.WHITE, 300, p.getPublicProfile(), pGuest.getPublicProfile());
            gm = app.getGManager().createGame(inv);

            //
            try {
                app.getGManager().save();
            } catch (NoIdException expt) {
                System.out.println(expt.getMessage());
                System.out.println(expt.getStackTrace());
            }
        } else {
            System.out.println("Probleme lors de la connection.");
        }*/
    }
};

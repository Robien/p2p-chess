/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lo23.data.tests;

import lo23.data.ApplicationModel;
import lo23.data.Game;
import lo23.data.NewInvitation;
import lo23.data.Player;
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

    static public void main(String[] args) {
        getListAllGame();
    }

    static private void getListAllGame() {
        ApplicationModel app;
        Profile pHost, pGuest;
        NewInvitation inv;
        Game gm;

        app = new ApplicationModel();
        app.setGameManager(new GameManager(app));
        app.setProfileManager(new ProfileManager(app));

        char[] fakePassword = {};
        
        pHost = new Profile("", "host", fakePassword, Enums.STATUS.INGAME, "", null, "", "", 21);
        pGuest = new Profile("", "host", fakePassword, Enums.STATUS.INGAME, "", null, "", "", 21);
        inv = new NewInvitation(Enums.COLOR.WHITE,300, pHost.getPublicProfile(), pGuest.getPublicProfile());
        gm = app.getGManager().createGame(inv);
        
        //
        try {
            app.getGManager().save();
        } catch (NoIdException expt) {
            System.out.println(expt.getMessage());
            System.out.println(expt.getStackTrace());
        }
        catch (Exception expt) {
            System.out.println(expt.getMessage());
            System.out.println(expt.getStackTrace());
        }
        
    }
}

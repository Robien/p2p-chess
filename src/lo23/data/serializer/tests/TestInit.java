/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lo23.data.serializer.tests;

import lo23.data.ApplicationModel;
import lo23.data.Game;
import lo23.data.Invitation;
import lo23.data.NewInvitation;
import lo23.data.Player;
import lo23.data.Profile;
import lo23.data.PublicProfile;
import lo23.data.managers.GameManager;
import lo23.data.managers.ProfileManager;
import lo23.utils.Enums.COLOR;
import lo23.utils.Enums.STATUS;

/**
 *
 * @author khamidou
 * tests the initialization
 */
public class TestInit {
    public static void main(String[] args) {
        ApplicationModel app = new ApplicationModel();



        app.setGameManager(new GameManager(app));
        app.setProfileManager(new ProfileManager(app));
        Player p1 = new Player(COLOR.WHITE, 0);
        Player p2 = new Player(COLOR.BLACK, 0);

        Profile pHost = new Profile("", "host", "", STATUS.INGAME, "", null, "", "", 21);
        Profile pGuest = new Profile("", "host", "", STATUS.INGAME, "", null, "", "", 21);
        NewInvitation inv = new NewInvitation(pHost.getPublicProfile(), pGuest.getPublicProfile());
        Game gm = app.getGManager().createGame(inv);
        gm.buildPieces();
        gm.dumpBoard();
    }
}

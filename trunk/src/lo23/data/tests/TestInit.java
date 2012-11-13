/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lo23.data.tests;

import java.util.logging.Level;
import java.util.logging.Logger;
import lo23.data.ApplicationModel;
import lo23.data.Game;
import lo23.data.Invitation;
import lo23.data.Move;
import lo23.data.NewInvitation;
import lo23.data.Player;
import lo23.data.Position;
import lo23.data.Profile;
import lo23.data.PublicProfile;
import lo23.data.exceptions.IllegalMoveException;
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
    ApplicationModel app;
    Player p1, p2;
    Profile pHost, pGuest;
    NewInvitation inv;
    Game gm;

    void init() {
        app = new ApplicationModel();
        app.setGameManager(new GameManager(app));
        app.setProfileManager(new ProfileManager(app));
        p1 = new Player(COLOR.WHITE, 0, "");
        p2 = new Player(COLOR.BLACK, 0, "");

        pHost = new Profile("", "host", "", STATUS.INGAME, "", null, "", "", 21);
        pGuest = new Profile("", "host", "", STATUS.INGAME, "", null, "", "", 21);
        inv = new NewInvitation(pHost.getPublicProfile(), pGuest.getPublicProfile());
        gm = app.getGManager().createGame(inv);
        gm.buildPieces();
        gm.dumpBoard();

        Move m = new Move(new Position(0, 0), new Position(0, 2), gm.getPieceAtXY(0, 0));
        try {
            gm.playMove(m);
        } catch (IllegalMoveException ex) {
            Logger.getLogger(TestInit.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(">>>> After Move <<<<<");
        gm.dumpBoard();
    }
    
}

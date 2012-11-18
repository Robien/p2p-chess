/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lo23.data.serializer.tests;

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
    public static void main(String[] args) {
        ApplicationModel app = new ApplicationModel();



        app.setGameManager(new GameManager(app));
        app.setProfileManager(new ProfileManager(app));
        Player p1 = new Player(COLOR.WHITE, 0, null);
        Player p2 = new Player(COLOR.BLACK, 0, null);
      
        char[] fakePassword = {};
        
        Profile pHost = new Profile("", "host", fakePassword, STATUS.INGAME, "", null, "", "", 21);
        Profile pGuest = new Profile("", "host", fakePassword, STATUS.INGAME, "", null, "", "", 21);
        NewInvitation inv = new NewInvitation(pHost.getPublicProfile(), pGuest.getPublicProfile());
        Game gm = app.getGManager().createGame(inv);
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

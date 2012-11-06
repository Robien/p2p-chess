/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lo23.data.serializer.tests;

import lo23.data.ApplicationModel;
import lo23.data.Game;
import lo23.data.Player;
import lo23.data.managers.GameManager;
import lo23.data.managers.ProfileManager;
import lo23.utils.Enums.COLOR;

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
        Game gm = new Game();
        gm.buildPieces();
        gm.dumpBoard();
    }
}

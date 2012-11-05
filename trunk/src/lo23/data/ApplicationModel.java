/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lo23.data;

import lo23.communication.ISender;
import lo23.data.managers.GameManagerInterface;
import lo23.data.managers.ProfileManagerInterface;

/**
 *
 * @author Noé Gaumont
 */
public class ApplicationModel {

    private GameManagerInterface gManager;
    private ProfileManagerInterface pManager;
    private ISender comManager;

    ApplicationModel(GameManagerInterface gManager, ProfileManagerInterface pManager, ISender comManager) {
        this.gManager = gManager;
        this.pManager = pManager;
        this.comManager = comManager;
    }

    GameManagerInterface getGManager() {
        return gManager;
    }

    ProfileManagerInterface getPManager() {
        return pManager;
    }

    ISender getISender() {
        return comManager;
    }
}

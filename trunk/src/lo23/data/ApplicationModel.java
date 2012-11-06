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

    public ApplicationModel(GameManagerInterface gManager, ProfileManagerInterface pManager, ISender comManager) {
        this.gManager = gManager;
        this.pManager = pManager;
        this.comManager = comManager;
    }
    
    public GameManagerInterface getGManager() {
        return gManager;
    }

    public ProfileManagerInterface getPManager() {
        return pManager;
    }

    public ISender getSender() {
        return comManager;
    }
}

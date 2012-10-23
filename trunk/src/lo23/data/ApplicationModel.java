/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lo23.data;

import lo23.data.managers.GameManagerInterface;
import lo23.data.managers.ProfileManagerInterface;

/**
 *
 * @author Noé Gaumont
 */
public class ApplicationModel {
    
    private GameManagerInterface  gManager;
    private ProfileManagerInterface pManager;

    
    ApplicationModel(GameManagerInterface gManager,ProfileManagerInterface pManager){
        this.gManager=gManager;
        this.pManager=pManager;
    }
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lo23.data.tests;

import lo23.data.ApplicationModel;
import lo23.data.managers.GameManager;


/**
 *
 * @author ksadorf
 */
public class GameManagerTest {
    static public void main(String[] args)
    {

        
        

        
    }
    
    static private void getListAllGame(){
        ApplicationModel app = new ApplicationModel();
        app.setGameManager(new GameManager(app));
        
    }
}

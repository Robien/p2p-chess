/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lo23.ui.login.tests;

import java.util.ArrayList;
import javax.swing.JButton;
import lo23.data.ApplicationModel;
import lo23.ui.login.IHMList;
import lo23.ui.login.IhmConnexionWindow;
import lo23.ui.login.IhmListGames;
import lo23.ui.login.IhmLoginModel;
import lo23.ui.login.mockManager.CommManagerMock;
import lo23.ui.login.mockManager.GameManagerMock;
import lo23.ui.login.mockManager.ProfileManagerMock;
import org.uispec4j.Trigger;
import org.uispec4j.UISpec4J;
import org.uispec4j.UISpecTestCase;
import org.uispec4j.interception.WindowHandler;
import org.uispec4j.interception.WindowInterceptor;

/**
 *
 * @author marcrossi
 */
public class IhmListGamesTest extends UISpecTestCase {
    
    static IhmListGames listFrame;
    static IhmLoginModel ihmLoginModel;
            
    static {
       UISpec4J.init();
    }
    
    public static void main(String args[]) {
        
        testInitialState();
        
        // Test de la connexion avec différentes valeurs erronées
        testListEndGames();
        
        // Test de la connexion avec différentes valeurs correctes
//        testConnectionCorrect();
        
    }
    
    
    public static void testInitialState() {
        
        //Instantiate DataManager
        ApplicationModel appModel = new ApplicationModel();
        appModel.setComManager(new CommManagerMock(appModel));
        appModel.setGameManager((new GameManagerMock((appModel))));
        appModel.setProfileManager(new ProfileManagerMock(appModel));
        //Instantiate IhmLoginModel
        ihmLoginModel = new IhmLoginModel(appModel);
        IHMList listplayers = new IHMList(ihmLoginModel);

        listFrame = new IhmListGames(ihmLoginModel, listplayers);
    }
    
    public static void testListEndGames() {
        
        System.out.println("***** TEST LIST END GAME *****");
        // 1er test
        ArrayList<JButton> listReviewGameBtn = ihmLoginModel.getListReviewGameBtn();
        for (JButton btn : listReviewGameBtn) {
            onClickReviewBtnWindowHandlerCorrect(btn);
        }
    }
    
    public static void onClickReviewBtnWindowHandlerCorrect (final JButton btn) {
        
        // permet d'intercepter une nouvelle fenetre
        WindowInterceptor
            .init(new Trigger() {
              public void run() throws Exception {
                // ... trigger something that will cause the first window to be shown ...
                btn.doClick();
              }
            })
            .process(new WindowHandler("first dialog") {
                @Override
                public Trigger process(org.uispec4j.Window window) throws Exception {
                    System.out.println("Result --> " + window.getTitle());
//                    assertEquals(window.getTitle(), IHMList.TITLE);
//                    window.dispose();
                    return Trigger.DO_NOTHING;
                    //window.getButton(backBtnName).triggerClick();
                }
                })
            .run();
        
    }
    
}

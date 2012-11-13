/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lo23.ui.login.tests;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import lo23.data.ApplicationModel;
import lo23.ui.login.IHMListe;
import lo23.ui.login.IHMListe;
import lo23.ui.login.IhmConnexionWindow;
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
public class IhmConnexionWindowTest extends UISpecTestCase {
    
    static IhmConnexionWindow loginFrame;
            
    static {
       UISpec4J.init();
    }
    
    public static void main(String args[]) {
        
        testInitialState();
        
        // Test de la connexion avec différentes valeurs erronées
        testConnectionError();
        
        // Test de la connexion avec différentes valeurs correctes
        testConnectionCorrect();
        
    }
    
    
    public static void testInitialState() {
        
        //Instantiate DataManager
        ApplicationModel appModel = new ApplicationModel();
        appModel.setComManager(new CommManagerMock(appModel));
        appModel.setGameManager(new GameManagerMock((appModel)));
        appModel.setProfileManager(new ProfileManagerMock(appModel));
        
        //Instantiate IhmLoginModel
        IhmLoginModel ihmLoginModel = new IhmLoginModel(appModel);

        loginFrame = new IhmConnexionWindow(ihmLoginModel);
    }
    
    public static void testConnectionCorrect() {
        
        System.out.println("***** TEST CONNECTION CORRECT *****");
        
        // 1er test
        loginFrame.getLoginField().setText("admin");
        loginFrame.getPasswordField().setText("admin");
        onClickConnectBtnWindowHandlerCorrect("Disconnect");
        
        // 2eme test
        testInitialState();
        loginFrame.getLoginField().setText("admin");
        loginFrame.getPasswordField().setText("admin");
        onClickConnectBtnWindowHandlerCorrect("Disconnect");
        
    }
    
    public static void testConnectionError() {
        
        System.out.println("***** TEST CONNECTION AVEC ERREURS *****");
        
        // 1er test
        onClickConnectBtnWindowHandlerError("OK");
        
        // 2eme Test
        loginFrame.getLoginField().setText("toto");
        loginFrame.getPasswordField().setText("titi");
        onClickConnectBtnWindowHandlerError("OK");
    }
    
    public static void onClickConnectBtnWindowHandlerError (final String backBtnName) {
        
        // permet d'intercepter une nouvelle fenetre
        WindowInterceptor
            .init(new Trigger() {
              public void run() throws Exception {
                // ... trigger something that will cause the first window to be shown ...
                loginFrame.getConnectBtn().doClick();
              }
            })
            .process(new WindowHandler("first dialog") {
                @Override
                public Trigger process(org.uispec4j.Window window) throws Exception {
                    System.out.println("Result --> " + window.getTitle());
                    assertEquals(window.getTitle(), "Login error");
                    return window.getButton(backBtnName).triggerClick();
                }
                })
            .run();
    }
    
    public static void onClickConnectBtnWindowHandlerCorrect (final String backBtnName) {
        
        // permet d'intercepter une nouvelle fenetre
        WindowInterceptor
            .init(new Trigger() {
              public void run() throws Exception {
                // ... trigger something that will cause the first window to be shown ...
                loginFrame.getConnectBtn().doClick();
              }
            })
            .process(new WindowHandler("first dialog") {
                @Override
                public Trigger process(org.uispec4j.Window window) throws Exception {
                    System.out.println("Result --> " + window.getTitle());
                    assertEquals(window.getTitle(), IHMListe.TITLE);
                    window.dispose();
                    return Trigger.DO_NOTHING;
                    //window.getButton(backBtnName).triggerClick();
                }
                })
            .run();
        
    }
    
}

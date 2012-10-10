/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lo23.ui.login;

import javax.swing.JOptionPane;

/**
 *
 * @author marcrossi
 */
public class Connect {
    
    public Connect (String login, String password) {
        
    } 
    
    static int Connection(String login, String password) {
        int statusCode = 0;

        // Test si champs vides
        if (login.isEmpty() || password.isEmpty()) {
            statusCode = 0;
        }
        // TODO tester dans le fichier de profil
        
        if(statusCode==0) {
            JOptionPane.showMessageDialog(null, "Connection failed !", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        return statusCode;
    } 
}

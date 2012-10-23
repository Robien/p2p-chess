/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lo23.ui.grid;

import javax.swing.SwingUtilities;

/**
 *
 * @author Karim
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            public void run() {
                MainWindow fenetre = new MainWindow();
                fenetre.setVisible(true);
            }
        });
    }
}

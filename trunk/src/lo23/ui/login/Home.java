/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lo23.ui.login;

import javax.swing.SwingUtilities;

/**
 *
 * @author marcrossi
 */
public class Home {
	public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				HomeWindow fenetre = new HomeWindow();
				fenetre.setVisible(true);
                               
			}
		});
	}
}


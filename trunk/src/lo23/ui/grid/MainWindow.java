/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lo23.ui.grid;

/**
 *
 * @author Karim
 */

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import lo23.data.ApplicationModel;

public class MainWindow extends JFrame implements ActionListener {
    ApplicationModel myModel;
    
    public MainWindow(ApplicationModel m) {
        super();
        myModel = m;
        build();//On initialise notre fenêtre

    }

    private void build() {
        setTitle("Chess P2P"); //On donne un titre à l'application
        setSize(1020, 750); //On donne une taille à notre fenêtre
        setLocationRelativeTo(null); //On centre la fenêtre sur l'écran
        setResizable(false); //On interdit la redimensionnement de la fenêtre
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //On dit à l'application de se fermer lors du clic sur la croix

        setContentPane(buildContentPanel());
    }

    private JPanel buildContentPanel() {

        //Panel global
        JPanel panel = new JPanel();
        GridBagLayout gridBagLayout = new GridBagLayout();
        panel.setLayout(gridBagLayout);

        GridBagConstraints constraints = new GridBagConstraints();

        constraints.fill = GridBagConstraints.HORIZONTAL;

        //Le panneau est représenté par un tableau de 6*8 cases


        // Grille
        GamePanel gamePanel = new GamePanel();
        constraints.insets = new Insets(0,0,0,0);
	constraints.gridwidth = 4;
	constraints.gridheight = 4;
	constraints.gridx = 1;
	constraints.gridy = 1;

        panel.add(gamePanel, constraints);

        //Test Ajout Timer, à remplacer par profilPanel
        constraints.gridwidth = 6;
	constraints.gridheight = 1;
	constraints.gridx = 1;
	constraints.gridy = 0;

        TimerPanel timerPanel = new TimerPanel();
        panel.add(timerPanel, constraints);

        //Test Ajout Timer
        constraints.gridwidth = 6;
	constraints.gridheight = 1;
	constraints.gridx = 1;
	constraints.gridy = 5;

        TimerPanel timerPanel2 = new TimerPanel();
        panel.add(timerPanel2, constraints);

        //Chat panel
        constraints.insets = new Insets(50,50,0,0);
        constraints.gridwidth = 2;
	constraints.gridheight = 6;
	constraints.gridx = 6;
	constraints.gridy = 0;

        ChatPanel2 chatPanel = new ChatPanel2(myModel);
        panel.add(chatPanel, constraints);
       
        return panel;
    }

//    private GridBagConstraints modifyConstraint(int width, float weightx, float weighty, int height) {
//        GridBagConstraints c = new GridBagConstraints();
//        c.gridwidth = width;
//        c.weightx = weightx;
//        c.weighty = weighty;
//        c.gridheight = height;
//        c.fill = GridBagConstraints.HORIZONTAL;
//
//        return c;
//    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
    }
 }

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lo23.ui.grid;

/**
 *
 * @author Karim
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainWindow extends JFrame implements ActionListener {

    public MainWindow() {
        super();
        build();//On initialise notre fenêtre
    }

    private void build() {
        setTitle("Chess P2P"); //On donne un titre à l'application
        setSize(1020, 750); //On donne une taille à notre fenêtre
        setLocationRelativeTo(null); //On centre la fenêtre sur l'écran
        setResizable(false); //On interdit la redimensionnement de la fenêtre
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //On dit à l'application de se fermer lors du clic sur la croix

        setContentPane(buildContentPanel());

        //Test Ajout Timer
        TimerPanel timerPanel = new TimerPanel();
        this.getContentPane().add(timerPanel);

    }

    private JPanel buildContentPanel() {

        // Grille
        GamePanel gamePanel = new GamePanel();
        JPanel panel = new JPanel();
        BorderLayout mainLayout = new BorderLayout();
       
        panel.setLayout(mainLayout);
        
        panel.add(gamePanel, mainLayout.CENTER);
       
        return panel;
    }

    private GridBagConstraints modifyConstraint(int width, float weightx, float weighty, int height) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = width;
        c.weightx = weightx;
        c.weighty = weighty;
        c.gridheight = height;
        c.fill = GridBagConstraints.HORIZONTAL;

        return c;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
    }
 }

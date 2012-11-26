/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lo23.ui.grid;

/**
 *
 * @author Karim
 */
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import lo23.data.ApplicationModel;
import lo23.data.Game;
import lo23.data.Player;
import lo23.ui.grid.PlayerPanel;

public class MainWindow extends JFrame implements ActionListener {
    ApplicationModel myModel;
    Game game;
    
    public MainWindow(ApplicationModel m) {
        super();

        //Launch the Sound
//       during_party = new Launch_Sound("chess.wav");
//       during_party.play();


        myModel = m;
  
        build();//On initialise notre fenêtre

    }
 
    public MainWindow(ApplicationModel m, Game gm) {
        super();
            
       //Launch the Sound
//       during_party = new Launch_Sound("chess.wav");
//       during_party.play();
   
       
        myModel = m;
        game = gm;
        
        build();//On initialise notre fenêtre
      
    }

    private void build() {
        setTitle("Chess P2P"); //On donne un titre à l'application
       
        setSize(GridConstants.WINDOW_WIDTH, GridConstants.WINDOW_HEIGHT); //On donne une taille à notre fenêtre
        setLocationRelativeTo(null); //On centre la fenêtre sur l'écran
        setResizable(false); //On interdit la redimensionnement de la fenêtre
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //On dit à l'application de se fermer lors du clic sur la croix
      
        setContentPane(buildContentPanel());
      
        //create the Menu	 
     
        
        Menu menu = new Menu(this);
        
     }
    
    private JPanel buildContentPanel() {

        //Global Panel
        JPanel panel = new JPanel();
        GridBagLayout gridBagLayout = new GridBagLayout();
        panel.setLayout(gridBagLayout);
        
     
//  
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.fill = GridBagConstraints.HORIZONTAL;

        //The panel is a grid of 6*8 squares

        //Grid
        final GamePanel gamePanel = new GamePanel(myModel, game);
        constraints.insets = new Insets(0,0,0,0);
		constraints.gridwidth = 4;
		constraints.gridheight = 4;
		constraints.gridx = 1;
		constraints.gridy = 1;
        panel.add(gamePanel, constraints);
        


        //Test Ajout Timer, a remplacer par profilPanel
        constraints.gridwidth = 6;
		constraints.gridheight = 1;
		constraints.gridx = 1;
		constraints.gridy = 0;

        //TimerPanel timerPanel = new TimerPanel();
        //panel.add(timerPanel, constraints);

      //   PlayerPanel remotePlayerPanel = new PlayerPanel(myModel, myModel.getGManager().getCurrentGame().getRemotePlayer());
      // tmp :
           PlayerPanel remotePlayerPanel = new PlayerPanel(myModel);
           panel.add(remotePlayerPanel, constraints);


        //Test Ajout Timer
        constraints.gridwidth = 6;
		constraints.gridheight = 1;
		constraints.gridx = 1;
		constraints.gridy = 5;

         //PlayerPanel localPlayerPanel = new PlayerPanel(myModel, myModel.getGManager().getCurrentGame().getRemotePlayer());
         PlayerPanel localPlayerPanel = new PlayerPanel(myModel);

         panel.add(localPlayerPanel, constraints);

        //Chat panel
        constraints.insets = new Insets(50,50,0,0);
        constraints.gridwidth = 2;
		constraints.gridheight = 6;
		constraints.gridx = 6;
		constraints.gridy = 0;

        ChatPanel2 chatPanel = new ChatPanel2(myModel);
        panel.add(chatPanel, constraints);
        
        
        //Menu
        
        
        
        return panel;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        
    }
 
      
}

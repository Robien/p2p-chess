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
import java.awt.event.ActionEvent;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.PopupMenu;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

import lo23.data.ApplicationModel;
import lo23.data.Player;
import lo23.ui.grid.PlayerPanel;

public class MainWindow extends JFrame implements ActionListener {
    ApplicationModel myModel;
    Launch_Sound pendant_partie;
    boolean is_full_screen;


    public MainWindow(ApplicationModel m) {
        super();
            
        //Launch the Sound
       pendant_partie = new Launch_Sound();
       pendant_partie.play();
      
        myModel = m;
        build();//On initialise notre fenêtre
       
    }

    private void build() {
        setTitle("Chess P2P"); //On donne un titre à l'application
        setSize(GridConstants.WINDOW_WIDTH, GridConstants.WINDOW_HEIGHT); //On donne une taille à notre fenêtre
        setLocationRelativeTo(null); //On centre la fenêtre sur l'écran
        setResizable(false); //On interdit la redimensionnement de la fenêtre
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //On dit à l'application de se fermer lors du clic sur la croix
        is_full_screen = false;
        setContentPane(buildContentPanel());
        
        	//this.setSize(new Dimension(400,400));
               
                Menu();
    }
    
    private JPanel buildContentPanel() {

        //Global Panel
        JPanel panel = new JPanel();
        GridBagLayout gridBagLayout = new GridBagLayout();
        panel.setLayout(gridBagLayout);

        GridBagConstraints constraints = new GridBagConstraints();

        constraints.fill = GridBagConstraints.HORIZONTAL;

        //The panel is a grid of 6*8 squares

        //Grid
        final GamePanel gamePanel = new GamePanel(myModel);
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

    private void Menu() 
    {
        
     	JMenuBar menu=new JMenuBar();
        JMenu fichier=new JMenu("Fichier");
        JMenu options=new JMenu("Options");
        JMenu son=new JMenu("Sound");
        JMenuItem new_game=new JMenuItem("New Game");
        JMenuItem full_screen=new JMenuItem("Full screen");
        
        JRadioButtonMenuItem stop_music= new JRadioButtonMenuItem("stop music");
        JRadioButtonMenuItem play_music = new JRadioButtonMenuItem("play music");
        JMenuItem close=new JMenuItem("Quit");
        //fichier 
        fichier.add(new_game);
        fichier.addSeparator();
        fichier.add(close);
         //options
        options.add(full_screen);
                
        //Radio buttons sound
         ButtonGroup bg = new ButtonGroup();
        
         bg.add(play_music);
         bg.add(stop_music);
        
         play_music.setSelected(true);
       
         son.add(play_music);
         son.add(stop_music);
      
       //accelerator
        fichier.setMnemonic('F');
        close.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_MASK));
        options.setMnemonic('O');
        full_screen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F8,0));
       
        
         //add in the Menu
        menu.add(fichier);
        menu.add(options);
        menu.add(son);
        this.setJMenuBar(menu); 
      
      
        
        //Listeners
        Stop_music stopm=new Stop_music();
        stop_music.addActionListener(stopm); 
        
        Play_music playm=new Play_music();
        play_music.addActionListener(playm); 
        
        Quit quit=new Quit();
        close.addActionListener(quit);

        Full screen=new Full();
        full_screen.addActionListener(screen);
          
    
         
       
       
 }

    private  class Quit implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
            } 
        }  
    
     private  class Stop_music implements ActionListener{

         @Override
        public void actionPerformed(ActionEvent e)
            {
                 // pendant_partie.play();
                  pendant_partie.pause();
            } 
        }  
     
      private  class Play_music implements ActionListener{

          @Override
        public void actionPerformed(ActionEvent e)
            {
                 // pendant_partie.play();
                    pendant_partie.play();
            } 
        }
      
      private  class Full implements ActionListener{

          @Override
        public void actionPerformed(ActionEvent e)
            {
                if(is_full_screen == false)
                {  
                  Dimension tailleEcran = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
                  setLocation(0,0);
                  setSize(tailleEcran);
                  is_full_screen = true;
                } 
                else
                {
                  setSize(GridConstants.WINDOW_WIDTH, GridConstants.WINDOW_HEIGHT); //On donne une taille à notre fenêtre
                  setLocationRelativeTo(null); //On centre la fenêtre sur l'écran
                  is_full_screen = false;
                }
            } 
        }  
    
    }

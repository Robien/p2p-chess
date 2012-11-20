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

public class MainWindow extends JFrame implements ActionListener {
    ApplicationModel myModel;
    Launch_Sound during_party;    // launch the background sound
    boolean is_full_screen;      //control if the screen is in full screen
    static boolean noise_on;
     
    public MainWindow(ApplicationModel m) {
        super();
            
       //Launch the Sound
       during_party = new Launch_Sound("chess.wav");
       during_party.play();
   
       
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
        noise_on = true;
        setContentPane(buildContentPanel());
      
        //create the Menu	 
        Menu();
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
        JMenu file=new JMenu("File");
        JMenu options=new JMenu("Options");
        JMenu son=new JMenu("Sound");
        JMenu other=new JMenu("?");
        JMenuItem new_game=new JMenuItem("New Game");
        JMenuItem full_screen=new JMenuItem("Full screen");
        JMenuItem rules=new JMenuItem("Rules of chess");
        JMenuItem about=new JMenuItem("About");
        JRadioButtonMenuItem stop_music= new JRadioButtonMenuItem("Stop music      (╯°□°)╯︵ ┻━┻");
        JRadioButtonMenuItem play_music = new JRadioButtonMenuItem("Play music      ♥‿♥");
        JRadioButtonMenuItem stop_noise= new JRadioButtonMenuItem("Stop sound effects      (ʃ˘̩̩ε˘̩ƪ)");
        JRadioButtonMenuItem play_noise = new JRadioButtonMenuItem("Make some noises        ＼(^O^)／");
        
        JMenuItem close=new JMenuItem("Quit");
        //fichier 
        file.add(new_game);
        file.addSeparator();
        file.add(close);
         //options
        options.add(rules); 
        options.addSeparator();
        options.add(full_screen);
      
       
        //Radio buttons sound
        ButtonGroup bg = new ButtonGroup();
         ButtonGroup bg2 = new ButtonGroup();
         bg.add(play_music);
         bg.add(stop_music);
         play_music.setSelected(true);
         bg2.add(play_noise);
         bg2.add(stop_noise);
         play_noise.setSelected(true);
         
         son.add(play_music);
         son.add(stop_music);
         son.addSeparator();
         son.add(play_noise);
         son.add(stop_noise);
      
        //other
        other.add(about);
       //accelerator
        file.setMnemonic('F');
        close.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_MASK));
        options.setMnemonic('O');
        full_screen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F11,0));
        son.setMnemonic('S');
        
 
        //add in the Menu
        menu.add(file);
        menu.add(options);
        menu.add(son);
        menu.add(other);
        this.setJMenuBar(menu); 
      
      
        
        //Listeners
        Stop_music stopm=new Stop_music();
        stop_music.addActionListener(stopm); 
        
        Play_music playm=new Play_music();
        play_music.addActionListener(playm); 
        
        Stop_noise stopn=new Stop_noise();
        stop_noise.addActionListener(stopn); 
        
        Play_noise playn=new Play_noise();
        play_noise.addActionListener(playn); 
        
        
        
        Quit quit=new Quit();
        close.addActionListener(quit);

        Full screen=new Full();
        full_screen.addActionListener(screen);
          
    
        Rules rul=new Rules();
        rules.addActionListener(rul);
        
        About ab=new About();
        about.addActionListener(ab);
       
        
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
                 
                  during_party.pause();
            } 
        }  
     
      private  class Play_music implements ActionListener{

          @Override
        public void actionPerformed(ActionEvent e)
            {
                 
                    during_party.play();
            } 
        }
      
        private  class Play_noise implements ActionListener{

          @Override
        public void actionPerformed(ActionEvent e)
            {
                 
                   set_noise_on(true);
            } 
        }
         private  class Stop_noise implements ActionListener{

          @Override
        public void actionPerformed(ActionEvent e)
            {
                 
                   set_noise_on(false);
            } 
        }
      
      private  class Full implements ActionListener
      {
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
      private  class Rules extends JFrame implements ActionListener
      {
        @Override
        public void actionPerformed(ActionEvent e)
            {
               JOptionPane.showMessageDialog(this,
            "Chess is played on a square board of eight rows (called ranks and denoted with numbers 1 to 8) and eight columns (called files and denoted with letters a to h) of squares."
            +"\nThe colors of the sixty-four squares alternate and are referred to as \"light squares\" and \"dark squares\". The chessboard is placed with a light square at the right-hand"
            + "\nend of the rank nearest to each player, and the pieces are set out as shown in the diagram, with each queen on its own color. The pieces are divided, by convention,"
            + "\ninto white and black sets. The players are referred to as \"White\" and \"Black\", and each begins the game with sixteen pieces of the specified color. "
            + "\nThese consist of one king, one queen, two rooks, two bishops, two knights, and eight pawns."
            + "\n\nMovement\n"
            + "\nWhite always moves first. After the initial move, the players alternately move one piece at a time (with the exception of castling, when two pieces are moved)."
            + "\nPieces are moved to either an unoccupied square or one occupied by an opponent's piece, which is captured and removed from play. With the sole exception of en passant,"
            + "\nall pieces capture opponent's pieces by moving to the square that the opponent's piece occupies."
            + "\nA player may not make any move that would put or leave his king under attack. If the player to move has no legal moves, the game is over; "
            + "\nit is either a checkmate (a loss for the player with no legal moves)"
            + "\n—if the king is under attack—or a stalemate (a draw)—if the king is not."
            + "\nEach chess piece has its own style of moving. In the diagrams, the dots mark the squares where the piece can move if no other pieces "
            + "\n(including one's own piece) are on the squares between the piece's initial position and its destination." 
            +"\nThe king moves one square in any direction. The king has also a special move which is called castling and involves also moving a rook."
            +"\nThe rook can move any number of squares along any rank or file, but may not leap over other pieces. Along with the king, the rook is involved during the king's castling move."
            +"\n The bishop can move any number of squares diagonally, but may not leap over other pieces."
            +"\nThe queen combines the power of the rook and bishop and can move any number of squares along rank, file, or diagonal, but it may not leap over other pieces."
            +"\nThe knight moves to any of the closest squares that are not on the same rank, file, or diagonal, thus the move forms an \"L\""
            +"\n-shape: two squares vertically and one square horizontally, or two squares horizontally and one square vertically."
            +"\nThe knight is the only piece that can leap over other pieces. The pawn may move forward to the unoccupied square immediately in front of it on the same file;"
            + "\nor on its first move it may advance two squares along the same file provided both squares are unoccupied; "
            +"\nor it may move to a square occupied by an opponent's piece which is diagonally in front of it on an adjacent file, capturing that piece."
            + "\nThe pawn has two special moves: the en passant capture and pawn promotion."
    ,"Rules of Chess",
            JOptionPane.INFORMATION_MESSAGE,new ImageIcon(getClass().getClassLoader().getResource(".").getPath() + "lo23/ui/resources/KW.png"));
            }  
      }
      
       private  class About extends JFrame implements ActionListener
       {

       @Override
       public void actionPerformed(ActionEvent e)
           {
               JOptionPane.showMessageDialog(this,
            "Game developed by UTC Students"
             + "\n" + "        LO23 Project - 2012", "About",
            JOptionPane.INFORMATION_MESSAGE, new ImageIcon(getClass().getClassLoader().getResource(".").getPath() + "lo23/ui/resources/logo_utc.png"));
           }
       }
       
       
       
       
     
        
    private static void setWindowsLook() {
        LookAndFeel lf = UIManager.getLookAndFeel();

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
  

    }
    
    
        public static boolean get_noise_on()
        {
            return noise_on;
        }
        
       public void set_noise_on(boolean t)
        {
             noise_on = t;
           
        }
;
    }

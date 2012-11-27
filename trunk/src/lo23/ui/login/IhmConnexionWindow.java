package lo23.ui.login;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import lo23.data.ApplicationModel;
import lo23.data.PublicProfile;
import lo23.data.exceptions.ProfileIdAlreadyExistException;
import lo23.data.exceptions.ProfilePseudoAlreadyExistException;
import lo23.data.managers.ProfileManagerInterface;
import lo23.ui.login.mockManager.CommManagerMock;
import lo23.ui.login.mockManager.GameManagerMock;
import lo23.ui.login.mockManager.ProfileManagerMock;

/**
 * IhmConnexionWindow_old : interface de connexion (login) à l'application
 * Première fenetre de l'application
 *
 * Le design a été créé via l'assistant design de netbeans
 *
 * @author marcrossi
 */
public class IhmConnexionWindow extends javax.swing.JFrame implements PropertyChangeListener {
    
    
    public static final String REFRESH_LIST = "refresh-list";

    private IhmLoginModel ihmLoginModel;
    private javax.swing.JButton connectBtn;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton loadProfileBtn;
    private javax.swing.JComboBox loginCombo;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JButton registerBtn;

    static String TITLE = "Chess-P2P";
    // End of variables declaration

    /**
     * Creates new form IhmConnexionWindow_old
     */
    public IhmConnexionWindow(IhmLoginModel ihmLoginModel) {
        
        this.ihmLoginModel = ihmLoginModel;
        ihmLoginModel.addPropertyChangeListener(this.REFRESH_LIST,this);
        initComponents();
        setSize(360, 500);
        setResizable(false);
        setLocationRelativeTo(null); //On centre la fenêtre sur l'écran
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                //Instantiate DataManager
                ApplicationModel appModel = new ApplicationModel();
                appModel.setComManager(new CommManagerMock(appModel));
                appModel.setGameManager(new GameManagerMock((appModel)));
                appModel.setProfileManager(new ProfileManagerMock(appModel));
                //Instantiate IhmLoginModel
                IhmLoginModel ihmLoginModel = new IhmLoginModel(appModel);
                
               // new IhmConnexionWindow_old(ihmLoginModel).setVisible(true);
                new IhmConnexionWindow(ihmLoginModel).setVisible(true);
                ihmLoginModel.refreshProfileList();
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle(TITLE);

        // Ajout du Jpanel
        setContentPane(buildContentPanel());
        
        // set connectBtn comme bouton par défaut (répond à la touche ENTER)
        this.getRootPane().setDefaultButton(connectBtn); 
    }

    private JPanel buildContentPanel() {

        jPanel1 = new javax.swing.JPanel()
        {
            // Création de l'image d'arrière plan
            @Override
            public void paint(Graphics g) {
                URL backgroundPath = IhmConnexionWindow.class.getResource("/lo23/ui/resources/backgroundLogin.jpg");
                try {
                    BufferedImage image = ImageIO.read(backgroundPath);
                    g.drawImage(image, 0, 0, null);
                    super.paint(g);
                } catch (IOException e) {
                    System.out.println("DEBUG : Image Non Chargee. URI: "+backgroundPath);
                    e.printStackTrace();
                    super.paint(g);
                }
            };
        };
        jPanel1.setOpaque(false);

        // Fields
        
        loginCombo = new javax.swing.JComboBox();
        passwordField = new javax.swing.JPasswordField();
        passwordField.setText("");

        // Buttons
        connectBtn = new javax.swing.JButton();
        loadProfileBtn = new javax.swing.JButton();
        registerBtn = new javax.swing.JButton();

        // Styles
        connectBtn.setFont(new java.awt.Font("Comic Sans MS", 0, 14));
        connectBtn.setText("Connect");
        loadProfileBtn.setFont(new java.awt.Font("Comic Sans MS", 0, 13));
        loadProfileBtn.setText("Load Profile");
        registerBtn.setFont(new java.awt.Font("Comic Sans MS", 0, 13));
        registerBtn.setText("Register");
        registerBtn.setPreferredSize(new java.awt.Dimension(118, 29));
        registerBtn.setSelected(true);

        // Listener
        connectBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectBtnActionPerformed(evt);
            }
        });
        
        registerBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerBtnActionPerformed(evt);
            }
        });
        
        
        loadProfileBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadProfileBtnActionPerformed(evt);
            }
        });

        // Positionnement
        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1Layout.createSequentialGroup()
                .add(15, 15, 15)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(loginCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 165, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(passwordField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 162, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(jPanel1Layout.createSequentialGroup()
                        .add(loadProfileBtn, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 114, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 101, Short.MAX_VALUE)
                        .add(registerBtn, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .add(20, 20, 20))
            .add(jPanel1Layout.createSequentialGroup()
                .add(128, 128, 128)
                .add(connectBtn, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 132, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(108, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(25, 25, 25)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(loginCombo, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 36, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(passwordField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 36, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(connectBtn, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 61, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 311, Short.MAX_VALUE)
                .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(loadProfileBtn, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 37, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(registerBtn, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 37, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(24, 24, 24))
        );
        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        return jPanel1;
    }

    private void connectBtnActionPerformed(java.awt.event.ActionEvent evt) {
        // debug
        System.out.println("Login=" + getLoginCombo().getSelectedItem() + " / Password= " + new String(getPasswordField().getPassword()));

        // Appel de la methode de connexion
        ProfileManagerInterface pmi = ihmLoginModel.getApplicationModel().getPManager();
        try {
            PublicProfile selectedProfile = (PublicProfile) getLoginCombo().getSelectedItem();
            boolean ret = pmi.connection(selectedProfile.getProfileId(), getPasswordField().getPassword());
            if (ret == false) {
                JOptionPane.showMessageDialog(this, "Please make sur login and password are correct.", "Login error", JOptionPane.ERROR_MESSAGE);
            } else {
                IHMList listWindow = new IHMList(ihmLoginModel);
                this.setVisible(false);
                this.dispose();
                listWindow.setVisible(true);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
        }
    }
     private void registerBtnActionPerformed(java.awt.event.ActionEvent evt) {
         new IhmProfileWindow(ihmLoginModel,IhmProfileWindow.CREATE,null).setVisible(true);
     }
     
     private void loadProfileBtnActionPerformed(java.awt.event.ActionEvent evt){
        //open explorer to select the location
            final JFileChooser fc = new JFileChooser();
            int n = fc.showOpenDialog(this);
         
            //Si valide appele le modèle
            if(n==JFileChooser.APPROVE_OPTION){
                    String path = fc.getSelectedFile().getAbsolutePath();
                    System.out.println(path);
                    try {
                        ihmLoginModel.getApplicationModel().getPManager().importProfile(path);
                    } catch (ProfileIdAlreadyExistException ex) {
                        //TODO
                    } catch (ProfilePseudoAlreadyExistException ex) {
                        //TODO
                    } catch (Exception ex){
                        JOptionPane.showMessageDialog(this, ex.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
                    }
                    ihmLoginModel.refreshProfileList();
            }
    }

    /**
     * Méthode retournant le champ "login" du formulaire
     * @return JTextField
     */
    public JComboBox getLoginCombo() {
        return loginCombo;
    }

    /**
     * Méthode retournant le champ "password" du formulaire
     * @return JTextField
     */
    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public JButton getConnectBtn() {
        return connectBtn;
    }
    

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        if(this.isVisible()){
            if(pce.getPropertyName().equals(REFRESH_LIST)){
                PublicProfile [] profilesList;
                try {
                    profilesList = ihmLoginModel.getApplicationModel().getPManager().getLocalPublicProfiles().toArray(new PublicProfile[]{});
                    loginCombo.setModel(new DefaultComboBoxModel(profilesList));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
                }
                
            }
        }
    }


}

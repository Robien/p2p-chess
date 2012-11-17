/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lo23.ui.login;

import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import lo23.data.PublicProfile;
import lo23.data.Profile;

/**
 *
 * @author Gaëtan Grégoire & Clermont Rémi
 */
public class IhmProfileWindow extends JFrame{

    private IhmLoginModel ihmLoginModel;
    private PublicProfile publicProfile;
    
    public static final int MODIFY = 0;
    public static final int CREATE = 1;
    public static final int READ = 2;
    
    private int status;

    /**
     * Creates new form IhmProfileWindow
     * Status : Modify, create or read.
     * PublicProfile : give null for create and modify, give distantProfile for read
     */
    public IhmProfileWindow(IhmLoginModel ihmLoginModel,int status, PublicProfile publicProfile) {
        super();
        this.ihmLoginModel = ihmLoginModel;
        this.status = status;
        this.publicProfile = publicProfile;
        initComponent();
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initComponent() {
        switch(status){
            case MODIFY :
                setTitle("Gestion du profil"); //On donne un titre à l'application
                setSize(400, 450); //On donne une taille à notre fenêtre
                break;
            case READ :
                setTitle("Consultation d'un profil"); //On donne un titre à l'application
                setSize(400, 450); //On donne une taille à notre fenêtre
                break;
            case CREATE :
                setTitle("Création d'un profil"); //On donne un titre à l'application
                setSize(400, 450); //On donne une taille à notre fenêtre
                break;
                
            
        }
        
        
        setLocationRelativeTo(null); //On centre la fenêtre sur l'écran
        setResizable(false); //On interdit la redimensionnement de la fenêtre
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //On dit à l'application de se fermer lors du clic sur la croix

        setContentPane(initContentPanel());
    }

     private JPanel initContentPanel() {

        JPanel panel = new JPanel();
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);

        //PROFILE INFORMATION PART
        JLabel login = new JLabel();
        login.setText("Login");
        JTextField loginField = new JTextField();

        JLabel password = new JLabel();
        password.setText("Mot de passe");
        JPasswordField jPasswordField1 = new JPasswordField();

        JLabel passwordConfirm = new JLabel();
        passwordConfirm.setText("Ressaisissez votre mot de passe");
        JPasswordField jPasswordField2 = new JPasswordField();

        JLabel firstName = new JLabel();
        firstName.setText("Nom");
        JTextField firstNameField = new JTextField();
        
        JLabel lastName = new JLabel();
        lastName.setText("Prénom");
        JTextField lastNameField = new JTextField();

        JLabel age = new JLabel();
        age.setText("Âge");
        JTextField ageField = new JTextField();
        
        
        JButton exportProfileButton = new JButton();
        exportProfileButton.setText("Exporter le profil");
        // Listener
        exportProfileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportProfilePerformed(evt);
            }
        });

        JButton applyButton = new JButton();
        // Listener
        applyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                applyPerformed(evt);
            }
        });

        // The separators
        JSeparator jSeparator1 = new JSeparator();
        JSeparator jSeparator2 = new JSeparator();
   
        //TODO image operationnel
        JButton changeImageButton = new JButton();
        
        
        ImageIcon userIcon = new ImageIcon("gaetan.jpg");
        JLabel profileImage = new JLabel(userIcon);
        
         //STATISTIC PART
        JLabel gamesWonLabel = new JLabel();
        gamesWonLabel.setText("Parties gagnées");
        JLabel gamesLostLabel = new JLabel();
        gamesLostLabel.setText("Parties perdues");

        JLabel gamesDrawLabel = new JLabel();
        gamesDrawLabel.setText("Parties nulles");

        //TODO : from statistic profile
        JTextField gamesWonField = new JTextField("8");
        JTextField gamesLostField = new JTextField("9");
        JTextField gamesDrawField = new JTextField("10");
        
        gamesWonField.setEditable(false);
        gamesLostField.setEditable(false);
        gamesDrawField.setEditable(false);
        
        switch(status){
            case MODIFY :
                //TODO define text from profile
                loginField.setEditable(true);
                lastNameField.setEditable(true);
                firstNameField.setEditable(true);
                ageField.setEditable(true);
                
                Profile currentProfile = ihmLoginModel.getApplicationModel().getPManager().getCurrentProfile();
                loginField.setText(currentProfile.getPseudo());
                lastNameField.setText(currentProfile.getName());
                firstNameField.setText(currentProfile.getFirstName());
                ageField.setText(String.valueOf(currentProfile.getAge()));
                changeImageButton.setText("Changer votre avatar");
                //TODO : avatar profile
                
                
                break;
            case READ :
                //TODO define text from profile
                loginField.setEditable(false);
                lastNameField.setEditable(false);
                firstNameField.setEditable(false);
                ageField.setEditable(false);
                password.setVisible(false);
                passwordConfirm.setVisible(false);
                jPasswordField1.setVisible(false);
                jPasswordField2.setVisible(false);
                changeImageButton.setVisible(false);
                setSize(400, 450); //On donne une taille à notre fenêtre
                exportProfileButton.setVisible(false);
                applyButton.setText("Revenir à la liste des parties");
                
                loginField.setText(publicProfile.getPseudo());
                lastNameField.setText(publicProfile.getName());
                firstNameField.setText(publicProfile.getFirstName());
                ageField.setText(String.valueOf(publicProfile.getAge()));
                //TODO avatard
                
                break;
            case CREATE :
                loginField.setEditable(true);
                loginField.setEditable(true);
                lastNameField.setEditable(true);
                firstNameField.setEditable(true);
                ageField.setEditable(true);
                setSize(400, 350); //On donne une taille à notre fenêtre
                exportProfileButton.setVisible(false);
                applyButton.setText("Valider l'inscription");
                gamesWonLabel.setVisible(false);
                gamesLostLabel.setVisible(false);
                gamesDrawLabel.setVisible(false);
                gamesWonField.setVisible(false);
                gamesLostField.setVisible(false);
                gamesDrawField.setVisible(false);
                jSeparator1.setVisible(false);
                jSeparator2.setVisible(false);
                changeImageButton.setText("Selectionnez votre avatar");
                break;
        }

        
       
        



        //LAYOUT PART
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(exportProfileButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(applyButton))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(profileImage)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(changeImageButton))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(login)
                            .addComponent(age)
                            .addComponent(lastName)
                            .addComponent(firstName)
                            .addComponent(passwordConfirm)
                            .addComponent(password))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPasswordField1)
                            .addComponent(loginField)
                            .addComponent(lastNameField, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                            .addComponent(firstNameField, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                            .addComponent(ageField, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                            .addComponent(jPasswordField2, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(gamesDrawLabel)
                        .addGap(96, 96, 96)
                        .addComponent(gamesDrawField, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(gamesWonLabel)
                        .addGap(82, 82, 82)
                        .addComponent(gamesWonField, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(gamesLostLabel)
                        .addGap(84, 84, 84)
                        .addComponent(gamesLostField, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE))
                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE))
                .addGap(45, 45, 45))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(changeImageButton)
                    .addComponent(profileImage))
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(login)
                    .addComponent(loginField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(password)
                    .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(passwordConfirm)
                    .addComponent(jPasswordField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(firstName)
                    .addComponent(lastNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lastName)
                    .addComponent(firstNameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(age)
                    .addComponent(ageField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(gamesWonLabel)
                    .addComponent(gamesWonField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(gamesLostLabel)
                    .addComponent(gamesLostField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(gamesDrawLabel)
                    .addComponent(gamesDrawField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(exportProfileButton)
                    .addComponent(applyButton))
                .addContainerGap())
        );
        return panel;
    }
     
     //TODO actionListener pour les deux bouttons : switch
     private void applyPerformed(java.awt.event.ActionEvent evt) {
         switch(status){
            case MODIFY :
                //TODO set puis save
                //Puis retour liste partie
                break;
            case READ :
                //retour liste partie
                break;
            case CREATE :
                //TODO createProfile
                //Todo retour liste Partie
                break;
        }
     }
     private void exportProfilePerformed(java.awt.event.ActionEvent evt) {
         //TODO : faire ca
         //open explorer to select the location
         final JFileChooser fc = new JFileChooser();
         int returnVal = fc.showOpenDialog(this);
     }
}

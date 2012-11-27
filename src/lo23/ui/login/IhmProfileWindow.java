/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lo23.ui.login;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Arrays;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import lo23.data.Profile;
import lo23.data.PublicProfile;
import lo23.data.exceptions.NoIdException;

/**
 *
 * @author Gaëtan Grégoire & Clermont Rémi
 */
public class IhmProfileWindow extends JFrame{

    private IhmLoginModel ihmLoginModel;
    private PublicProfile publicProfile;
    
    private JTextField loginField = new JTextField();
    private JPasswordField jPasswordField1 = new JPasswordField();
    private JPasswordField jPasswordField2 = new JPasswordField();
    private JPasswordField jPasswordTestValidity = new JPasswordField("");
    private JTextField firstNameField = new JTextField();
    private JTextField lastNameField = new JTextField();
    private JTextField ageField = new JTextField();
    private JLabel profileImage = new JLabel();
    private ImageIcon icon;
    private JTextField gamesWonField = new JTextField();
    private JTextField gamesLostField = new JTextField();
    private JTextField gamesDrawField = new JTextField();
    
    private int initialHeight = 450;
    private int initialWidth = 450;
    
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
                setSize(initialWidth, initialHeight); //On donne une taille à notre fenêtre
                break;
            case READ :
                setTitle("Consultation d'un profil"); //On donne un titre à l'application
                setSize(initialWidth, initialHeight); //On donne une taille à notre fenêtre
                break;
            case CREATE :
                initialWidth = 350;
                initialHeight = 350;
                setTitle("Création d'un profil"); //On donne un titre à l'application
                setSize(initialWidth,initialHeight); //On donne une taille à notre fenêtre
                break;
                
            
        }
        
        
        setLocationRelativeTo(null); //On centre la fenêtre sur l'écran
        setResizable(false); //On interdit la redimensionnement de la fenêtre
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //On dit à l'application de se fermer lors du clic sur la croix
        JScrollPane jsp = new JScrollPane(initContentPanel());
        setContentPane(jsp);
    }

     private JPanel initContentPanel() {

        JPanel panel = new JPanel();
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);

        //PROFILE INFORMATION PART
        JLabel login = new JLabel();
        login.setText("Login");
        

        JLabel password = new JLabel();
        password.setText("Mot de passe");
        

        JLabel passwordConfirm = new JLabel();
        passwordConfirm.setText("Ressaisissez votre mot de passe");
        

        JLabel firstName = new JLabel();
        firstName.setText("Nom");
        
        
        JLabel lastName = new JLabel();
        lastName.setText("Prénom");
        

        JLabel age = new JLabel();
        age.setText("Âge");
        
        
        
        JButton exportProfileButton = new JButton();
        exportProfileButton.setText("Exporter le profil");
        // Listener
        exportProfileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportProfilePerformed(evt);
            }
        });

        JButton applyButton = new JButton();
        // set connectBtn comme bouton par défaut (répond à la touche ENTER)
        this.getRootPane().setDefaultButton(applyButton); 
        // Listener
        applyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                applyPerformed(evt);
            }
        });

        // The separators
        JSeparator jSeparator1 = new JSeparator();
        JSeparator jSeparator2 = new JSeparator();
   
        JButton changeImageButton = new JButton();	
        
        // Listener
        changeImageButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeImagePerformed(evt);
            }
        });
        
        //ImageIcon userIcon = new ImageIcon();
        //JLabel profileImage = new JLabel(userIcon);
        
         //STATISTIC PART
        JLabel gamesWonLabel = new JLabel();
        gamesWonLabel.setText("Parties gagnées");
        JLabel gamesLostLabel = new JLabel();
        gamesLostLabel.setText("Parties perdues");

        JLabel gamesDrawLabel = new JLabel();
        gamesDrawLabel.setText("Parties nulles");


        
        gamesWonField.setEditable(false);
        gamesLostField.setEditable(false);
        gamesDrawField.setEditable(false);
        
        switch(status){
            case MODIFY :
                loginField.setEditable(true);
                lastNameField.setEditable(true);
                firstNameField.setEditable(true);
                ageField.setEditable(true);
                
                Profile currentProfile = ihmLoginModel.getApplicationModel().getPManager().getCurrentProfile();
                loginField.setText(currentProfile.getPseudo());
                lastNameField.setText(currentProfile.getName());
                firstNameField.setText(currentProfile.getFirstName());
                ageField.setText(String.valueOf(currentProfile.getAge()));
                jPasswordField1.setText(new String(currentProfile.getPassword()));
                jPasswordField2.setText(new String(currentProfile.getPassword()));
                gamesWonField.setText(String.valueOf(currentProfile.getWonGames()));
                gamesLostField.setText(String.valueOf(currentProfile.getLostGames()));
                gamesDrawField.setText(String.valueOf(currentProfile.getDrawGames()));
                
                applyButton.setText("Valider");
                changeImageButton.setText("Changer votre avatar");
                try{
                    profileImage.setIcon(ihmLoginModel.getApplicationModel().getPManager().getCurrentProfile().getAvatar());
                    profileImage.repaint();
                    profileImage.setText("");
                }catch (Exception e1) {
                    // Image Inconnue
                    profileImage.setIcon(null);
                    profileImage.setText("Image Inconnue");
                }
                
                
                break;
            case READ :
                loginField.setEditable(false);
                lastNameField.setEditable(false);
                firstNameField.setEditable(false);
                ageField.setEditable(false);
                password.setVisible(false);
                passwordConfirm.setVisible(false);
                jPasswordField1.setVisible(false);
                jPasswordField2.setVisible(false);
                changeImageButton.setVisible(false);
                exportProfileButton.setVisible(false);
                applyButton.setVisible(false);
                
                
                loginField.setText(publicProfile.getPseudo());
                lastNameField.setText(publicProfile.getName());
                firstNameField.setText(publicProfile.getFirstName());
                ageField.setText(String.valueOf(publicProfile.getAge()));
                gamesWonField.setText(String.valueOf(publicProfile.getWonGames()));
                gamesLostField.setText(String.valueOf(publicProfile.getLostGames()));
                gamesDrawField.setText(String.valueOf(publicProfile.getDrawGames()));
                try{
                    profileImage.setIcon(publicProfile.getAvatar());
                    profileImage.repaint();
                    profileImage.setText("");
                }catch (Exception e1) {
                    // Image Inconnue
                    profileImage.setIcon(null);
                    profileImage.setText("Image Inconnue");
                }
                
                break;
            case CREATE :
                loginField.setEditable(true);
                loginField.setEditable(true);
                lastNameField.setEditable(true);
                firstNameField.setEditable(true);
                ageField.setEditable(true);
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
 
     //Change image
     private void changeImagePerformed(java.awt.event.ActionEvent evt) {
        JFileChooser fc = new JFileChooser();
        int n = fc.showOpenDialog(this);
        //Si valide appele le modèle
        if(n==JFileChooser.APPROVE_OPTION){
                String path = fc.getSelectedFile().getAbsolutePath();
                try {
                    BufferedImage image= ImageIO.read(new File(path));
                    //Redimentionne l'image
                     double scaleValue;
                     if(image.getHeight()>image.getWidth()){
                         scaleValue = (double)90/(double)image.getHeight();
                     }
                     else{
                         scaleValue = (double)90/(double)image.getWidth();
                     }
                    image = scale(image,scaleValue);
                    this.setSize(initialWidth, initialHeight+image.getHeight());
                    icon = new ImageIcon(image);
                    profileImage.setIcon(icon);
                    profileImage.repaint();
                    profileImage.setText("");
                }catch (Exception e1) {
                    // Image Inconnue
                    profileImage.setIcon(null);
                    profileImage.setText("Image Inconnue");
                }	
        }
     }
     
     private String RandomStringUUID() {
        // Creating a random UUID (Universally unique identifier).
        //
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();
        return randomUUIDString;
    }
     
     private void applyPerformed(java.awt.event.ActionEvent evt) {
         switch(status){
            case MODIFY :
                if(checkValidityFields()){
                    try{
                        ihmLoginModel.getApplicationModel().getPManager().getCurrentProfile().setAge(Integer.parseInt(ageField.getText()));
                    }
                    catch(Exception e){
                        ihmLoginModel.getApplicationModel().getPManager().getCurrentProfile().setAge(0);
                    }

                    ihmLoginModel.getApplicationModel().getPManager().getCurrentProfile().setFirstName(firstNameField.getText());
                    ihmLoginModel.getApplicationModel().getPManager().getCurrentProfile().setName(lastNameField.getText());
                    ihmLoginModel.getApplicationModel().getPManager().getCurrentProfile().setPseudo(loginField.getText());
                    ihmLoginModel.getApplicationModel().getPManager().getCurrentProfile().setPassword(jPasswordField1.getPassword());
                    ihmLoginModel.getApplicationModel().getPManager().getCurrentProfile().setAvatar(icon);
                    try {
                        ihmLoginModel.getApplicationModel().getPManager().saveProfile();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, ex.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
                    }
                    this.dispose();
                }
                break;

            case CREATE :
                if(checkValidityFields()){
                    int age = 0;
                    try{
                        age = Integer.parseInt(ageField.getText());
                    }
                    catch(Exception e){
                        System.out.println("Age non renseigné");
                    }
                    //IP
                    InetAddress thisIp = null;
                    try {
                        thisIp =InetAddress.getLocalHost();
                        System.out.println("IP:"+thisIp.getHostAddress());
                    }
                    catch(Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        //TODO status
                        ihmLoginModel.getApplicationModel().getPManager().createProfile(RandomStringUUID(), loginField.getText(), jPasswordField1.getPassword(), null, thisIp.getHostAddress(), icon, lastNameField.getText(), firstNameField.getText(), age);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, ex.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
                    }
                    ihmLoginModel.refreshProfileList();
                    this.dispose();
                }
                break;
        }
     }
     private boolean checkValidityFields(){
        if(!(Arrays.equals(jPasswordField1.getPassword(), jPasswordField2.getPassword())) || Arrays.equals(jPasswordTestValidity.getPassword(),jPasswordField1.getPassword())){
            JOptionPane.showMessageDialog(this, "Entrez le même mot de passe", "Password Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } 
        else{   
            if(!checkForDigit(ageField.getText())) {
                JOptionPane.showMessageDialog(this, "Entrez un age valide!", "Age Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            else{
                if(loginField.getText().isEmpty()){
                    JOptionPane.showMessageDialog(this, "Entrez un login valide!", "Login Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
                else{
                    return true;
                }
            }
         }
     }
     
     private void exportProfilePerformed(java.awt.event.ActionEvent evt) {
            //open explorer to select the location
            final JFileChooser fc = new JFileChooser();
            int n = fc.showSaveDialog(this);
         
            //Si valide appele le modèle
            if(n==JFileChooser.APPROVE_OPTION){
                String path = fc.getSelectedFile().getAbsolutePath();
                System.out.println(path);
                try {
                    ihmLoginModel.getApplicationModel().getPManager().exportProfile(path);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
                }
            }
     }
     
     private boolean checkForDigit(String s){
        boolean b = true;
        for (int i = 0; i < s.length(); i++){
            if (Character.isDigit(s.toCharArray()[i])==false){
                b = false;
                break;
            } // end if
        } // end for
        return b;
    }
     
    /** Effectue une homothétie de l'image.
     * 
     * @param bi l'image.
     * @param scaleValue la valeur de l'homothétie.
     * @return une image réduite ou agrandie.
     * 
     */public static BufferedImage scale(BufferedImage bi, double scaleValue) {
            AffineTransform tx = new AffineTransform();
            tx.scale(scaleValue, scaleValue);
            AffineTransformOp op = new AffineTransformOp(tx,AffineTransformOp.TYPE_BILINEAR);
            BufferedImage biNew = new BufferedImage( (int) (bi.getWidth() * scaleValue),
                    (int) (bi.getHeight() * scaleValue),
                    bi.getType());
            return op.filter(bi, biNew);
    } 
}

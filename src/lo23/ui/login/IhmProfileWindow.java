/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lo23.ui.login;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;

/**
 *
 * @author Gaëtan Grégoire
 */
public class IhmProfileWindow extends JFrame{

    private JButton jButton1;
    private JButton jButton2;
    private JButton jButton3;
    private JLabel login;
    private JLabel jLabel10;
    private JLabel password;
    private JLabel password_confirm;
    private JLabel firstname;
    private JLabel lastname;
    private JLabel age;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JLabel jLabel9;
    private JPasswordField jPasswordField1;
    private JPasswordField jPasswordField2;
    private JSeparator jSeparator1;
    private JSeparator jSeparator2;
    private JTextField login_field;
    private JTextField jTextField10;
    private JTextField jTextField11;
    private JTextField jTextField12;
    private JTextField jTextField13;
    private JTextField jTextField14;
    private JTextField jTextField9;
    
    /**
     * Creates new form IhmProfileWindow
     */
    public IhmProfileWindow() {
        super();
        build();
    }
    
    private void build() {
        setTitle("Gestion du profil"); //On donne un titre à l'application
        setSize(400, 450); //On donne une taille à notre fenêtre
        setLocationRelativeTo(null); //On centre la fenêtre sur l'écran
        setResizable(false); //On interdit la redimensionnement de la fenêtre
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //On dit à l'application de se fermer lors du clic sur la croix

        setContentPane(buildContentPanel());
    }
    
     private JPanel buildContentPanel() {

        JPanel panel = new JPanel();
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);
        
        login = new JLabel();
        login.setText("Login");
        
        
        password = new JLabel();
        password.setText("Mot de passe");
        
        password_confirm = new JLabel();
        password_confirm.setText("Ressaisissez votre mot de passe");
        
        firstname = new JLabel();
        firstname.setText("Nom");
        
        lastname = new JLabel();
        lastname.setText("Prénom");
        
        age = new JLabel();
        age.setText("Âge");
        
        login_field = new JTextField("1");
        
        // The separators
        jSeparator1 = new JSeparator();
        jSeparator2 = new JSeparator();
        
        jTextField9 = new JTextField("2");
        jTextField10 = new JTextField("3");
        jTextField11 = new JTextField("4");
        
        jButton1 = new JButton();
        jButton1.setText("Changer votre avatar");

        //JLabel lab = new JLabel(new ImageIcon("src\\votre_image.png")); 
        //lab.setBounds(0, 0, 270, 230); 
        ImageIcon userIcon = new ImageIcon("gaetan.jpg");
        jLabel7 = new JLabel(userIcon);
        
        jPasswordField1 = new JPasswordField("5");
        jPasswordField2 = new JPasswordField("67");
        
        jLabel8 = new JLabel();
        jLabel8.setText("Parties gagnées");
        jLabel9 = new JLabel();
        jLabel9.setText("Parties perdues");
        
        jLabel10 = new JLabel();
        jLabel10.setText("Parties nulles");
        
        jTextField12 = new JTextField("8");
        jTextField13 = new JTextField("9");
        jTextField14 = new JTextField("10");
        
        jButton2 = new JButton();
        jButton2.setText("Exporter le profil");
        
        jButton3 = new JButton();
        jButton3.setText("Appliquer");
        
        

        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1))
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(login)
                            .addComponent(age)
                            .addComponent(lastname)
                            .addComponent(firstname)
                            .addComponent(password_confirm)
                            .addComponent(password))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPasswordField1)
                            .addComponent(login_field)
                            .addComponent(jTextField9, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                            .addComponent(jTextField10, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                            .addComponent(jTextField11, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)
                            .addComponent(jPasswordField2, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(96, 96, 96)
                        .addComponent(jTextField14, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(82, 82, 82)
                        .addComponent(jTextField12, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(84, 84, 84)
                        .addComponent(jTextField13, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE))
                    .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE))
                .addGap(45, 45, 45))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jLabel7))
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(login)
                    .addComponent(login_field, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(password)
                    .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(password_confirm)
                    .addComponent(jPasswordField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(firstname)
                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lastname)
                    .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(age)
                    .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jTextField13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jTextField14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addContainerGap())
        );
        return panel;
    }
     private void jPasswordField2ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }
}

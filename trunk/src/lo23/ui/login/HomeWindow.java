/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lo23.ui.login;

/**
 *
 * @author marcrossi
 */
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class HomeWindow extends JFrame implements ActionListener {

    private JButton connectBtn;
    private JButton loadProfileBtn;
    private JButton registerBtn;
    private JTextField loginField;
    private JPasswordField passwordField;

    public HomeWindow() {
        super();
        build();//On initialise notre fenêtre
    }

    private void build() {
        setTitle("Chess P2P"); //On donne un titre à l'application
        setSize(400, 300); //On donne une taille à notre fenêtre
        setLocationRelativeTo(null); //On centre la fenêtre sur l'écran
        setResizable(false); //On interdit la redimensionnement de la fenêtre
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //On dit à l'application de se fermer lors du clic sur la croix

        setContentPane(buildContentPanel());
    }

    private JPanel buildContentPanel() {

        // Grille
        JPanel panel = new JPanel();
        GridBagLayout gridbag = new GridBagLayout();
        panel.setLayout(gridbag);


        // Titre
        JLabel title = new JLabel("Bienvenue sur Chess P2P !!");
        title.setHorizontalAlignment(JLabel.CENTER);
        gridbag.setConstraints(title, modifyConstraint(GridBagConstraints.REMAINDER, 1, 2, 1));
        panel.add(title);


        // Form
        JLabel login = new JLabel("Login");
        JLabel password = new JLabel("Password");
        login.setHorizontalAlignment(JLabel.CENTER);
        password.setHorizontalAlignment(JLabel.CENTER);

        loginField = new JTextField();
        passwordField = new JPasswordField();

        gridbag.setConstraints(login, modifyConstraint(GridBagConstraints.BOTH, 0, 0, 1));
        gridbag.setConstraints(loginField, modifyConstraint(GridBagConstraints.REMAINDER, 2, 0, 1));
        gridbag.setConstraints(password, modifyConstraint(GridBagConstraints.BOTH, 0, 0, 1));
        gridbag.setConstraints(passwordField, modifyConstraint(GridBagConstraints.REMAINDER, 1, 0, 1));

        panel.add(login);
        panel.add(loginField);
        panel.add(password);
        panel.add(passwordField);


        // Buttons
        connectBtn = new JButton("Connexion");
        loadProfileBtn = new JButton("Charger un profil");
        registerBtn = new JButton("Inscription");

        connectBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                connectBtnActionPerformed(ae);
            }
        });
        
        loadProfileBtn.addActionListener(this);
        registerBtn.addActionListener(this);

        gridbag.setConstraints(connectBtn, modifyConstraint(GridBagConstraints.REMAINDER, 1, 1, 1));
        gridbag.setConstraints(loadProfileBtn, modifyConstraint(GridBagConstraints.BOTH, 1, 2, 1));
        gridbag.setConstraints(registerBtn, modifyConstraint(GridBagConstraints.REMAINDER, 1, 2, 1));

        panel.add(connectBtn);
        panel.add(loadProfileBtn);
        panel.add(registerBtn);

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

    public JTextField getLoginField() {
        return loginField;
    }

    public JTextField getPasswordField() {
        return passwordField;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        
    }
    public void connectBtnActionPerformed(ActionEvent e) {
            // debug
            System.out.println("Vous avez cliqué sur connexion.");
            System.out.println("valeur des champs : " + getLoginField().getText() + "   pass: " + getPasswordField().getText());

            // Appel de la methode de connexion
            Connect.Connection(getLoginField().getText(), getPasswordField().getText());

    }
}



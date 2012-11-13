/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lo23.ui.login;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import lo23.data.ApplicationModel;
import lo23.data.Game;
import lo23.data.Invitation;
import lo23.data.Profile;
import lo23.data.PublicProfile;
import lo23.data.managers.GameManagerInterface;
import lo23.data.managers.ProfileManagerInterface;
import lo23.data.ApplicationModel;
import lo23.ui.login.mockManager.CommManagerMock;
import lo23.ui.login.mockManager.GameManagerMock;
import lo23.ui.login.mockManager.ProfileManagerMock;

/**
 *
 * @author pat
 */
public class IHMListe extends javax.swing.JFrame implements PropertyChangeListener {

    private class PlayerModel extends DefaultTableModel {

        @Override
        public boolean isCellEditable(int r, int c) {
            return false;
        }

        @Override
        public Class getColumnClass(int columnIndex) {
            Object o = getValueAt(0, columnIndex);

            if (o == null) {
                return Object.class;
            } else {
                return o.getClass();
            }
        }

        public void addPlayer(String name, String firstname, ImageIcon ico) {
            this.addRow(new Object[]{name, firstname, ico});
        }

        public void removePlayer(String id) {
            for (int i = 0; i < this.getRowCount(); i++) {
                if (this.getValueAt(i, 0) == id) {
                    this.removeRow(i);
                    return;
                }
            }
        }
    }

    private Color chooseColorDialog() {
        Color color = Color.WHITE;
        String[] colorTab = {"WHITE", "BLACK"};
        int rang = JOptionPane.showOptionDialog(null,
                "Please choose your color !",
                "Choose Color Dialog",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                colorTab,
                colorTab[1]);
        if ("BLACK".equals(colorTab[rang])) {
            color = Color.BLACK;
        }
        //JOptionPane.showMessageDialog(null, "your choice is " + colorTab[rang], "color", JOptionPane.QUESTION_MESSAGE);
        return color;
    }
    private void sendInvitation(String id_user, Color color) {
        
        color = chooseColorDialog();
        long time = System.currentTimeMillis();
        //Instantiate DataManager
        ApplicationModel appModel = new ApplicationModel();
        ProfileManagerInterface profileManager = appModel.getPManager();
        //Instantiate profile and invitation
        Profile profile = profileManager.loadProfile(id_user);
        Invitation invit = null;
        //invit = profileManager.createInvitation(profile, color, time);
        //Send invitation
        profileManager.sendInvitation(invit);  
      
    }
    
    private boolean openInvitationDialog(Invitation invit){ 
        int response = 0;
        PublicProfile profile = invit.getGuest();
        response = JOptionPane.showConfirmDialog(null, "Accept/deny invitation ?" + profile.getName());
        if(response == 0)
               return true; 
        else
               return false; 
      }
    private void acceptInvitation(Invitation invit) {
        ApplicationModel appModel = new ApplicationModel();
        GameManagerInterface gameManager = appModel.getGManager();
        if(openInvitationDialog(invit))
        {
            Game game = gameManager.createGame(invit);
            // load(string ou long) ???
            //gameManager.load(game.getGameId());
        }
        else
        {
            setVisible(false);
        }
    }
    
    private final IhmLoginModel model;
    static String TITLE = "Players list";

    /**
     * Creates new form IHMListe
     */
    public IHMListe(IhmLoginModel model) {
        // model.addPropertyChangeListener(this);

        this.model = model;

        initComponents();
        this.setLocationRelativeTo(null); //On centre la fenêtre sur l'écran
        this.setTitle(TITLE);
        
       tablePlayers.addMouseListener(new MouseAdapter() {
           @Override
            public void mouseClicked(MouseEvent me) {
                int num = tablePlayers.rowAtPoint(me.getPoint());
                System.out.println(tablePlayers.getModel().getValueAt(num, 0));
            }
        });
       
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelTablePlayers = new javax.swing.JScrollPane();
        tablePlayers = new javax.swing.JTable();
        labelTable = new javax.swing.JLabel();
        panelInfos = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        labelInfos = new javax.swing.JLabel();
        manageProfileBtn = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        javax.swing.JButton disconnectBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(600, 600));

        tablePlayers.setModel(model.getPlayerModel());
        panelTablePlayers.setViewportView(tablePlayers);

        labelTable.setText("Liste des joueurs connectés");

        jTextField1.setEditable(false);
        jTextField1.setText("Nom du joueur");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jTextField2.setEditable(false);
        jTextField2.setText("Nom du joueur");
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelInfosLayout = new javax.swing.GroupLayout(panelInfos);
        panelInfos.setLayout(panelInfosLayout);
        panelInfosLayout.setHorizontalGroup(
            panelInfosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInfosLayout.createSequentialGroup()
                .addGroup(panelInfosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 118, Short.MAX_VALUE))
        );
        panelInfosLayout.setVerticalGroup(
            panelInfosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInfosLayout.createSequentialGroup()
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 158, Short.MAX_VALUE))
        );

        labelInfos.setText("Infos Profil");

        manageProfileBtn.setText("Gérer son profil");

        disconnectBtn.setText("Disconnect");
        disconnectBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                disconnectBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(panelTablePlayers, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(labelTable)
                                        .addGap(0, 49, Short.MAX_VALUE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(panelInfos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(labelInfos)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(manageProfileBtn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(disconnectBtn)))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(182, 182, 182))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTable, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelInfos))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelInfos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelTablePlayers, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(manageProfileBtn)
                    .addComponent(disconnectBtn))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        System.out.println("coucou");
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void disconnectBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_disconnectBtnActionPerformed
        
        this.dispose();
        //Instantiate DataManager
        ApplicationModel appModel = new ApplicationModel();
        appModel.setComManager(new CommManagerMock(appModel));
        appModel.setGameManager(new GameManagerMock((appModel)));
        appModel.setProfileManager(new ProfileManagerMock(appModel));
        //Instantiate IhmLoginModel
        IhmLoginModel ihmLoginModel = new IhmLoginModel(appModel);

        // new IhmConnexionWindow_old(ihmLoginModel).setVisible(true);
        new IhmConnexionWindow(ihmLoginModel).setVisible(true);
    }//GEN-LAST:event_disconnectBtnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(IHMListe.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
               new IHMListe(null).setVisible(true);

            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JLabel labelInfos;
    private javax.swing.JLabel labelTable;
    private javax.swing.JButton manageProfileBtn;
    private javax.swing.JPanel panelInfos;
    private javax.swing.JScrollPane panelTablePlayers;
    private javax.swing.JTable tablePlayers;
    // End of variables declaration//GEN-END:variables

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        System.out.println("property change");
        System.out.println(pce.getPropertyName());
    }
}
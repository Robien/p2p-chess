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
        return color;
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
    
    
    private final IhmLoginModel model;
    public static String TITLE = "Players list";

    /**
     * Creates new form IHMListe
     */
    public IHMListe(final IhmLoginModel model) {
        // model.addPropertyChangeListener(this);

        this.model = model;

        initComponents();
        this.setLocationRelativeTo(null); //On centre la fenêtre sur l'écran
        this.setTitle(TITLE);
        
       tablePlayers.removeColumn(tablePlayers.getColumn("id"));
       tablePlayers.addMouseListener(new MouseAdapter() {
           @Override
            public void mouseClicked(MouseEvent me) {
                int num = tablePlayers.rowAtPoint(me.getPoint());
                String id = (String)tablePlayers.getModel().getValueAt(0,num);
                PublicProfile profileSelected = model.getRemoteProfile(id);
                if(profileSelected != null){
                    //new IhmProfileWindow(model,IhmProfileWindow.SEE,profileSelected);
                    System.out.println("TODO Launch IHMProfileWindow");
                }
                else
                    JOptionPane.showMessageDialog(IHMListe.this, "User doesn't exist anymore", "User error", JOptionPane.ERROR_MESSAGE);
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
        manageProfileBtn = new javax.swing.JButton();
        javax.swing.JButton disconnectBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(600, 600));

        tablePlayers.setModel(model.getPlayerModel());
        panelTablePlayers.setViewportView(tablePlayers);

        labelTable.setText("Liste des joueurs connectés");

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
                    .addComponent(panelTablePlayers, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(labelTable)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(manageProfileBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 242, Short.MAX_VALUE)
                        .addComponent(disconnectBtn)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addComponent(labelTable, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(panelTablePlayers, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(manageProfileBtn)
                    .addComponent(disconnectBtn))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel labelTable;
    private javax.swing.JButton manageProfileBtn;
    private javax.swing.JScrollPane panelTablePlayers;
    private javax.swing.JTable tablePlayers;
    // End of variables declaration//GEN-END:variables

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        System.out.println("property change");
        System.out.println(pce.getPropertyName());
    }
}

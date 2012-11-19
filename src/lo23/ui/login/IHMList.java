/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lo23.ui.login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import lo23.data.PublicProfile;
import lo23.utils.Enums;
import lo23.utils.JTableButtonMouseListener;

/**
 *
 * @author pat
 */
public class IHMList extends javax.swing.JFrame implements PropertyChangeListener,TableModelListener {


    
    private final IhmLoginModel model;
    public static String TITLE = "Players list";
   
    
    /**
     * Creates new form IHMListe
     */
    public IHMList(final IhmLoginModel model) {
        // model.addPropertyChangeListener(this);

        this.model = model;
        

        initComponents();
        this.setLocationRelativeTo(null); //On centre la fenêtre sur l'écran
        this.setTitle(TITLE);
        
       tablePlayers.removeColumn(tablePlayers.getColumn("id"));
       tablePlayers.addMouseListener(new MouseAdapter() {
           @Override
            public void mouseClicked(MouseEvent me) {
               if((me.getButton() == MouseEvent.BUTTON1) && (me.getClickCount() == 2)){
                    int num = tablePlayers.rowAtPoint(me.getPoint());
                    String id = (String)tablePlayers.getModel().getValueAt(num,0);
                    PublicProfile profileSelected = model.getRemoteProfile(id);
                    if(profileSelected != null){
                        new IhmProfileWindow(model,IhmProfileWindow.READ,profileSelected).setVisible(true);
                    }
                    else
                        JOptionPane.showMessageDialog(IHMList.this, "User doesn't exist anymore", "User error", JOptionPane.ERROR_MESSAGE);
               }
           }
        });
        tablePlayers.getModel().addTableModelListener(this);
        
        //TEST
        for(PublicProfile p : model.getApplicationModel().getPManager().getLocalPublicProfiles()){
            model.pcs.firePropertyChange(IhmLoginModel.ADD_PLAYER_CONNECTED, null, p);
        }
    }
    
    private void launchGameBtnActionPerformed(java.awt.event.ActionEvent evt) {
        String id;
        JButton btn = (JButton) evt.getSource();
        
        id = (String) btn.getClientProperty("id");
        System.out.println("click launch game btn avec comme id = " + id);
        Enums.COLOR col = chooseColorDialog();
        if(col != null){
            System.out.println("Send invitation to id = "+id+" with color "+(col==Enums.COLOR.BLACK ? "Black" : "White"));
            model.sendInvitation(id,col);
            btn.setEnabled(false);
        }
    }
    
    
    private Enums.COLOR chooseColorDialog() {
        Enums.COLOR color = Enums.COLOR.WHITE;
        String[] colorTab = {"WHITE", "BLACK"};
        int rang = JOptionPane.showOptionDialog(null,
                "Please choose your color !",
                "Choose Color Dialog",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                colorTab,
                colorTab[0]);
        if(rang == -1) {
            return null;
        }
        else if ("BLACK".equals(colorTab[rang])) {
            color = Enums.COLOR.BLACK;
        }
        return color;
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
        reviewGamesBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(600, 600));

        tablePlayers.setModel(model.getPlayerModel());
        tablePlayers.getColumn("").setCellRenderer(new lo23.utils.JTableButtonRenderer());
        panelTablePlayers.setViewportView(tablePlayers);
        tablePlayers.addMouseListener(new JTableButtonMouseListener(tablePlayers));

        labelTable.setText("Liste des joueurs connectés");

        manageProfileBtn.setText("Gérer son profil");
        manageProfileBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                manageProfileBtnActionPerformed(evt);
            }
        });

        disconnectBtn.setText("Disconnect");
        disconnectBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                disconnectBtnActionPerformed(evt);
            }
        });

        reviewGamesBtn.setText("Review games");
        reviewGamesBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reviewGamesBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelTablePlayers, javax.swing.GroupLayout.DEFAULT_SIZE, 499, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(labelTable)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(manageProfileBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(reviewGamesBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                    .addComponent(disconnectBtn)
                    .addComponent(reviewGamesBtn))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void disconnectBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_disconnectBtnActionPerformed
        
        IhmLoginModel ihmLoginModel = new IhmLoginModel(model.getApplicationModel());
        
        this.dispose();
        
        new IhmConnexionWindow(ihmLoginModel).setVisible(true);
    }//GEN-LAST:event_disconnectBtnActionPerformed

    private void manageProfileBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_manageProfileBtnActionPerformed
        new IhmProfileWindow(model,IhmProfileWindow.MODIFY,null).setVisible(true);
    }//GEN-LAST:event_manageProfileBtnActionPerformed

    private void reviewGamesBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reviewGamesBtnActionPerformed
        this.setEnabled(false);
        new IhmListGames(model, this).setVisible(true);
    }//GEN-LAST:event_reviewGamesBtnActionPerformed

    /**
     * @param args the command line arguments
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel labelTable;
    private javax.swing.JButton manageProfileBtn;
    private javax.swing.JScrollPane panelTablePlayers;
    private javax.swing.JButton reviewGamesBtn;
    private javax.swing.JTable tablePlayers;
    // End of variables declaration//GEN-END:variables

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        System.out.println("property change");
        System.out.println(pce.getPropertyName());
    }

    @Override
    public void tableChanged(TableModelEvent tme) {
        if(tme.getType() == TableModelEvent.INSERT){
            int row = tme.getLastRow();
            TableModel tm = (TableModel)tme.getSource();
            Object o = tm.getValueAt(row,tm.getColumnCount()-1);
            if(o instanceof JButton){
                ((JButton)o).addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        launchGameBtnActionPerformed(ae);
                    }
                });
            }
        }
    }
}

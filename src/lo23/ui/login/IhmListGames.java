/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * IhmListGames.java
 *
 * Created on 6 nov. 2012, 15:36:15
 */

package lo23.ui.login;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import lo23.data.Game;
import lo23.data.Invitation;
import lo23.data.PublicProfile;
import lo23.data.ResumeGame;
import lo23.data.exceptions.WrongInvitation;
import lo23.utils.JTableButtonMouseListener;
import lo23.utils.JTableButtonRenderer;


/**
 * Class which manages the window of ended games to review or stopped games to continue
 * @author remi clermont
 */
public class IhmListGames extends javax.swing.JFrame implements TableModelListener,PropertyChangeListener{

    private IhmLoginModel ihmLoginModel;
    private IHMList listPlayers;
    private WaitingDialog waitingDialog;
    
    /** 
     * Constructor
     * Creates new form IhmListGames 
     */
    public IhmListGames(IhmLoginModel ihmLoginModel, IHMList listPlayers) {
        this.ihmLoginModel = ihmLoginModel;
        this.listPlayers = listPlayers;
        
        initComponents();
        
        setResizable(false);
        setLocationRelativeTo(null); //On centre la fenêtre sur l'écran
        // Ajoute un listener sur tous les ReviewGameBtn
        ArrayList<JButton> listReviewBtn = ihmLoginModel.getListReviewGameBtn();
        for (JButton btn : listReviewBtn) {
            btn.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    reviewGameBtnActionPerformed(evt);
                }
            });
        }
        
        //Add listener when table model change
        this.stopGamesTable.getModel().addTableModelListener(this);
        
        
        ihmLoginModel.addPropertyChangeListener(IhmLoginModel.INVIT_RECEIVE,this);
        ihmLoginModel.addPropertyChangeListener(IhmLoginModel.GAME_ENDED,this);
        ihmLoginModel.addPropertyChangeListener(IhmLoginModel.REQUEST_GAME_RESPONSE,this);
        ihmLoginModel.addPropertyChangeListener(IhmLoginModel.GAME_ENDED, this);
        ihmLoginModel.addPropertyChangeListener(IhmLoginModel.GAME_STARTED, this);
        
        //Seulement lorsque l'utilisateur distant est en ligne
        //Implémentation terminé à tester
        
        // Ajoute un listener sur tous les ContinueGameBtn
        /*ArrayList<JButton> listContinueBtn = ihmLoginModel.getListContinueGameBtn();
        for (JButton btn : listContinueBtn) {
            btn.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    continueGameBtnActionPerformed(evt);
                }
            });
        }*/
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.JButton previousBtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        endGamesTable = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        stopGamesTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("My games");

        previousBtn.setText("Previous");
        previousBtn.setActionCommand("previousBtn");
        previousBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                previousBtnActionPerformed(evt);
            }
        });

        jLabel1.setText("Ended games");

        jLabel2.setText("Started games (and player connected)");

        endGamesTable.setModel(ihmLoginModel.getEndGameModel());
        endGamesTable.getColumn("").setCellRenderer(new JTableButtonRenderer());
        endGamesTable.setAutoCreateRowSorter(true);
        endGamesTable.addMouseListener(new JTableButtonMouseListener(endGamesTable));
        jScrollPane3.setViewportView(endGamesTable);

        stopGamesTable.setModel(ihmLoginModel.getStopGameModel());

        stopGamesTable.getColumn("").setCellRenderer(new JTableButtonRenderer());
        stopGamesTable.addMouseListener(new JTableButtonMouseListener(stopGamesTable));
        stopGamesTable.setAutoCreateRowSorter(true);
        jScrollPane1.setViewportView(stopGamesTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, 0, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE)
                        .addComponent(previousBtn))
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                .addComponent(previousBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void previousBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_previousBtnActionPerformed
        this.dispose();
        this.listPlayers.setEnabled(true);
        this.listPlayers.dispatchInvit = false;
    }//GEN-LAST:event_previousBtnActionPerformed

    private void reviewGameBtnActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            long idGame;
            JButton btn = (JButton) evt.getSource();
            idGame = (Long) btn.getClientProperty("id");
            ihmLoginModel.loadEndedGame(idGame);
            this.setVisible(false);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
        }
    }    
    
    private void continueGameBtnActionPerformed(java.awt.event.ActionEvent evt) {  
        long idGame;
        JButton btn = (JButton) evt.getSource();
        idGame = (Long) btn.getClientProperty("id");
        ihmLoginModel.sendInvitationResumeGame(idGame);
        btn.setEnabled(false);
    }                                        

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable endGamesTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable stopGamesTable;
    // End of variables declaration//GEN-END:variables

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
                        continueGameBtnActionPerformed(ae);
                    }
                });
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent pce) {
        if(this.isVisible() && this.isEnabled()){
            if(pce.getPropertyName().equals(IhmLoginModel.INVIT_RECEIVE)){
                Invitation invitation = (Invitation)pce.getNewValue();
                boolean b = openInvitationDialog(invitation);
                try {
                    ihmLoginModel.sendInvitationAnswer(invitation,b);
                    if(b){
                       waitingDialog = new WaitingDialog(this,true);
                       waitingDialog.setVisible(true);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, ex.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
                }
            }
            if(pce.getPropertyName().equals(IhmLoginModel.REQUEST_GAME_RESPONSE)){
                boolean resp = (Boolean)pce.getOldValue();
                Invitation invitation = (Invitation)pce.getNewValue();
                if(resp){
                    try {
                        ihmLoginModel.loadGame(invitation);
                        
                        this.setVisible(false);
                        this.listPlayers.setVisible(false);
                        ihmLoginModel.sendGameStarted(invitation);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(this, ex.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
                    }
                }
                
                if(invitation instanceof ResumeGame){
                    Game g = ((ResumeGame)invitation).getGame();
                    for(JButton btn : ihmLoginModel.getListContinueGameBtn()){
                        if(btn.getClientProperty("id").equals(g.getGameId())){
                            btn.setEnabled(true);
                        }
                    }
                }
            }
            if(pce.getPropertyName().equals(IhmLoginModel.GAME_STARTED)){
                Boolean isReady = (Boolean) pce.getOldValue();
                Invitation invit = (Invitation)pce.getNewValue();
                if(isReady){
                    try {
                        ihmLoginModel.loadGame(invit);
                       
                        this.setVisible(false);
                    } catch (Exception ex){
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(this, ex.getMessage(), "Exception", JOptionPane.ERROR_MESSAGE);
                    }
                }
                waitingDialog.setVisible(false);
                waitingDialog.dispose();
            }
        }
        if(pce.getPropertyName().equals(IhmLoginModel.GAME_ENDED)){
            this.setVisible(true);
        }
    }
    
    private boolean openInvitationDialog(Invitation invit){ 
        int response = -1;
        PublicProfile profile = invit.getHost();
        response = JOptionPane.showConfirmDialog(null,"Accept/deny invitation from " + profile.getPseudo() + " ?", "Accept/deny invitation from " + profile.getPseudo() + " ?", JOptionPane.YES_NO_OPTION);
        System.out.println("Invitation : "+response);
        if(response == 0)
               return true; 
        else
               return false; 
    }

}

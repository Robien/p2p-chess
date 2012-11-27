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
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import lo23.data.ApplicationModel;
import lo23.data.Game;
import lo23.ui.login.mockManager.CommManagerMock;
import lo23.ui.login.mockManager.GameManagerMock;
import lo23.ui.login.mockManager.ProfileManagerMock;
import lo23.utils.JTableButtonMouseListener;
import lo23.utils.JTableButtonRenderer;


/**
 * Classe gérant la fenêtre de partie terminées à revoir et des parties en cours à continuer
 * 
 * @author rossmarc
 */
public class IhmListGames extends javax.swing.JFrame implements TableModelListener{

    private IhmLoginModel ihmLoginModel;
    private IHMList listPlayers;
    
    /** Creates new form IhmListGames */
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
        
        
        //Seulement lorsque l'utilisateur distant est en ligne
        //Implémentation terminé à tester
        
        // Ajoute un listener sur tous les ContinueGameBtn
        ArrayList<JButton> listContinueBtn = ihmLoginModel.getListContinueGameBtn();
        for (JButton btn : listContinueBtn) {
            btn.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    continueGameBtnActionPerformed(evt);
                }
            });
        }
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

        jLabel1.setText("Parties terminées");

        jLabel2.setText("Parties en cours (et adversaires connectés)");

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
    }//GEN-LAST:event_previousBtnActionPerformed

    public void reviewGameBtnActionPerformed(java.awt.event.ActionEvent evt) {
        long idGame;
        JButton btn = (JButton) evt.getSource();
        idGame = (Long) btn.getClientProperty("id");
        ihmLoginModel.sendInvitationResumeGame(idGame);
    }    
    
    private void continueGameBtnActionPerformed(java.awt.event.ActionEvent evt) {  
        long idGame;
        JButton btn = (JButton) evt.getSource();
        idGame = (Long) btn.getClientProperty("id");
        ihmLoginModel.sendInvitationResumeGame(idGame);
    }                                        

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                //Instantiate DataManager
                ApplicationModel appModel = new ApplicationModel();
                appModel.setComManager(new CommManagerMock(appModel));
                appModel.setGameManager((new GameManagerMock((appModel))));
                appModel.setProfileManager(new ProfileManagerMock(appModel));
                //Instantiate IhmLoginModel
                IhmLoginModel ihmLoginModel = new IhmLoginModel(appModel);
                IHMList listplayers = new IHMList(ihmLoginModel);
                new IhmListGames(ihmLoginModel, listplayers).setVisible(true);
            }
        });
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

}

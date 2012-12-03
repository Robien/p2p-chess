/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lo23.ui.grid;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;
import lo23.data.ApplicationModel;
import lo23.data.Event;
import lo23.data.Message;
import lo23.data.Move;
import lo23.data.Player;
import lo23.data.Position;
import lo23.data.pieces.Bishop;
import lo23.data.pieces.GamePiece;

/**
 *
 * @author PEP
 */
public class ReviewPanel extends javax.swing.JPanel {
    private ApplicationModel myModel;
    private ChatPanel2 myChatPanel;
    private GamePanel myGamePanel;
    private ArrayList<Event> listEvents;
    private int currentEvent;
    /**
     * Creates new form ReviewPanel
     */
    public ReviewPanel() {
        initComponents();
    }

    public ReviewPanel(ApplicationModel model, ChatPanel2 chat, GamePanel gamePanel) {
        initComponents();
        myModel = model;
        myChatPanel = chat;
        myGamePanel = gamePanel;
        currentEvent = 0;

        listEvents = new ArrayList<Event>();
       //listEvents = myModel.getGManager().getCurrentGame().getEvents();
        
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        begin = new javax.swing.JButton();
        previous = new javax.swing.JButton();
        next = new javax.swing.JButton();
        end = new javax.swing.JButton();

        begin.setText("Debut");
        begin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                beginActionPerformed(evt);
            }
        });

        previous.setText("Previous");

        next.setText("Next");
        next.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nextActionPerformed(evt);
            }
        });

        end.setText("end");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(begin)
                .addGap(18, 18, 18)
                .addComponent(previous)
                .addGap(18, 18, 18)
                .addComponent(next)
                .addGap(18, 18, 18)
                .addComponent(end)
                .addContainerGap(71, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(begin)
                    .addComponent(previous)
                    .addComponent(next)
                    .addComponent(end))
                .addContainerGap(22, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void beginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_beginActionPerformed
               
        GamePiece piece;
        Position from;
        Position to;
        
       // piece = new Bishop(new Position(2, 3), myModel.getGManager().getCurrentGame().getLocalPlayer(),myModel.getGManager().getCurrentGame() );
        
        piece = myModel.getGManager().getCurrentGame().getPieceAtXY(1, 6);
        from = piece.getPosition();
        to = new Position(1, 5);
        listEvents.add(new Move(from, to, piece));
        
        listEvents.add(new Message("test review 1", myModel.getGManager().getCurrentGame().getLocalPlayer(), myModel.getGManager().getCurrentGame().getRemotePlayer()));
    
        piece = myModel.getGManager().getCurrentGame().getPieceAtXY(1, 1);
        from = piece.getPosition();
        to = new Position(1, 2);
        listEvents.add(new Move(from, to, piece));
        
        listEvents.add(new Message("test review 2", myModel.getGManager().getCurrentGame().getLocalPlayer(), myModel.getGManager().getCurrentGame().getRemotePlayer()));
    
    }//GEN-LAST:event_beginActionPerformed

    private void nextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextActionPerformed

        while(currentEvent < listEvents.size() && !(listEvents.get(currentEvent) instanceof Move)){ // n'est pas un mouvement
            
            
            try {
                // ici on a les messages
                myChatPanel.receivedMsg((Message)listEvents.get(currentEvent));
            } catch (BadLocationException ex) {
                Logger.getLogger(ReviewPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            currentEvent = currentEvent + 1;
        }
        
        // si on a affaire à un mouvement
        if(currentEvent < listEvents.size()){
            try {
                // on affiche dans le chat
                myChatPanel.gameMsg((Move)listEvents.get(currentEvent));
                // on met à jour le board
                myGamePanel.updateReviewBoard((Move)listEvents.get(currentEvent));
                // on passe au coup suivant
                currentEvent++;
                
            } catch (BadLocationException ex) {
                Logger.getLogger(ReviewPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        // si on a executé le dernier move
        if(currentEvent == listEvents.size() ){
            next.setEnabled(false);
        }
    }//GEN-LAST:event_nextActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton begin;
    private javax.swing.JButton end;
    private javax.swing.JButton next;
    private javax.swing.JButton previous;
    // End of variables declaration//GEN-END:variables
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lo23.ui.grid;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import lo23.data.ApplicationModel;
import lo23.data.Message;
import lo23.data.Move;
import lo23.data.Player;
import lo23.data.exceptions.NoIdException;
import lo23.utils.Enums;
import lo23.utils.ResourceManager;
//import lo23.ui.grid.EventListener;

/** 
 *
 * @author PEP
 */
public class ChatPanel2 extends javax.swing.JPanel {

    StyleContext sc;
    Style defaultStyle;
    final Style localStyle;
    final Style remoteStyle;
    Style gameStyle;
    final DefaultStyledDocument doc;
    ApplicationModel myModel;
    EventListener eventListener; 

//       private EventListener eventListener;

    /**
     * Creates new form ChatPanel2
     */

        public ChatPanel2(ApplicationModel model) {
        myModel = model;

       eventListener = new EventListener(this, myModel);

        initComponents();
        sc = new StyleContext();
        doc = new DefaultStyledDocument(sc);
        // style pour le joueur local
        defaultStyle = sc.getStyle(StyleContext.DEFAULT_STYLE);
        localStyle = sc.addStyle("localStyle", defaultStyle);
        StyleConstants.setLeftIndent(localStyle, 16);
        StyleConstants.setRightIndent(localStyle, 16);
        StyleConstants.setFirstLineIndent(localStyle, 16);
        StyleConstants.setFontFamily(localStyle, "serif");
        StyleConstants.setFontSize(localStyle, 12);


        remoteStyle = jTextPane1.addStyle("remoteStyle", defaultStyle);
        StyleConstants.setFontFamily(remoteStyle, "serif");
        StyleConstants.setForeground(remoteStyle, Color.BLUE);
      

     this.setOpaque(false);
     this.setBorder(BorderFactory.createTitledBorder(""));

                // ajout d'un ecouteur de frappe du clavier sur le textField
        jTextField1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                // teste si la touche pressé correspond à la touche entrée
                if (e.getKeyCode() == 10) {
                    try {
                        // sur le bouton lui meme
                        sendMsg(jTextField1.getText());
                    } catch (BadLocationException ex) {
                        Logger.getLogger(ChatPanel2.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
        });
        
        
           try {
    for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
        if ("Nimbus".equals(info.getName())) {
            UIManager.setLookAndFeel(info.getClassName());
            break;
        }
    }
    } catch (Exception e) {
        // If Nimbus is not available, you can set the GUI to another look and feel.
    }

        addPropertyChangeListener(eventListener);

    }
        
        
   
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jTextField1 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();

        setBorder(new javax.swing.border.MatteBorder(null));

        jButton1.setBackground(new java.awt.Color(204, 204, 204));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lo23/ui/resources/draw.png"))); // NOI18N
        jButton1.setToolTipText("Propose a draw");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(204, 204, 204));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lo23/ui/resources/giveUp.png"))); // NOI18N
        jButton2.setToolTipText("Giving up the game");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(204, 204, 204));
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lo23/ui/resources/save.png"))); // NOI18N
        jButton3.setToolTipText("Save game");
        jButton3.setBorderPainted(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jTextPane1.setEditable(false);
        jTextPane1.setBorder(null);
        jTextPane1.setFont(new java.awt.Font("Tw Cen MT", 0, 11)); // NOI18N
        jTextPane1.setForeground(new java.awt.Color(102, 102, 102));
        jTextPane1.setToolTipText("");
        jTextPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTextPane1.setMaximumSize(new java.awt.Dimension(6, 20));
        jScrollPane1.setViewportView(jTextPane1);

        jTextField1.setDisabledTextColor(new java.awt.Color(204, 0, 0));

        jButton4.setBackground(new java.awt.Color(204, 204, 204));
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lo23/ui/resources/send.png"))); // NOI18N
        jButton4.setToolTipText("Send");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTextField1)
                        .addGap(11, 11, 11)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addGap(26, 26, 26)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField1))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        JOptionPane d = new JOptionPane();
        String[] choice = {"Yes", "No"};
        int retour = d.showOptionDialog(this, 
        "Do you really want to propose a draw ?",
        "Drawing",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.QUESTION_MESSAGE,
        new ImageIcon(ResourceManager.getInstance().getResource("time_chess.png")),
        choice,
        choice[1]);

        if(retour == 0){ // oui je veux proposer la nulle
            System.out.println("je veux proposer la nulle");

        }
    }//GEN-LAST:event_jButton1ActionPerformed
    /**
     * This function allows to display message that local player sent
     * @param msg
     * @throws BadLocationException
     */
    private void sendMsg(String msg) throws BadLocationException{
       if (!msg.equals("")) { // if not null
            StyledDocument doc2 = (StyledDocument) jTextPane1.getDocument();

                // sending message to remote player
                if(myModel == null)
                       System.out.print("variable modele nulle");
                else{
                   Message m = myModel.getGManager().createMessage(msg);
                   myModel.getGManager().sendMessage(m);
                }

                // printing on screen
             //   doc2.insertString(doc2.getLength(), "[" + getHeure() + "][" + myModel.getGManager().getCurrentGame().getLocalPlayer().getPublicProfile().getPseudo()+ "] : " + jTextField1.getText() + "\n", localStyle);
                jTextField1.setText("");
                jTextField1.setFocusable(true);
                

               // receivedMsg(new Message("test", null, null));

        }
    }
    /**
     * Fonction qui permet d'afficher un message envoyé par le joueur distant
     * @param msg
     * @throws BadLocationException
     */
    public void receivedMsg(Message msg) throws BadLocationException{
       System.out.println("J'ai bien reçu un message");

       if (!msg.getContents().equals("")) { // if not null
            StyledDocument doc2 = (StyledDocument) jTextPane1.getDocument();

            Player sender = msg.getSender();
           // Player receiver = msg.getReceiver();

            // printing on screen
            doc2.insertString(doc2.getLength(), "[" + getHeure() + "]["+sender.getPublicProfile().getPseudo()+"] : " + msg.getContents() + "\n", remoteStyle);


       }
       else{
           System.out.println("le message reçu est null");
       }
    }

    /**
     * Fonction qui permet d'afficher un message par rapport au jeu
     * @param msg
     * @throws BadLocationException
     */
    public void gameMsg(Move mv) throws BadLocationException{
        System.out.println("OK");
            StyledDocument doc2 = (StyledDocument) jTextPane1.getDocument();

            
            if(mv.getPiece().getOwner().getColor() == Enums.COLOR.BLACK){
                gameStyle = jTextPane1.addStyle("gameStyle", remoteStyle);
                Color color_B = new Color(36,38,41);
                StyleConstants.setForeground(gameStyle, color_B);
                StyleConstants.setFontSize(gameStyle, 12);
            }
            else{
                gameStyle = jTextPane1.addStyle("gameStyle", remoteStyle);
                Color color_W = new Color(158,133,78);
                StyleConstants.setForeground(gameStyle, color_W);
                StyleConstants.setFontSize(gameStyle, 12);
            }
                
                
                // printing on screen
                doc2.insertString(doc2.getLength(), "[" + getHeure() + "] "+ mv.toString() + "\n", gameStyle);
              //  jTextField1.setText("");
               // jTextField1.setFocusable(true);
                
                
    }
    
    private String getHeure(){
        Date actuelle = new Date();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        return dateFormat.format(actuelle);
    }

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        String val = jTextField1.getText();
        try {
            sendMsg(val);
        } catch (BadLocationException ex) {
            Logger.getLogger(ChatPanel2.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        JOptionPane d = new JOptionPane();
        String[] choice = {"Yes", "No"};
        int retour = d.showOptionDialog(this, 
        "Do you want to save the game in order to resume it next ?",
        "Saving",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.QUESTION_MESSAGE,
        new ImageIcon(ResourceManager.getInstance().getResource("chess_icon.png")),
        choice,
        choice[1]);
      //  int retour = d.showConfirmDialog(this,"Do you want to save the game in order to resume it next ?","Saving",JOptionPane.YES_NO_OPTION);

        if(retour == 0){ // oui je veux proposer la sauvegarde
            saveGame();
        }

    }//GEN-LAST:event_jButton3ActionPerformed
   
    private void saveGame(){
            try {
                myModel.getGManager().save();
            } catch (NoIdException ex) {
                Logger.getLogger(ChatPanel2.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ChatPanel2.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    // quitter
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        JOptionPane d = new JOptionPane();
        String[] choice = {"Yes", "No"};
        int retour = d.showOptionDialog(this, 
        "Do you want to save the game before leaving ?",
        "Exit",
        JOptionPane.YES_NO_OPTION,
        JOptionPane.QUESTION_MESSAGE,
        new ImageIcon(ResourceManager.getInstance().getResource("chess_icon.png")),
        choice,
        choice[1]);

        if(retour == 0){ // oui je sauvegarder avant de quitter
            saveGame();
        }
        
        
        // mettre du code pour quitter

    }//GEN-LAST:event_jButton2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextPane jTextPane1;
    // End of variables declaration//GEN-END:variables
}

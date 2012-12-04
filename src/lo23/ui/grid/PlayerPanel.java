/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PlayerPanel.java
 *
 * Created on 13 nov. 2012, 15:14:55
 */

package lo23.ui.grid;

import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import lo23.data.ApplicationModel;
import lo23.data.Player;
import lo23.data.PublicProfile;
import lo23.utils.Enums.STATUS;

/**
 *
 * @author lopicave
 */
public class PlayerPanel extends javax.swing.JPanel {
    ApplicationModel myModel;
    public static final java.awt.Color fond = new java.awt.Color(170, 51, 0);
    
        /** Creates new form PlayerPanel */
    public PlayerPanel(ApplicationModel model, Player player) {
        initComponents();
        myModel = model;
        // inits values
        nickName.setText(player.getPublicProfile().getPseudo());
        age.setText(String.valueOf(player.getPublicProfile().getAge()) + " ans");
        
       // ImageIcon img = player.getPublicProfile().getAvatar();
        //Icon ico = new ImageIcon("QW.png");
       // avatar.setIcon(ico);
        setBackground(fond);
        
        avatar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/lo23/ui/resources/QW.png"))); // NOI18N

//  
    }

    public PlayerPanel(ApplicationModel model) {
        initComponents();
        myModel = model;
        // inits values
      //  age.setText(String.valueOf(player.getPublicProfile().getAge()));
      //  nickName.setText(player.getPublicProfile().getPseudo());

        //ImageIcon img = player.getPublicProfile().getAvatar();
       // Icon ico = new ImageIcon(img);
       // avatar.setIcon(ico);

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        avatar = new javax.swing.JLabel();
        nickName = new javax.swing.JLabel();
        age = new javax.swing.JLabel();

        avatar.setBackground(new java.awt.Color(51, 255, 51));

        nickName.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N
        nickName.setForeground(new java.awt.Color(255, 51, 51));
        nickName.setText("Nom du joueur");

        age.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        age.setText("Age");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(avatar, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(nickName)
                    .addComponent(age))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(avatar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(nickName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(age)
                        .addGap(0, 30, Short.MAX_VALUE)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel age;
    private javax.swing.JLabel avatar;
    private javax.swing.JLabel nickName;
    // End of variables declaration//GEN-END:variables

}

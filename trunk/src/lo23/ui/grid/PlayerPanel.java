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
    /** Creates new form PlayerPanel */
    public PlayerPanel(ApplicationModel model){//,Player player) {
        initComponents();
        myModel = model;
        // inits values
//        PublicProfile tmp = new PublicProfile("profileId", "Pseudo", STATUS.INGAME, "192.168.1.1", null, "picavet", "loic", 24);
//        age.setText(String.valueOf(tmp.getAge()));
//        nickName.setText(tmp.getPseudo());

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
                .addContainerGap(34, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(nickName)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(age))
                    .addComponent(avatar, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel age;
    private javax.swing.JLabel avatar;
    private javax.swing.JLabel nickName;
    // End of variables declaration//GEN-END:variables

}

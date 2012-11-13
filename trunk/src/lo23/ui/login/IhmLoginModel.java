/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lo23.ui.login;

import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import lo23.data.ApplicationModel;
import lo23.data.Game;
import lo23.data.Invitation;
import lo23.data.Profile;
import lo23.data.PublicProfile;
import lo23.data.ResumeGame;
import lo23.data.exceptions.FileNotFoundException;
import lo23.data.managers.GameManagerInterface;
import lo23.data.managers.ProfileManager;
import lo23.data.managers.ProfileManagerInterface;
import lo23.utils.Enums;
import lo23.utils.Enums.COLOR;

/**
 *
 * @author Esteban
 */
public class IhmLoginModel implements PropertyChangeListener{
    
    public static final String VIEW_PROFILE_RESPONSE = "view-profile-response";
    public static final String ADD_PLAYER_CONNECTED = "add-player-connected";
    public static final String DELETE_PLAYER_DISCONNECTED = "delete-player-disconnected";
    public static final String REQUEST_GAME_RESPONSE = "request-game-response";
    public static final String INVIT_RECEIVE = "invit-receive";
    public static final String INVIT_EXPIRED = "invit-expired";
    
    private PropertyChangeSupport pcs;

    private ApplicationModel appModel;

    private PlayerModel listPlayers;
    private HashMap<PublicProfile,Date> listProfileDate;
    private ArrayList<Game> listEndGames;
    private ArrayList<ResumeGame> listStopGames;
    private IHMListe IHMList;
    
    public IhmLoginModel(ApplicationModel appModel){
        this.appModel = appModel;
        listEndGames = new ArrayList<Game>();
        listStopGames = new ArrayList<ResumeGame>();

        Object[][] donnees = {};
        String[] entetes = {"id","Prénom", "Nom", "Status"};
        listPlayers = new PlayerModel();
        listPlayers.setDataVector(donnees, entetes);

        listProfileDate = new HashMap<PublicProfile,Date>();

        pcs = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener l){
        if(pcs != null)
            pcs.addPropertyChangeListener(l);
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
    public void acceptInvitation(Invitation invit) throws FileNotFoundException{

        GameManagerInterface gameManager = appModel.getGManager();
        boolean response = openInvitationDialog(invit);
        if(response == true)
        {
            Game game = gameManager.createGame(invit);
            gameManager.load(game.getGameId());
        }
        else
        {
            //setVisible(true);
        }
    }
    private COLOR chooseColorDialog() {
        COLOR color = COLOR.WHITE;
        String[] colorTab = {"WHITE", "BLACK"};
        int rang = JOptionPane.showOptionDialog(null,
                "Please choose your color !",
                "Choose Color Dialog",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                colorTab,
                colorTab[0]);
        if ("BLACK".equals(colorTab[rang])) {
            color = COLOR.BLACK;
        }
        return color;
    }
    public void sendInvitation(String idUser,COLOR col){
      
        col = chooseColorDialog();
        long time = System.currentTimeMillis();
        //Instantiate DataManager
        ProfileManagerInterface profileManager = appModel.getPManager();
        //Instantiate profile and invitation
        Profile profile = profileManager.loadProfile(idUser);
        Invitation invit = profileManager.createInvitation(profile.getPublicProfile(), col, time);
        //Send invitation
        profileManager.sendInvitation(invit);     
    }

    public ApplicationModel getApplicationModel() {
        return appModel;
    }

    public PlayerModel getPlayerModel(){
        return listPlayers;
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals(ADD_PLAYER_CONNECTED)){
            PublicProfile profile = (PublicProfile)evt.getNewValue();


            listProfileDate.put(profile,new Date());

            removeOldPlayers();
            /*
            listPlayers.addPlayer("patrick", "browne", new ImageIcon("/home/pat/icon.gif"));
            listPlayers.addPlayer("mohamed", "lahlou", new ImageIcon("icon.gif"));
            listPlayers.addPlayer("gaetan", "gregoire", new ImageIcon("icon.gif"));
            listPlayers.addPlayer("remi", "clermont", new ImageIcon("icon.gif"));
            listPlayers.removePlayer("remi");
            listPlayers.removePlayer("gaetan");*/
        }
    }

    private void removeOldPlayers(){
        Date now = new Date();
        for(PublicProfile p : listProfileDate.keySet()){
            Date currDate = listProfileDate.get(p);
            if(now.getTime() - currDate.getTime() >= 30*1000){
                listProfileDate.remove(p);
                listPlayers.removePlayer(p.getProfileId());
            }
        }
    }
 

    private class PlayerModel extends DefaultTableModel {

        public boolean isCellEditable(int r, int c) {
            return false;
        }

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
}

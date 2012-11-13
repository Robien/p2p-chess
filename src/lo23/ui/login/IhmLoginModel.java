/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lo23.ui.login;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Date;
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import lo23.data.ApplicationModel;
import lo23.data.Game;
import lo23.data.Invitation;
import lo23.data.Profile;
import lo23.data.PublicProfile;
import lo23.data.exceptions.FileNotFoundException;
import lo23.data.managers.GameManagerInterface;
import lo23.data.managers.ProfileManagerInterface;
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
    private  EndGameModel listEndGames;
    private  StopGameModel listStopGames;
    JButton reviewGameBtn;
    JButton continueGameBtn;
    
    public IhmLoginModel(ApplicationModel appModel){
        this.appModel = appModel;

        // Liste des joueurs présents
        Object[][] donnees = {};
        String[] entetes = {"id","Prénom", "Nom", "Status"};
        listPlayers = new PlayerModel();
        listPlayers.setDataVector(donnees, entetes);

        // Loste des parties terminées
        String[] entetesEndGames = {"Date","Adversary", "Result", ""};
        listEndGames = new EndGameModel();
        listEndGames.setDataVector(donnees, entetesEndGames);

        // Liste des parties en cours
        String[] entetesStopGames = {"Date","Adversary", ""};
        listStopGames = new StopGameModel();
        listStopGames.setDataVector(donnees, entetesStopGames);
        
        // test
        reviewGameBtn = new JButton("Review");
        listEndGames.addGame(new Date(), "toto", "You won!", reviewGameBtn);
        listEndGames.addGame(new Date(), "sdfsd", "You lost!", reviewGameBtn);
        listEndGames.addGame(new Date(), "todfgto", "You won!", reviewGameBtn);
        listEndGames.addGame(new Date(), "tzzzzzzzoto", "You lost!", reviewGameBtn);
        listEndGames.addGame(new Date(), "tozazzaaaazazazto", "You won!", reviewGameBtn);
        listEndGames.addGame(new Date(), "tokikikikto", "You won!", reviewGameBtn);
        
        continueGameBtn = new JButton("Continue");
        listStopGames.addGame(new Date(), "toto", continueGameBtn);
        listStopGames.addGame(new Date(), "sdfsd", continueGameBtn);
        listStopGames.addGame(new Date(), "todfgto", continueGameBtn);
        listStopGames.addGame(new Date(), "tzzzzzzzoto", continueGameBtn);
        // end test

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
//            gameManager.load(game.getGameId());
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

    public EndGameModel getEndGameModel() {
        return listEndGames;
    }
 
    public StopGameModel getStopGameModel() {
        return listStopGames;
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

    PublicProfile getRemoteProfile(String id) {
        for(PublicProfile p : listProfileDate.keySet()){
            if(p.getProfileId().equals(id))
                return p;
        }
        return null;
    }
 

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

    private class GameModel extends DefaultTableModel {

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
    }
    
    private class EndGameModel extends GameModel {
        public void addGame(Date date, String adversary, String result, JButton reviewBtn) {
            this.addRow(new Object[]{date, adversary, result, reviewBtn});
        }
    } 
    private class StopGameModel extends GameModel {
        public void addGame(Date date, String adversary, JButton reviewBtn) {
            this.addRow(new Object[]{date, adversary, reviewBtn});
        }
    } 
    
   
    
    /*
     * Permet à la classe ihmListGame d'acceder au bouton review
     */
    public JButton getReviewGameBtn() {
        return reviewGameBtn;
    }
    
    /*
     * Permet à la classe ihmListGame d'acceder au bouton continue
     */
    public JButton getContinueGameBtn() {
        return continueGameBtn;
    }
}

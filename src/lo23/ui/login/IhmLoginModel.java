/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lo23.ui.login;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
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
import lo23.data.managers.GameManager;
import lo23.data.managers.GameManagerInterface;
import lo23.data.managers.ProfileManagerInterface;
import lo23.ui.login.mockManager.GameManagerMock;
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
    ArrayList<JButton> listContinueGameBtn;
    ArrayList<JButton> listReviewGameBtn;
    ArrayList<JButton> listPlayGameBtn;
    
    public IhmLoginModel(ApplicationModel appModel){
        this.appModel = appModel;

        // Liste des joueurs présents
        Object[][] donnees = {};
        String[] entetes = {"id", "Pseudo", "FistName", "Status",""};
        listPlayers = new PlayerModel();
        listPlayers.setDataVector(donnees, entetes);
        listPlayGameBtn = new ArrayList<JButton>();
        // Loste des parties terminées
        String[] entetesEndGames = {"Date","Adversary", "Result", ""};
        listEndGames = new EndGameModel();
        listEndGames.setDataVector(donnees, entetesEndGames);
        listReviewGameBtn = new ArrayList<JButton>();
        
        // Liste des parties en cours
        String[] entetesStopGames = {"Date","Adversary", ""};
        listStopGames = new StopGameModel();
        listStopGames.setDataVector(donnees, entetesStopGames);
        listContinueGameBtn = new ArrayList<JButton>();
                
        // test
        listEndGames.addGame(new Date(), "id=5", "You won!", 5);
        listEndGames.addGame(new Date(), "id=10", "You lost!", 10);
        listEndGames.addGame(new Date(), "id=10", "You lost!", 4);
        listEndGames.addGame(new Date(), "todfgto", "You won!", 3);
        listEndGames.addGame(new Date(), "tzzzzzzzoto", "You lost!", 1);
        listEndGames.addGame(new Date(), "tozazzaaaazazazto", "You won!", 40);
        listEndGames.addGame(new Date(), "tokikikikto", "You won!", 56);
        
        listStopGames.addGame(new Date(), "toto", 1);
        listStopGames.addGame(new Date(), "sdfsd", 2);
        listStopGames.addGame(new Date(), "todfgto", 3);
        listStopGames.addGame(new Date(), "tzzzzzzzoto", 4);
        
        listPlayers.addPlayer("1","Toto", "tata", null);
        listPlayers.addPlayer("2","Titi", "tata", null);
        
        // end test
        // A décommenter après implementation correcte coté gameManager
//        GameManager gameManager = new GameManager(appModel);
//        ArrayList<Game> stopGames = gameManager.getListStopGames();
//        for (Game game : stopGames ) {
//            listStopGames.addGame(game.getEndDate(), game.getRemotePlayer().toString(), continueGameBtn.putClientProperty("id", "5"));
//        }
//        
//        ArrayList<Game> endGames = gameManager.getListEndGames();
//        for (Game game : endGames ) {
//            listEndGames.addGame(game.getEndDate(), game.getRemotePlayer().toString(), game.getWinner(), continueGameBtn);
//        }

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
        response = JOptionPane.showConfirmDialog(null, "Accept/deny invitation ?" + profile.getPseudo());
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

        public void addPlayer(String id,String pseudo, String firstname, ImageIcon ico) {
            JButton btn = new JButton("Play");
            btn.putClientProperty("id", id);
            listPlayGameBtn.add(btn);
            this.addRow(new Object[]{id, pseudo, firstname, ico, btn});
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
        public void addGame(Date date, String adversary, String result, Integer id) {
            JButton btn = new JButton("Review");
            btn.putClientProperty("id", id);
            listReviewGameBtn.add(btn);
            this.addRow(new Object[]{date, adversary, result, btn});
        }
    } 
    private class StopGameModel extends GameModel {
        public void addGame(Date date, String adversary, Integer id) {
            JButton btn = new JButton("Continue");
            btn.putClientProperty("id", id);
            listContinueGameBtn.add(btn);
            this.addRow(new Object[]{date, adversary, btn});
        }
    } 
  

    /*
     * Permet à la classe ihmListGame d'acceder aux boutons review
     */
    public ArrayList<JButton> getListReviewGameBtn() {
        return listReviewGameBtn;
    }
    
    /*
     * Permet à la classe ihmListGame d'acceder aux boutons continue
     */
    public ArrayList<JButton> getListContinueGameBtn() {
        return listContinueGameBtn;
    }
    
    public ArrayList<JButton> getListPlayGameBtn() {
        return listPlayGameBtn;
    }
}

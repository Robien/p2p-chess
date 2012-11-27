/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lo23.ui.login;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;
import lo23.data.ApplicationModel;
import lo23.data.Game;
import lo23.data.Invitation;
import lo23.data.Profile;
import lo23.data.PublicProfile;
import lo23.data.exceptions.FileNotFoundException;
import lo23.data.exceptions.WrongInvitation;
import lo23.data.managers.GameManagerInterface;
import lo23.data.managers.Manager;
import lo23.data.managers.ProfileManagerInterface;
import lo23.ui.login.mockManager.GameManagerMock;
import lo23.ui.login.mockManager.ProfileManagerMock;
import lo23.utils.Enums;
import lo23.utils.Enums.STATUS;

/**
 *
 * @author Esteban
 */
public class IhmLoginModel implements PropertyChangeListener{
    
    public static final String VIEW_PROFILE_RESPONSE = "view-profile-response";//DEPRECATED
    public static final String ADD_PLAYER_CONNECTED = "add-player-connected";
    public static final String DELETE_PLAYER_DISCONNECTED = "delete-player-disconnected";//DEPRECATED
    public static final String REQUEST_GAME_RESPONSE = "request-game-response";
    public static final String INVIT_RECEIVE = "invit-receive";
    public static final String INVIT_EXPIRED = "invit-expired";
    public static final String GAME_ENDED = "game-ended";

    private final ImageIcon ONLINEICON = new ImageIcon(getClass().getResource("/lo23/ui/resources/status_online.png.png"));
    private final ImageIcon OFFLINEICON = new ImageIcon(getClass().getResource("/lo23/ui/resources/status_offline.png"));
    
    public PropertyChangeSupport pcs;
    public ArrayList<Long> idEndGames;
    public ArrayList<Long> idStartGames;
    public ArrayList<Long> idPlayersConnected;
    private ApplicationModel appModel;

    private PlayerModel listPlayers;
    private HashMap<PublicProfile,Date> listProfileDate;
    ArrayList<JButton> listPlayersLaunchBtn;
    private  EndGameModel listEndGames;
    private  StopGameModel listStartGames;
    ArrayList<JButton> listContinueGameBtn;
    ArrayList<JButton> listReviewGameBtn;
    ArrayList<JButton> listPlayGameBtn;
    
    public IhmLoginModel(ApplicationModel appModel){
        this.appModel = appModel;
        
        ((Manager)this.appModel.getPManager()).subscribe(this, INVIT_RECEIVE);
        ((Manager)this.appModel.getPManager()).subscribe(this, INVIT_EXPIRED);
        ((Manager)this.appModel.getPManager()).subscribe(this, ADD_PLAYER_CONNECTED);
        ((Manager)this.appModel.getPManager()).subscribe(this, REQUEST_GAME_RESPONSE);

        // Liste des joueurs présents
        Object[][] donnees = {};
        String[] entetes = {"id", "Pseudo", "FistName", "Status",""};
        ProfileManagerMock profileManager = new ProfileManagerMock(appModel);
        listPlayers = new PlayerModel();
        listPlayers.setDataVector(donnees, entetes);
        listPlayGameBtn = new ArrayList<JButton>();
        listPlayersLaunchBtn = new ArrayList<JButton>();
       
        idPlayersConnected = profileManager.getIdPlayersConnected(); // pour le test
         
        // Liste des parties terminées
        GameManagerMock gameManager = new GameManagerMock(appModel);
        String[] entetesEndGames = {"Date","Adversary", "Result", ""};
        listEndGames = new EndGameModel();
        listEndGames.setDataVector(donnees, entetesEndGames);
        listReviewGameBtn = new ArrayList<JButton>();

        ArrayList<Game> endGames = gameManager.getListStopGames();
        idEndGames = gameManager.getIdStopGames(); // Pour les tests
        for (Game game : endGames ) {
            listEndGames.addGame(game.getEndDate(), game.getRemotePlayer().getPublicProfile().toString(),"result", game.getGameId());
        }
        
        // Liste des parties en cours
        String[] entetesStopGames = {"Date","Adversary", ""};
        listStartGames = new StopGameModel();
        listStartGames.setDataVector(donnees, entetesStopGames);
        listContinueGameBtn = new ArrayList<JButton>();

        ArrayList<Game> stopGames = gameManager.getListStartGames();
        idStartGames = gameManager.getIdStartGames(); // Pour les tests
        /*for (Game game : stopGames ) {
            listStartGames.addGame(game.getEndDate(), game.getRemotePlayer().getPublicProfile().toString(), game.getGameId());
        }*/
        
        listProfileDate = new HashMap<PublicProfile,Date>();

        pcs = new PropertyChangeSupport(this);
        

    }

    public void addPropertyChangeListener(String evt,PropertyChangeListener l){
        if(pcs != null)
            pcs.addPropertyChangeListener(evt,l);
    }
    
    public void acceptInvitation(Invitation invit) throws FileNotFoundException, IOException, ClassNotFoundException, WrongInvitation{
        GameManagerInterface gameManager = appModel.getGManager();
        Game game = gameManager.createGame(invit);
        gameManager.load(game.getGameId());
    }

    
    
    public void sendInvitation(String idUser,Enums.COLOR col) throws IOException, ClassNotFoundException, FileNotFoundException{
        long time = System.currentTimeMillis();
        //Instantiate DataManager
        ProfileManagerInterface profileManager = appModel.getPManager();
        //Instantiate profile and invitation
        PublicProfile p = this.getRemoteProfile(idUser);
        //Profile profile = profileManager.loadProfile(idUser);
        Invitation invit = profileManager.createInvitation(p, col, time);
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
        return listStartGames;
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getPropertyName().equals(ADD_PLAYER_CONNECTED)){
            PublicProfile profile = (PublicProfile)evt.getNewValue();

            PublicProfile p = getRemoteProfile(profile.getProfileId());
            if(p == null){
                //Add remote profile to Jtable model
                listPlayers.addPlayer(profile.getProfileId(),profile.getPseudo(),profile.getFirstName(),getIconStatus(profile));
                
                //Get all games stopped which have the id of the current profile p
                ArrayList<Game> gamesContinue = getGamesContinueFromId(profile.getProfileId());
                for(Game g : gamesContinue){
                    listStartGames.addGame(g.getEndDate(), g.getRemotePlayer().getPublicProfile().toString(), g.getGameId(),profile.getStatus());
                }
                
                //Set profile to p
                p = profile;
                pcs.firePropertyChange(ADD_PLAYER_CONNECTED, null, p);
            }
            //put p to list
            listProfileDate.put(p,new Date());
            
            
            System.out.println("Player : "+profile.getPseudo()+" added");

            removeDisconnectedProfilesAndGames();
            
        }
        if(evt.getPropertyName().equals(INVIT_RECEIVE)){
            pcs.firePropertyChange(INVIT_RECEIVE, evt.getOldValue(), evt.getNewValue());
        }
        if(evt.getPropertyName().equals(REQUEST_GAME_RESPONSE)){
            pcs.firePropertyChange(REQUEST_GAME_RESPONSE, evt.getOldValue(), evt.getNewValue());
        }
        if(evt.getPropertyName().equals(INVIT_EXPIRED)){
            pcs.firePropertyChange(INVIT_EXPIRED,evt.getOldValue(),evt.getNewValue());
        }
    }
    
    
    private ImageIcon getIconStatus(PublicProfile profile){
        if(profile.getStatus().equals(STATUS.CONNECTED)){
            return ONLINEICON;
        }
        else
            return OFFLINEICON;
    }

    private void removeDisconnectedProfilesAndGames(){
        Date now = new Date();
        for(PublicProfile p : listProfileDate.keySet()){
            Date currDate = listProfileDate.get(p);
            if(now.getTime() - currDate.getTime() >= 30*1000){
                //Remove profile from two list
                listProfileDate.remove(p);
                listPlayers.removePlayer(p.getProfileId());
                
                //Remove games associated
                ArrayList<Game> gamesContinue = this.getGamesContinueFromId(p.getProfileId());
                for(Game g : gamesContinue){
                    this.listStartGames.removeGame(g.getGameId());
                }
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

    public void loadGame(Invitation invitation) throws FileNotFoundException, IOException, ClassNotFoundException, WrongInvitation {
        Game game = appModel.getGManager().createGame(invitation);
        appModel.getGManager().load(game.getGameId());
    }

    void refreshProfileList() {
        pcs.firePropertyChange(IhmConnexionWindow.REFRESH_LIST,null,null);
    }

    private ArrayList<Game> getGamesContinueFromId(String profileId) {
        ArrayList<Game> res = new ArrayList<Game>();
        try {
            ArrayList<Game> gamesContinue = appModel.getGManager().getListStopGames();
            for(Game g : gamesContinue){
                if(g.getRemotePlayer().getPublicProfile().getProfileId().equals(profileId)){
                    res.add(g);
                }
            }
            
        } catch (IOException ex) {
            Logger.getLogger(IhmLoginModel.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(IhmLoginModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
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

        public void addPlayer(String id,String name, String firstname, ImageIcon ico) {
            
            JButton btn = new JButton("Send Invitation");
            btn.putClientProperty("id", id);
            listPlayersLaunchBtn.add(btn);
            if(ico == ONLINEICON)
                btn.setEnabled(true);
            else 
                btn.setEnabled(false);
            this.addRow(new Object[]{id,name, firstname, ico,btn});
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
        public void addGame(Date date, String adversary, String result, Long id) {
            JButton btn = new JButton("Review");
            btn.putClientProperty("id", id);
            listReviewGameBtn.add(btn);
            this.addRow(new Object[]{date, adversary, result, btn});
        }
        
        public void removeGame(Long id) {
            for (int i = 0; i < this.getRowCount(); i++) {
                if(this.getValueAt(i, 3) instanceof JButton){
                    JButton button = (JButton) this.getValueAt(i, 3);
                    if (button.getClientProperty("id") == id) {
                        this.removeRow(i);
                        return;
                    }
                }
            }
        }
        
    } 
    private class StopGameModel extends GameModel {
        public void addGame(Date date, String adversary, Long id,Enums.STATUS status) {
            JButton btn = new JButton("Continue");
            btn.putClientProperty("id", id);
            if(status.equals(Enums.STATUS.CONNECTED))
                btn.setEnabled(true);
            else
                btn.setEnabled(false);
            listContinueGameBtn.add(btn);
            this.addRow(new Object[]{date, adversary, btn});
        }
        
        public void removeGame(Long id) {
            for (int i = 0; i < this.getRowCount(); i++) {
                if(this.getValueAt(i, 2) instanceof JButton){
                    JButton button = (JButton) this.getValueAt(i, 2);
                    if (button.getClientProperty("id") == id) {
                        this.removeRow(i);
                        return;
                    }
                }
            }
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
    
    public ArrayList<JButton> getListLaunchGameBtn() {
        return  listPlayersLaunchBtn;
    }
}

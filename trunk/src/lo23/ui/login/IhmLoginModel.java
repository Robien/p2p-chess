/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lo23.ui.login;

import java.awt.Color;
import java.util.ArrayList;
import lo23.data.ApplicationModel;
import lo23.data.Game;
import lo23.data.Invitation;
import lo23.data.PublicProfile;
import lo23.data.ResumeGame;
import lo23.data.managers.ProfileManager;

/**
 *
 * @author Esteban
 */
public class IhmLoginModel {
    
    public static final String VIEW_PROFILE_RESPONSE = "view-profile-response";
    public static final String ADD_PLAYER_CONNECTED = "add-player-connected";
    public static final String DELETE_PLAYER_DISCONNECTED = "delete-player-disconnected";
    public static final String REQUEST_GAME_RESPONSE = "request-game-response";
    public static final String INVIT_RECEIVE = "invit-receive";
    public static final String INVIT_EXPIRED = "invit-expired";
    
    
    private ApplicationModel appModel;
    private ArrayList<PublicProfile> listPlayers;
    private ArrayList<Game> listEndGames;
    private ArrayList<ResumeGame> listStopGames;
    
    
    
    public IhmLoginModel(ApplicationModel appModel){
        this.appModel = appModel;
        listPlayers = new ArrayList<PublicProfile>();
        listEndGames = new ArrayList<Game>();
        listStopGames = new ArrayList<ResumeGame>();
    }
    
    public void addUser2List(PublicProfile userProfile){
        listPlayers.add(userProfile);
    }
    
    public void remUser2List(PublicProfile userProfile){
        listPlayers.remove(userProfile);
    }
    
    public void acceptInvitation(Invitation invit){
        //TODO
    }
    
    public void sendInvitation(PublicProfile user,Color col){
        //TODO
    }

    public ApplicationModel getApplicationModel() {
        return appModel;
    }
    
}

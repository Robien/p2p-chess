package lo23.data.managers;

import java.beans.PropertyChangeSupport;
import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import lo23.data.ApplicationModel;
import lo23.data.Constant;
import lo23.data.Event;
import lo23.data.Game;
import lo23.data.Invitation;
import lo23.data.Message;
import lo23.data.Move;

import lo23.data.NewInvitation;

import lo23.data.Player;

import lo23.data.Position;
import lo23.data.Profile;
import lo23.data.PublicProfile;
import lo23.data.ResumeGame;
import lo23.data.exceptions.FileNotFoundException;
import lo23.data.exceptions.IllegalMoveException;
import lo23.data.exceptions.NoIdException;
import lo23.data.pieces.GamePiece;
import lo23.data.serializer.Constants;
import lo23.data.serializer.Serializer;
import lo23.utils.Enums.COLOR;
import lo23.utils.Enums.CONSTANT_TYPE;


public class GameManager extends Manager implements GameManagerInterface {
    
    private Game currentGame;   
    
    public GameManager(ApplicationModel app) {
        super(app);
    }
    

    @Override
    public void save() throws NoIdException {
        Serializer.saveGame(currentGame);
    }

    
    @Override
    public Game load(long gameId) throws FileNotFoundException {
        return Serializer.readGame(gameId);
    }

    @Override
    public Move createMove(Position to, GamePiece piece) {
       return new Move(piece.getPosition(), to, piece);
    }

    @Override
    public void sendMove(Move move) {
        getApplicationModel().getComManager().sendMovement(move);
    }

    @Override
    public void playMove(Move move) {
        try {
            currentGame.playMove(move);
        } catch (IllegalMoveException ex) {
            Logger.getLogger(GameManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Message createMessage(String content) {
        return new Message(content, currentGame.getLocalPlayer(),
                                    currentGame.getRemotePlayer());
    }

    @Override
    public void sendMessage(Message message) {
        saveMessage(message);
        getApplicationModel().getComManager().sendChatMessage(message);
    }

    @Override
    public void saveMessage(Message message) {
        currentGame.pushEvent(message);
    }

    @Override
    public ArrayList<Event> getHistory() {
        return currentGame.getEvents();
    }

    @Override
    public void notifyChatMessage(Message message) {
        currentGame.getEvents().add(message);
    }

    @Override
    public Game createGame(Invitation invitation) {

        /* à finir: gaumont Noé
         A LA MAIN
         */
        if (invitation instanceof NewInvitation){
            /*
            Player guest = new Player(COLOR.BLACK, 400, invitation.getGuest());
            Player host = new Player(COLOR.WHITE, 400, invitation.getHost());
            currentGame = new Game(guest, host);
            currentGame.buildPieces();
            */
            NewInvitation I = (NewInvitation) invitation;
            COLOR guestColor;
            if (I.getColor()==COLOR.BLACK){
                guestColor=COLOR.WHITE;
            }else{
                guestColor=COLOR.BLACK;
            }
            //hey ! j'ai commenté tes lignes qui faisait foirer tout nos test !
            //préviens moi si tu le remet et dit moi comment le faire marcher !
            //Romain de ui grid (et Karim de Data ...)
            
            // Il faut que currentProfile ait une valeur!!!
            // remplacer "" par getApplicationModel().getPManager().getCurrentProfile().getProfileId()
            if(invitation.getGuest().getProfileId().equals("")){ // Il faut etre connecté
                // guest=local
                Player local = new Player(guestColor,I.getDuration(), invitation.getGuest());
                Player remote = new Player(I.getColor(), I.getDuration(), invitation.getHost());
                currentGame = new Game(local, remote);
                currentGame.buildPieces();
            }else{
                
                // guest = remote
                Player local = new Player(I.getColor(), I.getDuration(), invitation.getHost());
                Player remote = new Player(guestColor,I.getDuration(), invitation.getGuest());
                currentGame = new Game(local, remote);
                currentGame.buildPieces();
            }
            
        }
        else{
            //Il s'agit d'un resume game
            ResumeGame I = (ResumeGame) invitation;
            currentGame=I.getGame();
            currentGame.swapPlayer(); // Il faut inverser local et remote player
        }
        return currentGame;
    }

    @Override
    public Constant createConstant(CONSTANT_TYPE constant) {        
        return new Constant(constant, currentGame.getRemotePlayer(), currentGame.getLocalPlayer());
    }

    @Override
    public void sendConstant(Constant constant) {
        getApplicationModel().getComManager().sendConstantMessage(constant);
    }

    @Override
    public void notifyConstantMessage(Constant constant) {
        currentGame.getEvents().add(constant);
    }

    @Override
    public void saveConstant(Constant constant) {
        currentGame.getEvents().add(constant);
    }

    @Override
    public void notifyGameEnded() {
        publish("gameEnded", null);
    }

    @Override
    @Deprecated
    public Game notifyGameStarted(Invitation invitation) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList<Game> getListStopGames() {
        ArrayList<Game> gameList= getListAllGames();
        ArrayList<Integer> indexList= new ArrayList<Integer>();
        //StartGames have to be remove.
        for(int i= 0 ; i<gameList.size();i++){
            if(gameList.get(i).getEndDate()==null ){ //StartGame doesn't have an end Date.
                indexList.add(i);
            }
        }
        for(int i= 0 ; i<indexList.size();i++){
            gameList.remove(i);
        }
        return gameList;
    
    }

    @Override
    public ArrayList<Game> getListStartGames() {
        ArrayList<Game> gameList= getListAllGames();
        ArrayList<Integer> indexList= new ArrayList<Integer>();
        //EndGames have to be remove.
        for(int i= 0 ; i<gameList.size();i++){
            if(gameList.get(i).getEndDate()!=null ){ //EndGame have an end Date.
                indexList.add(i);
            }
        }
        for(int i= 0 ; i<indexList.size();i++){
            gameList.remove(i);
        }
        return gameList;
    }

    @Override
    public Game getCurrentGame() {
        return this.currentGame;
    }

    @Override
    public void notifyGameStarted(PublicProfile userProfile) {
        publish("gameStarted", userProfile);
    }

    @Override
    public void notifyMovement(Move move) {
        publish("move", move);
    }
    
    @Override
    public ArrayList<Game> getListAllGames(){
            File games = new File(Constants.GAMES_PATH);
        String[] fileList = games.list();
        
        ArrayList<Game> gameList= new ArrayList<Game>();
        
        for(int i=0; i<fileList.length;i++ ){
            try{ 
                //fileList[i] format is "gameId.game"
                //So the string is split in order to have the gameId.
                Profile cur= getApplicationModel().getPManager().getCurrentProfile();
                
                Game tmp=load(Long.parseLong(fileList[i].split(".")[0]));
                if( cur.getProfileId().equals(tmp.getLocalPlayer().getPublicProfile().getProfileId() ) ||
                    cur.getProfileId().equals(tmp.getRemotePlayer().getPublicProfile().getProfileId()) ){ //ajout de tmp si le localPLayer ou le distant est le profile connecte
                 gameList.add(tmp);
                }
            }
            catch(FileNotFoundException expt ){
                System.out.println(expt.getMessage());
                System.out.println(expt.getStackTrace());
            }            
        }
        return gameList;
    }

    public GamePiece getPieceAtXY(int x, int y) {
        return currentGame.getPieceAtXY(x, y);
    }
}

package lo23.data.managers;

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
import lo23.data.Position;
import lo23.data.PublicProfile;
import lo23.data.exceptions.FileNotFoundException;
import lo23.data.exceptions.IllegalMoveException;
import lo23.data.exceptions.NoIdException;
import lo23.data.pieces.GamePiece;
import lo23.data.serializer.Constants;
import lo23.data.serializer.Serializer;
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
    public Game load(String gameId) throws FileNotFoundException {
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
        getApplicationModel().getComManager().sendChatMessage(message);
    }

    @Override
    public void saveMessage(Message message) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ArrayList<Event> getHistory() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void notifyChatMessage(Message message) {
        currentGame.getEvents().add(message);
    }

    @Override
    public Game createGame(Invitation invitation) {
        
        throw new UnsupportedOperationException("Not supported yet.");
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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void notifyMovement(Move move) {
        throw new UnsupportedOperationException("Not supported yet.");
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
                gameList.add(load(fileList[i].split(".")[0]) );
            }
            catch(FileNotFoundException expt ){
                System.out.println(expt.getMessage());
                System.out.println(expt.getStackTrace());
            }            
        }
        return gameList;
    }
}

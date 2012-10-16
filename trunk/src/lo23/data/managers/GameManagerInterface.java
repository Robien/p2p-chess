package lo23.data.managers;

import java.util.ArrayList;
import lo23.data.Event;
import lo23.data.Game;
import lo23.data.GamePiece;
import lo23.data.Message;
import lo23.data.Move;
import lo23.data.Position;
import lo23.data.Profile;

/**
 * This interface is the one through each module should go for getting
 * and manipulating Game, Move and Message objects
 */
public interface GameManagerInterface
{
    /**
     * This method simply creates a game and returns it
     * 
     * @param localPlayer A reference to the local player
     * @param remotePlayer A reference to the remote player
     * 
     * @return  A game object
     */
    public Game createGame(Profile localPlayer, Profile remotePlayer);
    
    /**
     * This method saves a given game object to the filesystem
     * 
     * @param game The object to be saved
     */
    public void save(Game game);
    
    /**
     * This method reads the filesystem in order to parse a Game object whom
     * identifier is given as a parameter
     * 
     * @param gameId The identifier belonging to the expected Game object
     * 
     * @return Either a Game object, either null value
     */
    public Game load(String gameId);
    
    /**
     * This methods creates a new Move object
     * 
     * @param to The created movement destination
     * @param piece The concerned GamePiece object
     * 
     * @return A Move object
     */
    public Move createMove(Position to, GamePiece piece);
    
    /**
     * This method sends a move to the remote player
     * 
     * @param move The concerned Move object
     * @param game The Game object that the concerned Move object belongs to
     */
    public void sendMove(Move move, Game game);
    
    /**
     * This method triggers a UI animation corresponding to the given
     * Move object
     * (To be confirmed)
     * 
     * @param move The concerned Move object
     * @param game The Game object that the Move object belongs to
     */
    public void playMove(Move move, Game game);
    
    /**
     * This method simply creates a Message object
     * 
     * @param content The message's content
     * 
     * @return A Message object
     */
    public Message createMessage(String content);
    
    /**
     * This method sends a given Message object to the remote player
     * 
     * @param message The concerned Message object
     * @param game The Game object that the Message object belongs to
     */
    public void sendMessage(Message message, Game game);
    
    /**
     * This method saves a given Message object to the filesystem
     * 
     * @param message The concerned Message object
     */
    public void saveMessage(Message message);
    
    /**
     * This method returns a history of several events that took place
     * during a game whom identifier is passed as an argument
     * 
     * @param gameId The identifier of the concerned Game object
     * 
     * @return An ArrayList of Event objects
     */
    public ArrayList<Event> getHistory(String gameId);
    
    /**
     * This method propose a draw result to the remote player for a given
     * Game object
     * 
     * @param game The concerned Game object
     */
    public void proposeDraw(Game game);
    
    /**
     * This method accepts a draw proposal sent by the remote player
     * 
     * @param game The concerned Game object
     */
    public void acceptDraw(Game game);
    
    /**
     * This method notifies the remote player that the local player
     * is giving up.
     * 
     * @param game The concerned game
     */
    public void giveUp(Game game);
    
    /**
     * What's the aim of this method ?!
     * 
     * @param message
     * @param game 
     */
    public void receivedMessage(Message message, Game game);
    
    /**
     * And what's the aim of this method also ?!
     * 
     * @param game 
     */
    public void outOfTime(Game game);
}

package lo23.data.managers;

import java.util.ArrayList;
import lo23.data.Constant;
import lo23.data.Event;
import lo23.data.Game;
import lo23.data.Invitation;
import lo23.data.Message;
import lo23.data.Move;
import lo23.data.Position;
import lo23.data.PublicProfile;
import lo23.data.exceptions.FileNotFoundException;
import lo23.data.exceptions.NoIdException;
import lo23.data.pieces.GamePiece;
import lo23.utils.Enums;

/**
 * This interface is the one through each module should go for getting
 * and manipulating Game, Move and Message objects
 */
public interface GameManagerInterface
{ 
    /**
     * This method saves the current Game object
     * 
     * @throws NoIdException in case of a null or empty gameId value
     */
    public void save() throws NoIdException;
    
    /**
     * This method reads the filesystem in order to parse a Game object whom
     * identifier is given as a parameter
     * 
     * @param gameId The identifier belonging to the expected Game object
     * 
     * @return Either a Game object, either null value
     */
    public Game load(String gameId) throws FileNotFoundException;
    
    /**
     * This methods creates a new Move object
     * 
     * @param to The created movement destination
     * @param piece The concerned GamePiece object
     * 
     * @throws FileNotFoundException Exception returned if the process could not
     * read the targeted file
     * 
     * @return A Move object
     */
    public Move createMove(Position to, GamePiece piece);
    
    /**
     * This method sends a move to the remote player of the current Game object
     * 
     * @param move The concerned Move object
     */
    public void sendMove(Move move);
    
    /**
     * This method triggers a UI animation corresponding to the given
     * Move object
     * (To be confirmed)
     * 
     * @param move The concerned Move object
     */
    public void playMove(Move move);
    
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
     */
    public void sendMessage(Message message);
    
    /**
     * This method saves a given Message object to the filesystem
     * 
     * @param message The concerned Message object
     */
    public void saveMessage(Message message);
    
    /**
     * This method returns a history of several events that took place
     * during the current game
     * 
     * @return An ArrayList of Event objects
     */
    public ArrayList<Event> getHistory();
    
    /**
     * This method is invoked each time a Message is received by the local
     * player.
     * 
     * @param message The concerned message
     */
    public void notifyChatMessage(Message message);
    
    /**
     * This method is invoked when the user received an invitation and
     * accept it; it will then create a Game object.
     * 
     * @param invitation The received invitation
     * 
     * @return A Game object
     */
    public Game createGame(Invitation invitation);
    
    /**
     * This method creates a Constant object from a CONSTANT_TYPE type given
     * as an argument
     * 
     * @param constant The concerned CONSTANT_TYPE type
     * 
     * @return A Constant object
     */
    public Constant createConstant(Enums.CONSTANT_TYPE constant);
    
    /**
     * This message sends a given Constant event to the remote player
     * 
     * @param constant The Constant object to be sent
     */
    public void sendConstant(Constant constant);
    
    /**
     * This method is invoked each time the local player received a Constant
     * message.
     * 
     * @param constant The received object
     */
    public void notifyConstantMessage(Constant constant);
    
    /**
     * This method writes out the given Constant object to the filesystem
     * 
     * @param constant The object to be written
     */
    public void saveConstant(Constant constant);
    
    /**
     * This method is invoked when the current game is finished
     */
    public void notifyGameEnded();
    
    /**
     * This method is invoked when the remove player, which has been sent an
     * invitation, has accepted the invitation.
     * 
     * @param invitation The given invitation
     * 
     * @return A Game object
     */
    public Game notifiyGameStarted(Invitation invitation);
    
    /**
     * This method returns a list of ended games.
     * 
     * @return An ArrayList object of Game objects
     */
    public ArrayList<Game> getListStopGames();
    
    /**
     * This method returns a list of started games.
     * 
     * @return An ArrayList object of Game objects
     */
    public ArrayList<Game> getListStartGames();
    
    /**
     * This is a simple getter to the currentGame attribute
     * 
     * @return The current game
     */
    public Game getCurrentGame();

    /**
     * Notify the start of a game session.
     * @param userProfile the user who start the game session
     */
    public void notifyGameStarted(PublicProfile userProfile);

    /**
     * Notify a movement of a piece.
     * (only when a game session is started)
     * @param move the movement of a piece
     */
    public void notifyMovement(Move move);
}
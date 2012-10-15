package lo23.connexion;

/**
 * This interface is used for send messages to distant users,
 * It's implemented by the network module.
 * Check also IReceiveListener to understand the functioning of messages.
 */
public interface ISender {

    /**
     * Send a message to all users in order to receive a response
     * from each users.
     */
    public void sendMulticast();
    
    /**
     * Send a invitation to a user.
     * @param userProfile the user who will receive the invitation 
     */
    public void sendInvit(PublicProfile userProfile);
    
    /**
     * Send a response to a invitation.
     * @param userProfile the user who will receive the answer
     * @param answer the answer to the invitation
     */
    public void sendInvitAnswer(PublicProfile userProfile, boolean answer);
    
    /**
     * Start a game session with a user.
     * @param userProfile the user who will be the oponent
     */
    public void sendStartGame(PublicProfile userProfile);
    
    /**
     * Send a loading game which can be a new game or a old saved game.
     * (available only when a game session is started)
     * @param game the game to load
     */
    public void sendLoadingGame(Game game);
    
    /**
     * Send a response to a loading game.
     * (available only when a game session is started)
     * @param answer the answer to the loading game
     */
    public void sendLoadingGameAnswer(boolean answer);
    
    /**
     * Send a chat message.
     * (available only when a game session is started)
     * @param message the chat message
     */
    public void sendChatMessage(String message);
    
    /**
     * Send a movement of a piece.
     * (available only when a game session is started)
     * @param move the movement of a piece
     */
    public void sendMovement(Move move);
    
    /**
     * End the current game session.
     * (available only when a game session is started)
     */
    public void sendGameEnded();
    
}

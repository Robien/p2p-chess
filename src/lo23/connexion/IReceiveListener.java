package lo23.connexion;

/**
 * This interface allow to notify a receive message,
 * It needs to be implemented by the model.
 * Check also ISender to understand the functioning of messages.
 */
public interface IReceiveListener {
    
    /**
     * Notify the message receive in response to sendMulticast.
     * @param userProfile the user who responded
     */
    public void notifyAddUser(PublicProfile userProfile);
    
    /**
     * Notify a invitation.
     * @param userProfile the user who sent the invitation
     */
    public void notifyInvit(PublicProfile userProfile);
    
    /**
     * Notify the response to a invitation.
     * @param userProfile the user who sent the answer
     * @param answer the answer of the invitation
     */
    public void notifyInvitAnswer(PublicProfiler userProfile, boolean answer);
    
    /**
     * Notify the start of a game session.
     * @param userProfile the user who start the game session
     */
    public void notifyStartGame(PublicProfile userProfile);
    
    /**
     * Notify a loading game.
     * (only when a game session is started)
     * @param game the game to load
     */
    public void notifyLoadingGame(Game game);
    
    /**
     * Notify the response to the last loading game proposal.
     * (only when a game session is started)
     * @param answer the answer to the loading game
     */
    public void notifyLoadingGameAnswer(boolean answer);
    
    /**
     * Notify a chat message.
     * (only when a game session is started)
     * @param message the chat message
     */
    public void notifyChatMessage(String message);
    
    /**
     * Notify a movement of a piece.
     * (only when a game session is started)
     * @param move the movement of a piece
     */
    public void notifyMovement(Move move);
    
    /**
     * Notify the end of the current game session.
     * (only when a game session is started)
     */
    public void notifyGameEnded();
    
}

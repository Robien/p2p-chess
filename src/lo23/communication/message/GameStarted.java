package lo23.communication.message;

import lo23.data.PublicProfile;

/**
 * This message is sent to notify the distant client that the game is started.
 */
public class GameStarted {

    private PublicProfile guest;

    /**
     * Contructor of the GameStarted.
     * @param guest the distant PublicProfile connected to the current client.
     */
    public GameStarted(PublicProfile guest) {
        this.guest = guest;
    }

    public void setGuest(PublicProfile guest) {
        this.guest = guest;
    }

    public PublicProfile getGuest() {
        return guest;
    }

}

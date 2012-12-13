package lo23.communication.message;

import lo23.data.PublicProfile;

/**
 * This message is sent to notify the distant client that the game is started.
 */
public class GameStartedMsg extends ConnectionMessage{

    private PublicProfile guest;
    private boolean started;

    /**
     * Constructor of the GameStarted.
     * @param guest the distant PublicProfile connected to the current client.
     */
    public GameStartedMsg(PublicProfile guest, boolean started) {
        this.guest = guest;
        this.started = started;
    }

    public void setGuest(PublicProfile guest) {
        this.guest = guest;
    }

    public PublicProfile getGuest() {
        return guest;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }
}

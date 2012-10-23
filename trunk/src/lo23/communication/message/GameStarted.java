package lo23.communication.message;

import lo23.data.PublicProfile;

public class GameStarted {

    private PublicProfile guest;

    /**
     * Contructor of the GameStarted.
     * @param guest the distant PublicProfile connected to the current client.
     */
    public GameStarted(PublicProfile guest) {
        this.guest = guest;
    }
}

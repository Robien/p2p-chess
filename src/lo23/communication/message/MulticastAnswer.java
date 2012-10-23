package lo23.communication.message;

import lo23.data.PublicProfile;

public class MulticastAnswer {

    private PublicProfile guest;

    /**
     * Contructor of the MulticastAnswer.
     * @param guest the distant PublicProfile to add to the local PublicProfile list of the current client.F
     */
    public MulticastAnswer(PublicProfile guest) {
        this.guest = guest;
    }
}

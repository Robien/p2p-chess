/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lo23.data;

/**
 *
 * @author Pierre-Alexandre FONTA et Louis PONTOISE
 */
public abstract class Invitation {

    private PublicProfile host;
    private PublicProfile guest;

    public Invitation(PublicProfile host, PublicProfile guest) {
        this.host = host;
        this.guest = guest;
    }
    
    
    /**
     * Getter for the host attribute
     * 
     * @return The host attribute
     */
    public PublicProfile getHost()
    {
        return host;
    }
    
    /**
     * Getter for the guest attribute
     * 
     * @return The guest attribute
     */
    public PublicProfile getGuest()
    {
        return guest;
    }
}

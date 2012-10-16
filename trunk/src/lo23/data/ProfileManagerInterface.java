/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lo23.data;

import java.util.Collection;

/**
 *
 * @author Pierre-Alexandre FONTA et Louis PONTOISE
 */
public interface ProfileManagerInterface {

    /**
     *
     * @param pseudo
     * @param password
     * @return
     */
    public Profile createProfile(String pseudo, String password);

    /**
     *
     * @return
     */
    public Collection<Profile> getProfilesList();

    /**
     *
     * @param profileId
     * @return
     */
    public PublicProfile getPublicProfile(String profileId);

    /**
     *
     * @param pseudo
     * @param password
     * @return
     */
    public boolean login(String pseudo, String password);

    /**
     *
     * @param profileId
     */
    public void saveProfile(String profileId);

    /**
     *
     * @param profileId
     * @return
     */
    public Profile loadProfile(String profileId);

    /**
     *
     * Utiliser à la place les setteurs de la classe Profile.
     * @param invitation
     * @author Pierre-Alexandre FONTA et Louis PONTOISE
     */
    //public void modifyProfile();
    public void receiveInvitation(Invitation invitation);

    /**
     *
     * @param invitation
     */
    public void sendInvitation(Invitation invitation);
    /**
     * Commenté car le type Color n'a pas encore été défini dans le package
     * utils et car le type Time n'a pas encore été choisi.
     * @param guest
     * @param color
     * @param duration
     * @return
     */
    //public Invitation createInvitation(PublicProfile guest, Color color, Time duration);
}

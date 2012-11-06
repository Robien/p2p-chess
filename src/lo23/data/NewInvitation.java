/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lo23.data;

import java.util.Date;
import lo23.utils.Enums.COLOR;

/**
 *
 * @author Pierre-Alexandre FONTA et Louis PONTOISE
 */
public class NewInvitation extends Invitation {

    public NewInvitation(PublicProfile host, PublicProfile guest) {
        super(host, guest);
    }

    private COLOR color;
    private long duration;
    private Date date;
}

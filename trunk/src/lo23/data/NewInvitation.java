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
        date=new Date();
    }

    private COLOR color;
    private long duration;
    private Date date;
    
    public COLOR getColor(){
        return color;
    }
    
    public Date getDate(){
        return date;
    }
    
    public long getDuration(){
        return duration;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lo23.data;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author khamidou
 */
public abstract class Event {
    private Date date;

    public Event() {
        date = new Date();
    }
}

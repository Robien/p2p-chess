/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lo23.data;

import java.io.Serializable;

/**
 *
 * @author khamidou
 */
public class Message extends Event implements Serializable {
    private String contents;
    private Player sender;
    private Player receiver;

    public Message(String contents, Player sender, Player receiver) {
        this.contents = contents;
        this.sender = sender;
        this.receiver = receiver;
    }

    public String getContents() {
        return contents;
    }

}

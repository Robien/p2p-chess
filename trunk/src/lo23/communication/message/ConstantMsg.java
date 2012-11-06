/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package lo23.communication.message;
import lo23.data.Constant;

/**
 *
 * @author penotvin
 */
public class ConstantMsg extends GameMessage {

    private Constant constant;

    public ConstantMsg(Constant constant){
        this.constant = constant;
    }
    
    public void setConstant(Constant constant) {
        this.constant = constant;
    }

    public Constant getConstant() {
        return constant;
    }

}

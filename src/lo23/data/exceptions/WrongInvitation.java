
package lo23.data.exceptions;

/**
 * Thrown in createGame when a invitation don't contain the currentPrfile
 * @author ksadorf
 */

public class WrongInvitation extends Exception {
    public WrongInvitation()
    {
        super();
    }

    public WrongInvitation(String msg)
    {
        super(msg);
    }
}

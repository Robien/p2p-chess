package lo23.data.exceptions;

public class ProfileIdAlreadyExistException extends Exception
{
    public ProfileIdAlreadyExistException()
    {
        super();
    }

    public ProfileIdAlreadyExistException(String message)
    {
        super(message);
    }
}

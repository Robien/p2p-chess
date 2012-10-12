package lo23.data.serializer;

/**
 * This is an exception thrown by the Serializer class
 * It's thrown when it's called to serialize an objet that has no profileId
 */
public class NoIdException extends Exception
{
    public NoIdException()
    {
        super();
    }
    
    public NoIdException(String msg)
    {
        super(msg);
    }
}

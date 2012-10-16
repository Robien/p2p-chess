package lo23.data.serializer;



/**
 * This exception class is the one which will be thrown when the program
 * is trying to access a file that doesn't exist, but
 * that is supposed to.
 */
public class FileNotFoundException extends Exception
{
    public FileNotFoundException()
    {
        super();
    }
    
    public FileNotFoundException(String message)
    {
        super(message);
    }
}

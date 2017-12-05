/*  ==================================================================================================================*\
 *   Ben Mariem Sami - Derroitte Natan - Testouri Mehdi -     Project  - O.O.S.E
 *
 *    ProtocolException class
\*  ==================================================================================================================*/
import java.io.IOException;

public class ProtocolException extends IOException
{
    /**
     * ProtocolException constructor
     * @param message : error message
     */
    public ProtocolException(String message)
    {
        super(message);
    }
}

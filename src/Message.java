/*  ==================================================================================================================*\
 *   Ben Mariem Sami - Derroitte Natan - Testouri Mehdi -     Project  - O.O.S.E
 *
 *    Message class
\*  ==================================================================================================================*/

public class Message
{
    public User sender;
    public int date;
    public String msg;

    /**
     * Message constructor
     * @param sender : name of the sender
     * @param date : date at which the message was sent
     * @param msg : message
     */
    public Message(User sender, int date, String msg)
    {
        this.sender = sender;
        this.date = date;
        this.msg = msg;
    }
}

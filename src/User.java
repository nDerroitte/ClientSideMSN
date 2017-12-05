/*  ==================================================================================================================*\
 *   Ben Mariem Sami - Derroitte Natan - Testouri Mehdi -     Project  - O.O.S.E
 *
 *    User class
\*  ==================================================================================================================*/
public class User
{
    private String nickname;
    private int status;

    /**
     * User Constructor
     * @param nickname : name of the client
     * @param status : Status of the client
     */
    public User(String nickname, int status)
    {
        this.nickname = nickname;
        this.status = status;
    }


    public String getNickname()
    {
        return nickname;
    }

    /**
     * Return the status
     * @return : status
     */
    public int getStatus()
    {
        return status;
    }

    /**
     * Set the status
     * @param status : status
     */
    public void setStatus(int status)
    {
        this.status= status;
    }
}

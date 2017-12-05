/*  ==================================================================================================================*\
 *   Ben Mariem Sami - Derroitte Natan - Testouri Mehdi -     Project  - O.O.S.E
 *
 *    AbstractSender class
\*  ==================================================================================================================*/

public abstract class AbstractSender
{
    protected NetworkHandler networkHandler;

    /**
     * AbstractSender constructor
     * @param networkHandler : network handler
     */
    public AbstractSender(NetworkHandler networkHandler)
    {
        this.networkHandler = networkHandler;
    }

    /**
     * Transmit the authentication request from the GUI to the server
     * @param login : user login
     * @param password : password
     * @throws Exception : exception if the request is not valid or fails
     */
    protected abstract void authentication(String login,String password) throws Exception;

    /**
     * Transmit the status update request from the GUI to the server
     * @param status : new status of the user
     * @throws Exception : exception if the request is not valid or fails
     */
    protected abstract void upDateStatus(int status) throws Exception;

    /**
     * Transmit the see people request from the GUI to the server
     * @throws Exception : exception if the request is not valid or fails
     */
    protected abstract void seePeople() throws Exception;

    /**
     * Transmit the send message request from the GUI to the server
     * @param nickname : nickname of the receiver
     * @param msg : message
     * @throws Exception : exception if the request is not valid or fails
     */
    protected abstract void sendMsg(String nickname, String msg) throws Exception;
}

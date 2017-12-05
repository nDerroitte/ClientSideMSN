/*  ==================================================================================================================*\
 *   Ben Mariem Sami - Derroitte Natan - Testouri Mehdi -     Project  - O.O.S.E
 *
 *    Sender class
\*  ==================================================================================================================*/
import java.io.IOException;
import java.util.Vector;

public class Sender extends AbstractSender
{
    private Vector<Integer> requests;
    private ReceiverThread receiverThread;

    /**
     * Sender constructor
     * @param networkHandler : network handler
     */
    public Sender(NetworkHandler networkHandler)
    {
        super(networkHandler);
        this.requests = new Vector<>();
    }

    /**
     * Assign a receiver thread to the sender
     * @param receiverThread : receiver thread
     */
    public void assignReceiverThread(ReceiverThread receiverThread)
    {
        this.receiverThread = receiverThread;
    }

    /**
     * Send an authentication request to the server
     * @param login : user login
     * @param password : password
     * @throws IOException : exception if the sending fails
     */
    protected synchronized void authentication(String login,String password) throws IOException
    {
        networkHandler.send(Protocol.authentication(login,password));
        networkHandler.setSocketTimeout(Constants.SOCKET_TIMEOUT);
        this.requests.add(Constants.USER_LOGIN);
        new Thread(receiverThread).start();
    }

    /**
     * Send a status update request to the server
     * @param status : new status of the user
     * @throws IOException : exception if the sending fails
     */
    protected synchronized void upDateStatus(int status) throws IOException
    {
        networkHandler.send(Protocol.statusUpdate(status));
    }

    /**
     * Send a see people request to the server
     * @throws IOException : exception if the sending fails
     */
    protected synchronized void seePeople() throws IOException
    {
        networkHandler.send(Protocol.seeConnected());
        this.requests.add(Constants.SEE_ONLINE);
    }

    /**
     * Send a send message request to the server
     * @param nickname : nickname of the receiver
     * @param msg : message
     * @throws IOException : exception if the sending fails
     */
    protected synchronized void sendMsg(String nickname, String msg) throws IOException
    {
        networkHandler.send(Protocol.sendMsg(nickname,msg));
        //this.requests.add(Constants.SEND_MSG);
    }

    /**
     * Send a reply to the server
     * @param id : id
     * @throws IOException : exception if the sending fails
     */
    public synchronized void reply(int id) throws IOException
    {
        networkHandler.send(Protocol.reply(id));
    }

    /**
     * Return the stored requests
     * @return integer vector of requests
     */
    public Vector<Integer> requests()
    {
        return requests;
    }

    /**
     * Close the connection with the server
     */
    public void closeConnection()
    {
        try
        {
            networkHandler.disconnect();
        }
        catch (IOException e)
        {
            //e.printStackTrace();
        }
    }
}

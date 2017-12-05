/*  ==================================================================================================================*\
 *   Ben Mariem Sami - Derroitte Natan - Testouri Mehdi -     Project  - O.O.S.E
 *
 *    ReceiverThread class
\*  ==================================================================================================================*/
import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class ReceiverThread extends AbstractReceiver
{
    private Sender sender;

    /**
     * ReceiverThread constructor
     * @param guiManager : GUI manager
     * @param networkHandler : network handler
     * @param sender : sender object
     */
	public ReceiverThread(GUIManager guiManager, NetworkHandler networkHandler, Sender sender)
    {
		super(guiManager, networkHandler);
		this.sender = sender;
	}

    /**
     * Implements the receiver function to handle the message from the server using
     * the protocol to retrieve the data from the message
     */
    public void run()
    {
        while(true)
        {
            try
            {
                byte[] bytes = rcv();

                switch (Protocol.retrieveId(bytes))
                {
                    case Constants.RCV_MSG:
                        if(!sender.requests().contains(Constants.USER_LOGIN) && !sender.requests().contains(Constants.NONE))
                        {
                            try
                            {
                                Message message = Protocol.retrieveMsg(bytes);
                                receivedMessage(message.sender, message.date, message.msg);
                                sender.reply(Constants.OK);
                            }
                            catch (ProtocolException e)
                            {
                                sender.reply(Constants.BAD_REQUEST);
                            }
                        }
                        break;

                    case Constants.RCV_STATUS:
                        if(!sender.requests().contains(Constants.USER_LOGIN) && !sender.requests().contains(Constants.NONE))
                        {
                            update(Protocol.retrieveStatus(bytes));
                        }
                        break;

                    case Constants.OK:
                        if(sender.requests().contains(Constants.USER_LOGIN))
                        {
                            networkHandler.setSocketTimeout(0);
                            notifyLogin(Constants.LOGIN_SUCCESS);
                            sender.requests().remove((Object)Constants.USER_LOGIN);
                        }
                        if(sender.requests().contains(Constants.SEE_ONLINE))
                        {
                            update(Protocol.retrieveStatus(bytes));
                            sender.requests().remove((Object)Constants.SEE_ONLINE);
                        }
                        break;

                    case Constants.BAD_REQUEST:
                        if(sender.requests().contains(Constants.USER_LOGIN))
                        {
                            networkHandler.setSocketTimeout(0);
                            notifyLogin(Constants.LOGIN_FAILURE);
                            popError(Constants.BAD_REQUEST_LOGIN);
                            break;
                        }
                        popError(Constants.BAD_REQUEST_ERROR);
                        break;

                    case Constants.INVALID_PASSWORD:
                        networkHandler.setSocketTimeout(0);
                        notifyLogin(Constants.LOGIN_FAILURE);
                        popError(Constants.INVALID_PASSWORD_ERROR);
                        break;

                    case Constants.BLACKLISTED_CLIENT:
                        networkHandler.setSocketTimeout(0);
                        notifyLogin(Constants.LOGIN_FAILURE);
                        popError(Constants.BLACKLISTED_CLIENT_ERROR);
                        break;

                    case Constants.UNKNOWN_USER:
                        popError(Constants.UNKNOWN_USER_ERROR);
                        break;
                }
            }
            catch (SocketTimeoutException socketTimeoutException)
            {
                //socketTimeoutException.printStackTrace();
                notifyLogin(Constants.LOGIN_TIMEOUT);
                popError(Constants.CONNECTION_TIMEOUT_ERROR);
            }
            catch (SocketException socketException)
            {
                popError(Constants.CONNECTION_LOST);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * Read the bytes from the server and retrieve a message from the server
     * @return : the byte array corresponding to a message
     * @throws IOException : exception if the function fails
     */
    private byte[] rcv() throws IOException
    {
        int i = 0;
        byte[] size_bytes = new byte[Constants.SIZE_LENGTH];
        int size = 0;

        while(i < Constants.SIZE_LENGTH)
        {
            size_bytes[i] =  networkHandler.rcv();
            i++;
        }
        size = Protocol.retrieveSizeUnchecked(size_bytes);
        i = 0;
        byte[] data = new byte[0];

        if(size > 0)
        {
            data = new byte[Constants.SIZE_LENGTH+size];
            while(i < Constants.SIZE_LENGTH)
            {
                data[i] = size_bytes[i];
                i++;
            }
            while(i < size+Constants.SIZE_LENGTH)
            {
                data[i] =  networkHandler.rcv();
                i++;
            }
        }
        return data;
    }
}

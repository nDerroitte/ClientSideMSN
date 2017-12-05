/*  ==================================================================================================================*\
 *   Ben Mariem Sami - Derroitte Natan - Testouri Mehdi -     Project  - O.O.S.E
 *
 *    AbstractReceiver class
\*  ==================================================================================================================*/
import java.util.Vector;

public abstract class AbstractReceiver implements Runnable
{
    protected NetworkHandler networkHandler;
    protected GUIManager guiManager;

    /**
     * AbstractReceiver constructor
     * @param guiManager : GUI manager
     * @param networkHandler : network handler
     */
    public AbstractReceiver(GUIManager guiManager, NetworkHandler networkHandler)
    {
        this.networkHandler = networkHandler;
        this.guiManager = guiManager;
    }

    /**
     * Notifies the GUI about the state of the login
     * @param loginState : state of the login retrieved from the server response
     */
    protected void notifyLogin(int loginState)
    {
        GUIManager.notifyLogin(loginState);
    }

    /**
     * Transmit the received message from a user to the GUI
     * @param client : nickname of the sender
     * @param time : time at which the message was send
     * @param message : message from the sender
     */
    protected void receivedMessage(User client,int time, String message)
    {
        GUIManager.receivedMessage(client,time,message);
    }


    /**
     * Transmit the users status from the server to the GUI
     * @param users : vector of user to update
     */
    protected void update(Vector<User> users)
    {
        GUIManager.update(users);
    }

    /**
     * Display an error
     * @param error : string corresponding to the error
     */
    protected void popError(String error)
    {
        GUIManager.popError(error);
    }
}

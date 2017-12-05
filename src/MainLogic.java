/*  ==================================================================================================================*\
 *   Ben Mariem Sami - Derroitte Natan - Testouri Mehdi -     Project  - O.O.S.E
 *
 *    MainLogic class
\*  ==================================================================================================================*/
import java.io.IOException;
import java.util.Vector;

public final class MainLogic
{
    /**
     * Blocking method allowing to wait that the login is finish before launching the boddy list
     * @return : the state of WaitLogin
     */
    private synchronized  static boolean check()
    {
        return (GUI.WaitLogin);
    }

    /**
     * Main methods of our Client module
     * @param args : Should be empty.
     */
    public static void main(String args[])
    {
        //Create the Network Handler
        NetworkHandler networkHandler = new NetworkHandler();

        //Create the Sender
        Sender sender = new Sender(networkHandler);

        //Create the GUIManager and the receiver
        GUIManager manager = new GUIManager(sender);
        ReceiverThread receiverThread = new ReceiverThread(manager,networkHandler,sender);
        sender.assignReceiverThread(receiverThread);

        //We established the connection to the Server.
        try
        {
            networkHandler.connect(Constants.SERVER_ADDRESS,Constants.PORT,Constants.CONNECTION_TIMEOUT);
        }
        catch (IOException e)
        {
            System.err.println(Constants.CONNECTION_ERROR);
            return;
        }

        //Launch the GUI
        manager.launchProgram();

        //Wait that the connection phase is over.
        while(check()){}

        //Create the boddy list.
        Vector<User> list = new Vector<>();
        manager.newBoddyList(list);
    }
}

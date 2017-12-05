/*  ==================================================================================================================*\
 *   Ben Mariem Sami - Derroitte Natan - Testouri Mehdi -     Project  - O.O.S.E
 *
 *    GUIInstantiation class
\*  ==================================================================================================================*/
import java.io.IOException;
import java.util.Vector;

public class GUIInstantiation
{
    /**
     * Create a new Login window
     * @param sender : Object responsible to make the link between the GUI and the Network. Allows the GUI to
     *               send request to the server.
     * @return log : Reference to the GUILogin object correspoding to the window.
     */
    public static GUILogin createLoginWindow(Sender sender)
    {
        GUILogin log = new GUILogin(sender);
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                log.setVisible(true);
            }
        });
        return log;
    }

    /**
     * Create a new Message window
     * @param him : An UserGUI object corresponding to the client whom we want to send a message
     * @param us : An UserGUI object corresponding to the client who want to send the message
     * @param sender : Object responsible to make the link between the GUI and the Network. Allows the GUI to
     *               send request to the server.
     * @return conv : Reference to the GUILogin object corresponding to the window.
     */
    public static GUIMessageWindow createMessageWindow(UserGUI him, UserGUI us, Sender sender)
    {
        GUIMessageWindow conv =   new GUIMessageWindow(him,us,sender);
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                conv.setVisible(true);
            }
        });
        return conv;
    }

    /**
     * Create a new List Window.
     * @param list : A list of UserGUI corresponding to the list of Clients connected to the server.
     * @param us : An UserGUI object corresponding to the client who want to generate the boddy List.
     * @param sender : Object responsible to make the link between the GUI and the Network. Allows the GUI to
     *               send request to the server.
     * @return boddyList : Reference to the GUILogin object corresponding to the window.
     * @throws IOException
     */
    public static GUIList createListWindow(Vector<UserGUI> list, UserGUI us, Sender sender) throws IOException
    {
        GUIList boddyList = new GUIList(list,us,sender);
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                boddyList.setVisible(true);
            }
        });
        return boddyList;
    }

    /**
     * Create a new Error window.
     * @param Error : A string containing the error.
     * @return error : Reference to the GUIError.
     */
    public static GUIError createErrorWindow(String Error)
    {
        GUIError error = new GUIError(Error);
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                error.setVisible(true);
            }
        });
        return error;
    }
}

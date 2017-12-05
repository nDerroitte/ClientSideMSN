/*  ==================================================================================================================*\
 *   Ben Mariem Sami - Derroitte Natan - Testouri Mehdi -     Project  - O.O.S.E
 *
 *    GUIManager class
\*  ==================================================================================================================*/
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Vector;

public class GUIManager
{
    private static UserGUI me;
    private static Vector<UserGUI> knownUsers;
    private static GUILogin logGUI;
    private static GUIList listGUI;
    private static Sender sender;

    /**
     * Constructor of the GUIManager
     * @param sender : : Object responsible to make the link between the GUI and the Network. Allows the GUI to
     *               send request to the server.
     */
    public GUIManager(Sender sender)
    {
        GUIManager.knownUsers = new Vector<>();
        GUIManager.sender = sender;
    }

    /**
     * We launch the program, ie create a new login window.
     */
    public void launchProgram()
    {
        logGUI= GUIInstantiation.createLoginWindow(sender);
    }

    /**
     * Allows the Login to keep going and react in function of the value of login
     * @param loginState : the new value of login.
     *                   @see GUILogin
     */
    public static void notifyLogin(int loginState)
    {
        logGUI.notifyLogin(loginState);
    }

    /**
     * Transform the vector of User in a Vector of UserGUI and generates a new List of Users Window
     * @param list : the Vector of User corresponding to the list of connected clients to the server.
     */
    public void newBoddyList(Vector<User> list)
    {
        //Transformation
        for(int i =0; i<list.size();i++)
            knownUsers.add(new UserGUI(list.get(i).getNickname(),list.get(i).getStatus()));
        //New window creation
        try
        {
            listGUI =GUIInstantiation.createListWindow(new Vector<>(knownUsers),me,sender);
        }
        catch (IOException e)
        {
            //e.printStackTrace();
        }
    }

    /**
     * Transform the time in Unix form to a String in form "HH:mm"
     * @param UnixTime : Unix form of the time
     * @return String of the time in form "HH:mm"
     */
    private static String changeTime(int UnixTime)
    {
        Date date = Date.from( Instant.ofEpochSecond( UnixTime ) );
        DateFormat HM = new SimpleDateFormat("HH:mm");
        return HM.format(date);
    }

    /**
     * Method responsible to make appear the message received in the GUI.
     * @param Client : The user whom sent us the message
     * @param UnixTime : The time in the Unix form at when the message was sent
     * @param message : The string containing the message
     */
    public static void receivedMessage(User Client,int UnixTime, String message)
    {
        //Change the time in a "HH:mm" form.
        String time = changeTime(UnixTime);
        for (int i =0; i< knownUsers.size();i++)
        {
            //If we know the user who sent us the message
            if (Client.getNickname() == knownUsers.get(i).getNickname())
            {
                //If we are not currently speaking to him, create a new message window
                if (knownUsers.get(i).conversation == null)
                {
                    knownUsers.get(i).conversation = GUIInstantiation.createMessageWindow(knownUsers.get(i), me,sender);
                    knownUsers.get(i).conversation.receviedMessage(message, time);
                    return;
                }
                //Else, add the message in the window
                else
                {
                    knownUsers.get(i).conversation.receviedMessage(message, time);
                    return;
                }
            }
        }
        //The User is unknown.
        // Add the User to the known User and create a new message window containing the message
        UserGUI newUser = new UserGUI(Client.getNickname(),Client.getStatus());
        knownUsers.add(newUser);
        newUser.conversation = GUIInstantiation.createMessageWindow(newUser,me,sender);
        newUser.conversation.receviedMessage(message,time);
        //If the list window was already created, we add the User to the list.
        if(listGUI!=null)
            listGUI.updateList(knownUsers);
    }

    /**
     * Method responsible to make a error pop up. Create a new erorr window.
     * @param error : the String containing the error message.
     */
    public static void popError(String error)
    {
        GUIInstantiation.createErrorWindow(error);
    }

    /**
     * Update the list of users connected. That can be due to a changing state or a new user connected.
     * @param list : List of users we have to update.
     */
    public static void update(Vector<User> list)
    {
        updateKnownUser(list);
        System.out.println(knownUsers.size());
        listGUI.updateList(new Vector<>(knownUsers));
    }

    /**
     * Method responsible to update the list of users. If the new users aren't know, we add them.
     * Otherwise we just update their status.
     * @param list : the list of users to update and new users.
     */
    private static void updateKnownUser(Vector<User> list)
    {
        boolean added;
        for (int i =0 ; i < list.size();i++)
        {
            added = false;
            for(int j = 0;j<knownUsers.size();j++)
            {
                if(knownUsers.get(j).getNickname().equals(list.get(i).getNickname()))
                {
                    knownUsers.get(j).setStatus(list.get(i).getStatus());
                    added = true;
                    break;
                }
            }
            if(!added && list.get(i).getNickname() != me.getNickname())
                knownUsers.add(new UserGUI(list.get(i).getNickname(), list.get(i).getStatus()));
        }
    }

    /**
     * Method allowing the GUIManager to know what is the name and the status of the Client who is using the program
     * @param name : name of the Client.
     */
    public static void updateMe(String name)
    {
         me = new UserGUI(name,Constants.ONLINE);
    }
}

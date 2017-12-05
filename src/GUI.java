/*  ==================================================================================================================*\
 *   Ben Mariem Sami - Derroitte Natan - Testouri Mehdi -     Project  - O.O.S.E
 *
 *    GUI abstract class
\*  ==================================================================================================================*/
public abstract class GUI extends javax.swing.JFrame
{
    /**
     * Method used to initialise the corresponding GUI.
     */
    protected abstract  void initComponents();

    //Inheritance variables
    protected static String Police = "src/GUI/Font/FORCED.ttf";

    protected static String ImageAvailable = "/GUI/Images/1.png";
    protected static String ImageBusy = "/GUI/Images/3.png";
    protected static String ImageAway = "/GUI/Images/2.png";

    protected static String ImageBackground = "/GUI/Images/totalv4.png";

    protected static String ImageButton = "/GUI/Images/button.png";

    protected static boolean WaitLogin = true;

    protected Sender sender;
}

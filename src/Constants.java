/*  ==================================================================================================================*\
 *   Ben Mariem Sami - Derroitte Natan - Testouri Mehdi -     Project  - O.O.S.E
 *
 *    Constants class
\*  ==================================================================================================================*/
public final class Constants
{
    private Constants()
    {
        System.err.print("This class should never be instantiated.");
    }

    // server
    //final static String SERVER_ADDRESS = "localhost";
    final static String SERVER_ADDRESS = "marauder1.run.montefiore.ulg.ac.be";
    final static int PORT = 8080;
    final static int CONNECTION_TIMEOUT = 20000;
    final static int SOCKET_TIMEOUT = 10000;

    // byte table
    final static int INDEX_SIZE = 0;
    final static int INDEX_METHOD = 4;
    final static int INDEX_DATA = 5;
    final static int SIZE_LENGTH = 4;
    final static int ID_LENGTH = 1;
    final static byte SEPARATION = -1;
    final static int SEP_LENGTH = 1;
    final static int MAX_SIZE = 255;
    final static int DATE_LENGTH = 8;

    // errors
    final static String INVALID_SIZE = "Invalid size";
    final static String INVALID_ID = "Invalid method ID";
    final static String INVALID_DATA = "Invalid data";
    final static String INVALID_STATUS = "Invalid status";

    // GUI errors
    final static String CLOSING_ERROR = " (Closing this window will close the program)";
    final static String CONNECTION_ERROR = "Unable to reach server";
    final static String CONNECTION_TIMEOUT_ERROR = "Connection timeout" + CLOSING_ERROR;
    final static String BAD_REQUEST_ERROR = "101 BAD_REQUEST";
    final static String BAD_REQUEST_LOGIN = "101 BAD_REQUEST" + CLOSING_ERROR;
    final static String INVALID_PASSWORD_ERROR = "Invalid Password" + CLOSING_ERROR;
    final static String BLACKLISTED_CLIENT_ERROR = "103 BLACKLISTED_CLIENT" + CLOSING_ERROR;
    final static String UNKNOWN_USER_ERROR = "Unknown user";
    final static String CONNECTION_LOST = "Connection lost" + CLOSING_ERROR;


    // GUI login
    final static int LOGIN_PENDING = 0;
    final static int LOGIN_SUCCESS = 1;
    final static int LOGIN_FAILURE = 2;
    final static int LOGIN_TIMEOUT = 3;
    final static int LOGIN_BLACKLISTED_USER = 4;
    final static int LOGIN_INVALID_PASSWORD = 5;

    // status
    final static int ONLINE = 0;
    final static int BUSY = 1;
    final static int AWAY = 2;
    final static int OFFLINE = 3;

    // id
    final static int NONE = -1;
    final static int USER_LOGIN = 0;
    final static int SEND_STATUS = 1;
    final static int SEE_ONLINE = 2;
    final static int SEND_MSG = 3;
    final static int RCV_MSG = 4;
    final static int RCV_STATUS = 5;
    final static int OK = 100;
    final static int BAD_REQUEST = 101;
    final static int INVALID_PASSWORD = 102;
    final static int BLACKLISTED_CLIENT = 103;
    final static int UNKNOWN_USER = 104;
}

/*  ==================================================================================================================*\
 *   Ben Mariem Sami - Derroitte Natan - Testouri Mehdi -     Project  - O.O.S.E
 *
 *    Protocol class
\*  ==================================================================================================================*/
import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Vector;

public class Protocol
{
    /**
     * Generate the byte array corresponding to the authentication request
     * @param login : login of the user
     * @param password : password
     * @return : byte array
     * @throws IOException : exception if the arguments are not valid or if the encoding fails
     */
    public static byte[] authentication(String login, String password) throws IOException
    {
        if(login.isEmpty() || password.isEmpty())
            throw new ProtocolException(Constants.INVALID_SIZE);

        int size = Constants.ID_LENGTH + login.length() + Constants.SEP_LENGTH + password.length();
        int id = Constants.USER_LOGIN;

        byte[] nicknameBytes = login.getBytes("UTF-8");
        byte[] stringBytes = password.getBytes("UTF-8");
        byte[] data = new byte[nicknameBytes.length + 1 + stringBytes.length];

        System.arraycopy(nicknameBytes,0, data, 0, nicknameBytes.length);
        data[nicknameBytes.length] = Constants.SEPARATION;
        System.arraycopy(stringBytes,0, data, nicknameBytes.length+1, stringBytes.length);

        return getBytes(size,id,data);
    }

    /**
     * Generate the byte array corresponding to the status update request
     * @param status : new status
     * @return : byte array
     * @throws ProtocolException : exception if the arguments are not valid
     */
    public static byte[] statusUpdate(int status) throws ProtocolException
    {
        int size = Constants.ID_LENGTH + 1;
        int id = Constants.SEND_STATUS;

        if(!checkStatus((byte)status))
            throw new ProtocolException(Constants.INVALID_STATUS);

        byte[] data = new byte[1];
        data[0] = (byte)status;

        return getBytes(size,id,data);
    }

    /**
     * Generate the byte array corresponding to the see people request
     * @return : byte array
     */
    public static byte[] seeConnected()
    {
        int size = Constants.ID_LENGTH;
        int id = Constants.SEE_ONLINE;

        return getBytes(size,id);
    }

    /**
     * Generate the byte array corresponding to the send message request
     * @param nickname : nickname of the receiver
     * @param msg : message to transmit
     * @return : byte array
     * @throws IOException : exception if the arguments are not valid or if the encoding fails
     */
    public static byte[] sendMsg(String nickname, String msg) throws IOException
    {
        if(nickname.isEmpty() || msg.isEmpty())
            throw new ProtocolException(Constants.INVALID_SIZE);

        int size = Constants.ID_LENGTH + nickname.length() + Constants.SEP_LENGTH + msg.length();
        int id = Constants.SEND_MSG;

        byte[] nicknameBytes = nickname.getBytes("UTF-8");
        byte[] stringBytes = msg.getBytes("UTF-8");
        byte[] data = new byte[nicknameBytes.length + 1 + stringBytes.length];

        System.arraycopy(nicknameBytes,0, data, 0, nicknameBytes.length);
        data[nicknameBytes.length] = Constants.SEPARATION;
        System.arraycopy(stringBytes,0, data, nicknameBytes.length+1, stringBytes.length);

        return getBytes(size,id,data);
    }

    /**
     * Generate the byte array corresponding to the response
     * @param id : id of the response
     * @return : byte array
     */
    public static byte[] reply(int id)
    {
        return getBytes(1,id);
    }

    /**
     * Retrieve the size of the message from a byte array
     * @param bytes : byte array to decode
     * @return : size
     * @throws ProtocolException : exception if the byte array or the size are not valid
     */
    private static int retrieveSize(byte[] bytes) throws ProtocolException
    {
        if(bytes.length<(Constants.SIZE_LENGTH + Constants.ID_LENGTH))
            throw new ProtocolException(Constants.INVALID_SIZE);

        byte byteSize[] = new byte[Constants.SIZE_LENGTH];
        System.arraycopy(bytes,Constants.INDEX_SIZE, byteSize, 0, Constants.SIZE_LENGTH);
        int size = ByteBuffer.wrap(byteSize).getInt();

        if(!checkSize(size,bytes.length-Constants.SIZE_LENGTH-Constants.ID_LENGTH))
            throw new ProtocolException(Constants.INVALID_SIZE);

        return size;
    }

    /**
     * Retrieve the size of the message from a byte array without checking it
     * @param bytes : byte array
     * @return : size
     * @throws ProtocolException : exception if the byte array is not valid
     */
    public static int retrieveSizeUnchecked(byte[] bytes) throws ProtocolException
    {
        if(bytes.length<(Constants.SIZE_LENGTH))
            throw new ProtocolException(Constants.INVALID_SIZE);

        byte byteSize[] = new byte[Constants.SIZE_LENGTH];
        System.arraycopy(bytes,Constants.INDEX_SIZE, byteSize, 0, Constants.SIZE_LENGTH);

        return ByteBuffer.wrap(byteSize).getInt();
    }

    /**
     * Retrieve the id of the message from a byte array
     * @param bytes : byte array
     * @return : id
     * @throws ProtocolException : exception if the byte array is not valid
     */
    public static int retrieveId(byte[] bytes) throws ProtocolException
    {
        if(bytes.length < Constants.INDEX_METHOD+1)
            throw new ProtocolException(Constants.INVALID_SIZE);

        int id = bytes[Constants.INDEX_METHOD];

        if(!checkId(id))
            throw new ProtocolException(Constants.INVALID_ID);

        return id;
    }

    /**
     * Retrieve the data part of the message from a byte array
     * @param bytes : byte array
     * @return : data part
     * @throws ProtocolException : exception if the byte array is not valid
     */
    private static byte[] retrieveData(byte[] bytes) throws ProtocolException
    {
        int size = retrieveSize(bytes);

        byte[] data = new byte[size-Constants.ID_LENGTH];
        System.arraycopy(bytes,Constants.INDEX_DATA, data, 0, size-Constants.ID_LENGTH);

        return data;
    }

    /**
     * Generate a message object containing the information from a message sent by a user from a byte array
     * @param bytes : byte array
     * @return : message object
     * @throws IOException : exception if the byte array is not valid
     */
    public static Message retrieveMsg(byte[] bytes) throws IOException
    {
        byte[] data = retrieveData(bytes);

        if(data.length < 12)
            throw new ProtocolException(Constants.INVALID_SIZE);

        // retrieve the status
        int status;
        if(data[0] == 0)
            status = Constants.ONLINE;
        else if(data[0] == 1)
            status = Constants.OFFLINE;
        else
            throw new ProtocolException(Constants.INVALID_STATUS);

        // retrieve the date
        byte[] bytesDate = new byte[Constants.DATE_LENGTH];
        for(int i=0; i<bytesDate.length; i++)
            bytesDate[i] = data[1+i];
        int date = new BigInteger(bytesDate).intValue();

        // retrieve the sender and the message
        byte[] byteString = new byte[data.length-9];
        System.arraycopy(data,9, byteString, 0, byteString.length);
        String string = new String(byteString,"UTF-8");

        byte[] separation = {Constants.SEPARATION};
        String regex = new String(separation,"UTF-8");
        String[] strings = string.split(regex);

        if(strings.length != 2)
            throw new ProtocolException(Constants.INVALID_DATA);

        // assign the data
        User sender = new User(strings[0],status);
        String msg = strings[1];

        return new Message(sender,date,msg);
    }

    /**
     * Generate a vector of users from a byte array
     * @param bytes : byte array
     * @return : vector of users
     * @throws IOException : exception if the byte array is not valid
     */
    public static Vector<User> retrieveStatus(byte[] bytes) throws IOException
    {
        Vector<User> status = new Vector<>();

        byte[] data = retrieveData(bytes);
        String string = new String(data, "UTF-8");

        byte[] separation = {Constants.SEPARATION};
        String regex = new String(separation,"UTF-8");
        String[] strings = string.split(regex);

        for(int i=0; i<strings.length; i++)
        {
            if(strings[i].length() < 2)
                throw new ProtocolException(Constants.INVALID_DATA);
            status.add(new User(strings[i].substring(1),strings[i].getBytes("UTF-8")[0]));
        }

        return status;
    }

    /**
     * Check the size of the size of the message
     * @param size : size
     * @param dataSize : size of the data part
     * @return : true if size is correct, false otherwise
     */
    private static boolean checkSize(int size, int dataSize)
    {
        if(size != Constants.ID_LENGTH + dataSize)
            return false;
        return true;
    }

    /**
     * Check the id of the message
     * @param id : id
     * @return : true if id is correct, false otherwise
     */
    private static boolean checkId(int id)
    {
        switch(id)
        {
            case Constants.USER_LOGIN:
                break;
            case Constants.SEND_STATUS:
                break;
            case Constants.SEE_ONLINE:
                break;
            case Constants.SEND_MSG:
                break;
            case Constants.RCV_MSG:
                break;
            case Constants.RCV_STATUS:
                break;
            case Constants.OK:
                break;
            case Constants.BAD_REQUEST:
                break;
            case Constants.INVALID_PASSWORD:
                break;
            case Constants.BLACKLISTED_CLIENT:
                break;
            case Constants.UNKNOWN_USER:
                break;
            default:
                return false;
        }
        return true;
    }

    /**
     * Check the status
     * @param status : status
     * @return : true if status is correct, false otherwise
     */
    private static boolean checkStatus(int status)
    {
        switch(status)
        {
            case Constants.ONLINE:
                break;
            case Constants.BUSY:
                break;
            case Constants.AWAY:
                break;
            case Constants.OFFLINE:
                break;
            default:
                return false;
        }
        return true;
    }

    /**
     * Generate the byte array corresponding to the provided size, id and data
     * @param size : size
     * @param id : id
     * @param data : data part
     * @return : byte array
     */
    private static byte[] getBytes(int size, int id, byte[] data)
    {
        byte bytes[] = new byte[Constants.SIZE_LENGTH + size];

        byte[] byteSize = ByteBuffer.allocate(Constants.SIZE_LENGTH).putInt(size).array();
        System.arraycopy(byteSize,0, bytes, Constants.INDEX_SIZE, Constants.SIZE_LENGTH);

        bytes[Constants.INDEX_METHOD] = (byte)id;

        System.arraycopy(data,0, bytes, Constants.INDEX_DATA, data.length);

        return bytes;
    }

    /**
     * Generate the byte array corresponding to the provided size and id
     * @param size : size
     * @param id : id
     * @return : byte array
     */
    private static byte[] getBytes(int size, int id)
    {
        byte bytes[] = new byte[Constants.SIZE_LENGTH + size];

        byte[] byteSize = ByteBuffer.allocate(Constants.SIZE_LENGTH).putInt(size).array();
        System.arraycopy(byteSize,0, bytes, Constants.INDEX_SIZE, Constants.SIZE_LENGTH);

        bytes[Constants.INDEX_METHOD] = (byte)id;

        return bytes;
    }
}

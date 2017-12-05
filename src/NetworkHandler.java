/*  ==================================================================================================================*\
 *   Ben Mariem Sami - Derroitte Natan - Testouri Mehdi -     Project  - O.O.S.E
 *
 *    NetworkHandler class
\*  ==================================================================================================================*/
import java.io.*;
import java.net.*;

public class NetworkHandler
{
    private OutputStream outToServer;
    private InputStream inFromServer;
    private Socket clientSocket;

    /**
     * NetworkHandler constructor
     */
    public NetworkHandler(){}

    /**
     * Connect the client socket to the server
     * @param address : address of the server
     * @param port : port to use
     * @param connectionTimeOut : connection timeout
     * @throws IOException : exception in case of connection failure
     */
    public void connect(String address, int port, int connectionTimeOut) throws IOException
    {
        clientSocket = new Socket();
        clientSocket.connect(new InetSocketAddress(address, port), connectionTimeOut);
        outToServer = clientSocket.getOutputStream();
        inFromServer = clientSocket.getInputStream();
    }

    /**
     * Set the socket timeout of the client socket
     * @param timeout : timeout value
     * @throws SocketException : exception if the setting fails
     */
    public void setSocketTimeout(int timeout) throws SocketException
    {
        clientSocket.setSoTimeout(timeout);
    }

    /**
     * Disconnect the client socket
     * @throws IOException : exception of disconnect failure
     */
    public void disconnect() throws IOException
    {
        clientSocket.close();
    }

    /**
     * Send a byte array to the server
     * @param bytes : byte array to send
     * @throws IOException : exception in case of sending failure
     */
    public void send(byte[] bytes) throws IOException
    {
        outToServer.write(bytes,0,bytes.length);
        outToServer.flush();
    }

    /**
     * Read one byte from the input stream
     * @return : the byte read
     * @throws IOException : exception in case of reading failure
     */
    public byte rcv() throws IOException
    {
        return (byte) inFromServer.read();
    }
}
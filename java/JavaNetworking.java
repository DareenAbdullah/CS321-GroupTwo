/** This class is responsible for connecting and communicating with
 * the main python class in the beaglebone.
 * @author Romel Munoz Valencia, Nina Vu
 */
import javax.swing.*;
import java.net.*;
import java.io.*;

public class JavaNetworking implements Runnable {
    /**
     * Local host IP address used for testing
     */
    public static final String LOCAL_HOST = "localhost";
    
    /**
     * readsMessages from beagleBone
     */
    private BufferedReader readMessage;

    /**
     * sends messages to beagleBone
     */
    private PrintWriter sendMessage;
    
    /**
     * Nina:
     * Socket initialize 
     */
    private Socket socket= null;

    /**
     * Default password to work with current python script
     * in python folder
     */
    private String defaultPassword = "none";

    /**
     * Default port numbert to work with current python script
     * in python folder
     */
    private int defaultPort = 12345;

    /**
     * Nina:
     * Constructor to intialize socket calling the create connection.
     * //Default to local host with default password to work with current python script in python folder
     */
    public JavaNetworking(){
        socket= createConnection(defaultPort, LOCAL_HOST, defaultPassword, defaultPassword);
    }

    /**
     * create connection with user specified port, host, client password and server password
     */
    public JavaNetworking(int portNumber, String hostName, String clientPass, String serverPass){
        socket = createConnection(portNumber, hostName, clientPass, serverPass);
    }

    /** This method listens in a specified port
     * if a connection can be made, it returns the socket through which
     * communication can be made.
     * 
     * @param portNumber user specified port number
     * 
     * @return socket through which communication is possible.
     */
    public Socket createConnection(int portNumber, String hostName, String clientPass, String serverPass) {
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(hostName, portNumber), 10000); // 10 seconds timeout

            this.readMessage = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.sendMessage = new PrintWriter(socket.getOutputStream(), true);

            //Sends string to beaglebone
            sendMessage.print(serverPass);
            sendMessage.flush();

            String check = null;
            check = readMessage.readLine();

            //Checks if beaglebone sends correct password
            if (check == null || !check.contains((clientPass))) {
                sendMessage.close();
                readMessage.close();
                socket.close();
                return null;
            } else {
                return socket;
            }
        } catch (IOException e) {
            //if a connection cannot be made.
            socket = null;
        }

        return socket;
    }

    /*
     * Nina: Removed the socket from param
     */

    /** This method sends commands to the beaglebone
     *
     * @param command either 'l', 'r', 'f', 'b', '0' , '1'
     * 
     * @return 0 if success
     */
    public int move(char command){
        int output = 0;
        if (socket == null){
            return -100;
        }
        try{
            String confirmed = null;
            
            switch (command) {
                case 'l':
                    sendMessage.print("0");
                    sendMessage.flush();
                    break;
                case 'r':
                    sendMessage.print("1");
                    sendMessage.flush();
                    break;
                case 'f':
                    sendMessage.print("2");
                    sendMessage.flush();
                    break;
                case 'b':
                    sendMessage.print("3");
                    sendMessage.flush();
                    break;
                case 'q':
                    sendMessage.print("q");
                    cleanUp();
                    return 0;
                default:
                    sendMessage.print("invalid");
                    sendMessage.flush();
                    break;
            }
            if (socket.isConnected()){
                /*confirmed = readMessage.readLine();
            
                if (confirmed == null){
                    output = -1;
                }*/
                output = 0;
            }
            else{
                output = -1;
            }
        }
        catch(Exception e){
            output = -1;
        }
        return output;
    }
    
    /** This method sends the command to start video streaming
     *
     * 
     * @return true if stream starts successfully
     */
    public boolean startStreaming(){
        if (this.socket == null){
            return false;
        }
        try{
            this.sendMessage.print("4");
            this.sendMessage.flush();

            String confirmed = null;
            confirmed = this.readMessage.readLine();

            return confirmed == null ? false : true;
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return false;
    }

    /** Stops video streaming
     *
     * @return true if successful
     */
    public boolean stopStreaming(){
        //If it's not streaming, simply return true
        try{
            System.out.println("stopping the stream");
            this.sendMessage.print("5");
            this.sendMessage.flush();
            String confirmed = null;
            confirmed = this.readMessage.readLine();
            
            return confirmed == null ? false : true;
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return true;    
    }

    /** This method closes the open socket and any other resource
     * @return 0 if success, -1 if any error
     */
    public int cleanUp(){
        if (this.socket != null && this.socket.isConnected()){
            try{
                stopStreaming();
                this.sendMessage.close();
                this.readMessage.close();
                this.socket.close();
            }
            catch(IOException e){
                e.printStackTrace();
                return -1;
            }
        }
        
        return 0;
    }

    /** This method returns the socket
     * @return true if socket is open and connected.
     */
    public boolean isConnected(){
        
        return this.socket == null ? false : this.socket.isConnected();
    }
    /** Main method for debugging
     *  //TODO: work on the param for start/stop streaming
     * // TODO DONE! - Romel
     * 
     * @param args
     */
    public static void main(String[] args){
        JavaNetworking j = new JavaNetworking();
        int i = 0;
        if (j.isConnected()){
            while(i <1000){
                if (i == 999){
                    j.move('q');
                    break;
                }
                System.out.println("success connecting!");
                System.out.println(j.move('l'));               
                System.out.println(j.move('r'));
                System.out.println(j.move('f'));
                System.out.println(j.move('b'));
                System.out.println(j.startStreaming()); 
                System.out.println(j.move('e'));
                if (!j.isConnected()){
                    break;
                }
                System.out.println(i);
                i++;
            }
        }
        else{
            System.out.println("Unsuccessful, socket is closed");
        }
    }

    // Add SharedMessage instance variable
    private SharedMessage sharedMessage;

    // Add a constructor that accepts a SharedMessage object
    public JavaNetworking(SharedMessage sharedMessage) {
        this.sharedMessage = sharedMessage;
        socket = createConnection(defaultPort, LOCAL_HOST, defaultPassword, defaultPassword);
    }

    // Modify the move() method to use the message from the shared object
    public int move() {
        // Get the message from the shared object
        String command = sharedMessage.getMessage();
        if (command == null || command.length() == 0) {
            return 0;
        }

        // Use the first character of the command as the input
        return move(command.charAt(0));
    }

    @Override
    public void run() {
        while (true) {
            int result = move();
            if (result == -1) {
                break;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        cleanUp();
    }
}

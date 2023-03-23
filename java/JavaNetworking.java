/** This class is responsible for connecting and communicating with
 * the main python class in the beaglebone.
 * @author Romel Munoz Valencia, Nina Vu
 */
import java.net.*;
import java.io.*;

public class JavaNetworking {
    /**
     * Local host IP address used for testing
     */
    public static final String LOCAL_HOST = "127.0.0.1";
    
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
     * @param password message recognized by our beaglebone server program
     * 
     * @return socket through which communication is possible.
     */
    public Socket createConnection(int portNumber, String hostName, String clientPass, String serverPass){
        /*
         * Nina: Created the socket on line 28
         */
        // Socket socket = null; 
        try{
            socket = new Socket(hostName, portNumber);
            this.readMessage = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.sendMessage = new PrintWriter(socket.getOutputStream(), true);
            
            //Sends string to beaglebone
            sendMessage.print(serverPass);
            sendMessage.flush();

            String check = null;
            check = readMessage.readLine();

            //Checks if beaglebone sends correct password
            if (check == null || !check.equals((clientPass))){
                sendMessage.close();
                readMessage.close();
                socket.close();
                return null;
            }
            else{
                return socket;
            }
        }
        catch(IOException e){
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
     * @param socket //Removed from param for easier use in the GUI
     * @param command either 'l', 'r', 'f', 'b', '0' , '1'
     * 
     * @return 0 if success
     */
    public int move(char command){
        int output = 0;
        if (socket == null){
            return -1;
        }
        try{
            String confirmed = null;
            
            switch (command) {
                case 'l':
                    System.out.println("Moving left");
                    sendMessage.print("0");
                    sendMessage.flush();
                    break;
                case 'r':
                    System.out.println("Moving right");
                    sendMessage.print("1");
                    sendMessage.flush();
                    break;
                case 'f':
                    System.out.println("Moving forward");
                    sendMessage.print("2");
                    sendMessage.flush();
                    break;
                case 'b':
                    System.out.println("Moving in reverse");
                    sendMessage.print("3");
                    sendMessage.flush();
                    break;
                case 'q':
                    System.out.println("Exiting all processes");
                    sendMessage.print("q");
                    cleanUp();
                    return 0;
                default:
                    System.out.println(command);
                    sendMessage.print("invalid");
                    sendMessage.flush();
                    break;
            }
            if (socket.isConnected()){
                confirmed = readMessage.readLine();
            
                if (confirmed == null){
                    output = -1;
                }
            }
            else{
                output = -1;
            }
        }
        catch(IOException e){
            output = -1;
        }
        return output;
    }
    
    /** This method sends the command to start video streaming
     * 
     * @param socket
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
            if (confirmed == null || !confirmed.equals("Streaming")){
                return false;
            }
            else{
                return true;
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return false;
    }

    /** Stops video streaming
     *  
     * @param socket
     * @return true if successful
     */
    public boolean stopStreaming(){
        //If it's not streaming, simply return true
        boolean flag = false;
        if (!startStreaming()){
            return true;
        }
        else{
            try{
                this.sendMessage.print("5");
                this.sendMessage.flush();

                String confirmed = null;
                confirmed = this.readMessage.readLine();
                if (confirmed == null || !confirmed.equals("Not streaming")){
                    flag = false;
                }
                else{
                    flag = true;
                }
            }
            catch(IOException e){
                e.printStackTrace();
                flag = false;
            }
        }
        return flag;
    }

    /** This method closes the open socket and any other resource
     * @param socket
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
            while(i <10000){
                if (i == 9999){
                    j.move('q');
                }
                System.out.println("success connecting!");
                System.out.println(j.move('l'));               
                System.out.println(j.move('r'));
                System.out.println(j.move('f'));
                System.out.println(j.move('b'));
                System.out.println(j.startStreaming()); 
                System.out.println(j.stopStreaming());
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
}

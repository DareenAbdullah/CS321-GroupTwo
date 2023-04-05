import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SharedMessage sharedMessage = new SharedMessage();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Controller_keys controllerKeys = new Controller_keys(sharedMessage);
                controllerKeys.setFrame();
            }
        });


        JavaNetworking javaNetworking = new JavaNetworking(sharedMessage);

        Thread javaNetworkingThread = new Thread(javaNetworking);
        javaNetworkingThread.start();

        try {
            javaNetworkingThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

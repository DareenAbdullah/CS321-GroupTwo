public class Main {
    public static void main(String[] args) {
        SharedMessage sharedMessage = new SharedMessage();
        JavaNetworking javaNetworking = new JavaNetworking(sharedMessage);
        Controller_keys controllerKeys = new Controller_keys(sharedMessage);

        Thread javaNetworkingThread = new Thread(javaNetworking);
        Thread controllerKeysThread = new Thread(controllerKeys);

        javaNetworkingThread.start();
        controllerKeysThread.start();

        try {
            javaNetworkingThread.join();
            controllerKeysThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

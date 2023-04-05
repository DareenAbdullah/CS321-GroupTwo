import java.util.concurrent.atomic.AtomicReference;

public class SharedMessage {
    private final AtomicReference<String> message;

    public SharedMessage() {
        message = new AtomicReference<>();
    }

    public String getMessage() {
        return message.get();
    }

    public void setMessage(String newMessage) {
        message.set(newMessage);
    }
}

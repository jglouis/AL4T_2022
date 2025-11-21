package be.ecam.spy;

public class NotificationService {
    private final MessageSender sender;

    public NotificationService(MessageSender sender) {
        this.sender = sender;
    }

    public void notifyUser(String user) {
        String message = "Hello " + user;
        sender.send(message);
    }
}

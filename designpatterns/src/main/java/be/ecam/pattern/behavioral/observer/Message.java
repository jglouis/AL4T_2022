package be.ecam.pattern.behavioral.observer;

/**
 * An immutable message.
 */
public class Message {
    private final String author;
    private final String message;

    public Message(String author, String message) {
        this.author = author;
        this.message = message;
    }

    public String getAuthor() {
        return author;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return String.format("%s: %s", author, message);
    }
}

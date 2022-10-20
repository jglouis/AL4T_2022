package be.ecam.pattern.behavioral.observer;

public class Main {
    public static void main(String[] args) {
        Chat chat = new Chat();
        Chat.Listener listener = newMessage -> System.out.println("saw a new message -> " + newMessage);
        chat.registerListener(listener);

        chat.post(new Message("alice", "hi"));
        chat.post(new Message("bob", "hi"));
        chat.post(new Message("alice", "all good?"));
        chat.post(new Message("bob", "yes"));
    }
}

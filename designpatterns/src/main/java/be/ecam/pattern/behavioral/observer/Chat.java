package be.ecam.pattern.behavioral.observer;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

public class Chat {
    private final List<Message> messages = new ArrayList<>();
    private final WeakHashMap<Listener, Void> listeners = new WeakHashMap<>();

    /**
     * Post a message to the chat.
     *
     * @param message the {@link Message} to post
     */
    public void post(Message message) {
        messages.add(message);
        for (Listener listener : listeners.keySet()) {
            listener.onNewMessage(message);
        }
    }

    /**
     * Register a chat listener for listening to new messages.
     *
     * @param listener the {@link Listener} to register
     */
    public void registerListener(Listener listener) {
        listeners.put(listener, null);
    }

    interface Listener {
        void onNewMessage(Message newMessage);
    }
}

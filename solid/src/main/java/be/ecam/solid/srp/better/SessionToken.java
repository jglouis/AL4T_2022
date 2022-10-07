package be.ecam.solid.srp.better;

import java.util.Random;

public class SessionToken {
    private final User user;
    private final int token;

    public SessionToken(User user) {
        this.user = user;
        this.token = new Random().nextInt();
    }

    public User getUser() {
        return user;
    }

    public int getToken() {
        return token;
    }
}

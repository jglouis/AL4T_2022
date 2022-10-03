package be.ecam.solid.srp.better;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AuthenticationService {
    private final Map<String, User> userByLogin = new HashMap<>(); //would be a database in a real environment;
    private final Set<SessionToken> validSessionTokens = new HashSet<>();

    private SessionToken AuthenticateUser(String login, String password) throws IncorrectCredentialsException, UnknownUserException {
        //Check if user exists in map and password is correct
        User user = userByLogin.get(login);
        if (user == null) {
            // Signal user was not found
            throw new UnknownUserException(login);
        } else {
            if (password != null && password.equals(user.getPassword())) {
                // Access is granted, return a token
                return new SessionToken(user);
            }
            // Signal incorrect password
            throw new IncorrectCredentialsException();
        }
    }

    public static class UnknownUserException extends Exception {
        public UnknownUserException(String login) {
            super(String.format("Login %s is unknown", login));
        }
    }

    public static class IncorrectCredentialsException extends Exception {
        public IncorrectCredentialsException() {
            super("Given credentials were incorrect");
        }
    }
}

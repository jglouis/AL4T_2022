package be.ecam.basics.exercises;

import java.util.List;

public class ImmutableProfile {
    private final String username;
    private final List<String> roles;

    public ImmutableProfile(String username, List<String> roles) {
        this.username = username;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public List<String> getRoles() {
        return roles;
    }
}

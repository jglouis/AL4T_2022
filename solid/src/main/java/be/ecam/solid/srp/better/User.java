package be.ecam.solid.srp.better;

import java.util.List;

public class User {
    private final List<Account> ownedAccounts;
    private final String password;

    public User(List<Account> ownedAccounts, String password) {
        this.ownedAccounts = ownedAccounts;
        this.password = password;
    }

    public List<Account> getOwnedAccounts() {
        return ownedAccounts;
    }

    public String getPassword() {
        return password;
    }
}

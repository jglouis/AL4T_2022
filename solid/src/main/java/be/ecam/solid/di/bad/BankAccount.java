package be.ecam.solid.di.bad;

import be.ecam.solid.srp.better.SessionToken;

public class BankAccount {
    public void transferMoneyTo(SessionToken token,
                                BankAccount receivingParty,
                                int amount) {
        //Validate token
        if (AuthenticationService.getInstance().validateToken()) {
            // transfer money
        }
    }
}

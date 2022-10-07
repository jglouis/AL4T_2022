package be.ecam.solid.di.better;


import be.ecam.solid.srp.better.SessionToken;

public class BankAccount {

    private final AuthenticationService authenticationService;

    public BankAccount(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    public void transferMoneyTo(SessionToken token,
                                BankAccount receivingParty,
                                int amount) {
        //Validate token
        if (authenticationService.validateToken()) {
            // transfer money
        }
    }

}

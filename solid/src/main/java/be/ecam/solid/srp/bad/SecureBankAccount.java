package be.ecam.solid.srp.bad;

public class SecureBankAccount {
    private String Iban;
    private float balance;
    private String password;

    public void transferMoneyTo(String password, SecureBankAccount receivingParty, float amount) {
        //Validate password

        //Proceed to transfer
    }
}

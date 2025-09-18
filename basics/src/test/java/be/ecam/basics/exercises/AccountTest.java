package be.ecam.basics.exercises;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
    void depositAndWithdrawShouldBeExactInCents() {
        Account a = new Account();
        a.deposit(0.10);
        a.deposit(0.20);
        assertEquals(0.30, a.getBalance(), 0.0, "Money operations should be exact in cents");
        a.withdraw(0.10);
        assertEquals(0.20, a.getBalance(), 0.0);
    }

    @Test
    void transferShouldPreserveTotal() {
        Account a = new Account(1.00);
        Account b = new Account(0.00);
        a.transferTo(b, 0.10);
        assertEquals(0.90, a.getBalance(), 0.0);
        assertEquals(0.10, b.getBalance(), 0.0);
        assertEquals(1.00, a.getBalance() + b.getBalance(), 0.0);
    }
}

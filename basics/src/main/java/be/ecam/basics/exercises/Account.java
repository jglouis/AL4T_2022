package be.ecam.basics.exercises;

import java.util.Objects;

public class Account {
    private double balance;

    public Account() {
        this(0.0);
    }

    public Account(double initial) {
        this.balance = initial;
    }

    public double getBalance() {
        // return balance;
        return Math.round(balance * 100.0) / 100.0; //added - rounding to 1e-2
    }

    public void deposit(double amount) {
        if (amount < 0) throw new IllegalArgumentException("amount");
        balance += amount;
        balance = Math.round(balance * 100.0) / 100.0; //added - rounding to 1e-2
    }

    public void withdraw(double amount) {
        if (amount < 0) throw new IllegalArgumentException("amount");
        if (amount > balance) throw new IllegalStateException("insufficient");
        balance -= amount;
        balance = Math.round(balance * 100.0) / 100.0; //added - rounding to 1e-2
    }

    public void transferTo(Account other, double amount) {
        Objects.requireNonNull(other, "other");
        withdraw(amount);
        other.deposit(amount);
    }
}

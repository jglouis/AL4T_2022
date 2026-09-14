package be.ecam.basics.exercises;

public class DiscountService {
    public static class Customer {
        public double discountRate = 0.0;

        public double applyDiscount(double amount) {
            return amount * (1.0 - discountRate);
        }
    }

    public static class VipCustomer extends Customer {
        public double discountRate = 0.20;
    }

    public static double computeTotal(Customer customer, double amount) {
        return customer.applyDiscount(amount);
    }
}

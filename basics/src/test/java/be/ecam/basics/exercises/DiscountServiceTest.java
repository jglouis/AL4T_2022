package be.ecam.basics.exercises;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DiscountServiceTest {

    @Test
    void regularCustomerGetsNoDiscount() {
        DiscountService.Customer regular = new DiscountService.Customer();
        assertEquals(100.0, DiscountService.computeTotal(regular, 100.0), 0.001);
    }

    @Test
    void vipCustomerGetsDiscount() {
        DiscountService.Customer vip = new DiscountService.VipCustomer();
        assertEquals(80.0, DiscountService.computeTotal(vip, 100.0), 0.001);
    }
}

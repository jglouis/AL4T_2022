package be.ecam;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    private Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

    @Test
    void add() {
        int result = calculator.add(1, 2);
        assertEquals(3, result);
    }

    @Test
    void add_overflow() {
        int result = calculator.add(Integer.MAX_VALUE, 1);
        assertEquals(Integer.MIN_VALUE, result);
    }

    @Test
    void subtract() {
        int result = calculator.subtract(1, 2);
        assertEquals(-1, result);
    }

    @Test
    void multiply() {
        int result = calculator.multiply(3, 4);
        assertEquals(12, result);
    }

    @Test
    void divide() {
        int result = calculator.divide(15, 3);
        assertEquals(5, result);
    }

    @Test
    void divide_by_zero() {
        assertThrows(ArithmeticException.class, () -> calculator.divide(15, 0));
    }

}
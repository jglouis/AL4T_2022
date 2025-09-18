package be.ecam.basics.exercises;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CounterTest {

    @Test
    void countSmall() {
        assertEquals(5, Counter.count(0, 5));
    }

    @Test
    void countShouldNotOverflow() {
        assertEquals(300, Counter.count(0, 300));
    }
}

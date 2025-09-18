package be.ecam.basics.exercises;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SensorWindowTest {

    @Test
    void averageSimple() {
        assertEquals(2, SensorWindow.average(new int[]{1,2,3}));
    }

    @Test
    void averageVeryLargeShouldNotOverflow() {
        int avg = SensorWindow.average(new int[]{Integer.MAX_VALUE, Integer.MAX_VALUE});
        assertEquals(Integer.MAX_VALUE, avg);
    }
}

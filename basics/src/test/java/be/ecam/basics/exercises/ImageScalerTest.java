package be.ecam.basics.exercises;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ImageScalerTest {

    @Test
    void scaleThirdShouldRoundReasonably() {
        int[] out = ImageScaler.scale(5, 5, 1, 3);
        assertArrayEquals(new int[]{2, 2}, out);
    }

    @Test
    void scaleHalfShouldHandleOddSizes() {
        int[] out = ImageScaler.scale(5, 4, 1, 2);
        assertArrayEquals(new int[]{3, 2}, out);
    }
}

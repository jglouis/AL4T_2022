package be.ecam.basics.samples;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class SamplesTest {

    @Test
    void castingByteOverflow() {
        assertEquals(-128, Samples.castByte128());
    }

    @Test
    void integerDivisionTruncates() {
        assertEquals(0, Samples.intDivision(1, 2));
        assertEquals(2, Samples.intDivision(5, 2));
    }

    @Test
    void floatingPointPrecision() {
        double sum = Samples.sumDoublePointOneAndTwo();
        assertNotEquals(0.3, sum, 0.0, "Binary floating point cannot represent 0.1 exactly");
        // But it's close to 0.3
        assertEquals(0.3, sum, 1e-9);
    }

    @Test
    void integerOverflowWraps() {
        assertEquals(Integer.MIN_VALUE, Samples.overflowAdd(Integer.MAX_VALUE, 1));
        assertEquals(-2_147_483_646, Samples.overflowAdd(Integer.MAX_VALUE, 3));
    }

    @Test
    void nanIsNotEqualToItself() {
        assertFalse(Samples.isNaNEqualToItself());
    }

    @Test
    void nullDereferenceThrowsNPE() {
        assertThrows(NullPointerException.class, Samples::dereferenceNull);
    }

    @Test
    void checkedExceptionMustBeHandled() {
        assertThrows(IOException.class, Samples::throwChecked);
        // Alternatively, we could declare `throws IOException` on the test and call directly.
    }

    @Test
    void referenceAliasingMutation() {
        int[] data = {41};
        Samples.mutateFirstElement(data);
        assertArrayEquals(new int[]{42}, data);
    }

    @Test
    void stringEqualityPitfall() {
        assertFalse(Samples.stringReferenceEqualityForEqualContents());
    }

    @Test
    void autoUnboxingNullThrowsNPE() {
        assertThrows(NullPointerException.class, Samples::unboxNull);
    }
}

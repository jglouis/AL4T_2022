package be.ecam;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MathTest {

    @ParameterizedTest
    @CsvFileSource(resources = "/addition.csv")
    void testAddition(int a, int b, int expected) {
        assertEquals(expected, a + b);
    }
}

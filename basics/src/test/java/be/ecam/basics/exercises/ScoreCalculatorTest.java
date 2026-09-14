package be.ecam.basics.exercises;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ScoreCalculatorTest {

    @Test
    void sumScoresWithNullsTreatsAsZero() {
        assertEquals(15, ScoreCalculator.sumScores(Arrays.asList(10, null, 5)));
    }

    @Test
    void sumScoresRegular() {
        assertEquals(20, ScoreCalculator.sumScores(Arrays.asList(10, 5, 5)));
    }

    @Test
    void areEqualScoresOutsideCache() {
        assertTrue(ScoreCalculator.areEqualScores(Integer.valueOf(1000), Integer.valueOf(1000)));
    }

    @Test
    void areEqualScoresDifferent() {
        assertFalse(ScoreCalculator.areEqualScores(10, 20));
    }
}

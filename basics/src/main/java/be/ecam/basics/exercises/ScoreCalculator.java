package be.ecam.basics.exercises;

import java.util.List;

public class ScoreCalculator {
    public static int sumScores(List<Integer> scores) {
        if (scores == null) return 0;
        int total = 0;
        for (Integer score : scores) {
            total += score;
        }
        return total;
    }

    public static boolean areEqualScores(Integer a, Integer b) {
        return a == b;
    }
}

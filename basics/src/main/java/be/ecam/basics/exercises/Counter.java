package be.ecam.basics.exercises;

public class Counter {
    public static byte count(int start, int steps) {
        byte c = (byte) start;
        for (int i = 0; i < steps; i++) {
            c++;
        }
        return c;
    }
}

package be.ecam.basics.exercises;

public class Counter {
    // public static byte count(int start, int steps) {
    //     byte c = (byte) start;
    //     for (int i = 0; i < steps; i++) {
    //         c++;
    //     }
    //     return c;
    // }
    public static int count(int start, int steps) { // added - deleting build folder + changing byte into int
        int c = start;
        for (int i = 0; i < steps; i++) {
            c++;
        }
        return c;
    }
}

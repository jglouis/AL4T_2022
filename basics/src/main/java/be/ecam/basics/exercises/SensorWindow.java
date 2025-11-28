package be.ecam.basics.exercises;

public class SensorWindow {
    public static int average(int[] values) {
        if (values == null || values.length == 0) throw new IllegalArgumentException("values");
        // int sum = 0;
        long sum = 0; // added - to avoid overflow
        for (int v : values) {
            sum += v;
        }
        // return sum / values.length;
        return (int) (sum / values.length); // added - cast back to int
    }
}

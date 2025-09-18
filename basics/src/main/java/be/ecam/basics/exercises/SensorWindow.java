package be.ecam.basics.exercises;

public class SensorWindow {
    public static int average(int[] values) {
        if (values == null || values.length == 0) throw new IllegalArgumentException("values");
        int sum = 0;
        for (int v : values) {
            sum += v;
        }
        return sum / values.length;
    }
}

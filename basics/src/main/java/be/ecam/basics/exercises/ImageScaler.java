package be.ecam.basics.exercises;

public class ImageScaler {
    public static int[] scale(int width, int height, int num, int den) {
        if (width < 0 || height < 0) throw new IllegalArgumentException();
        if (den == 0) throw new IllegalArgumentException();
        // int w = width * num / den;
        // int h = height * num / den;
        int w = (int) Math.round((double) width  * num / den); // added - casting to double to avoid integer division
        int h = (int) Math.round((double) height * num / den);
        // long w = (long) ((long) width  * num / den); // Correction ?
        // long h = (long) ((long) height * num / den);
        if (w < 0) w = 0;
        if (h < 0) h = 0;
        return new int[]{w, h};
    }
}

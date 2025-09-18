package be.ecam.basics.samples;

import java.io.IOException;

/**
 * A collection of tiny, focused samples that showcase common Java pitfalls.
 * Each static method either returns a value that demonstrates a behavior or throws an exception.
 */
public final class Samples {
    private Samples() { }

    // 1) Casting overflow: (byte)128 wraps to -128
    public static byte castByte128() {
        return (byte) 128; // wraps because byte is signed 8-bit (-128..127)
    }

    // 2) Integer division truncation: 1/2 == 0 (for ints)
    public static int intDivision(int a, int b) {
        return a / b; // truncates toward zero for integers
    }

    // 3) Floating point precision: 0.1 + 0.2 is not exactly 0.3 in binary floating point
    public static double sumDoublePointOneAndTwo() {
        return 0.1 + 0.2;
    }

    // 4) Integer overflow wraps around using two's complement
    public static int overflowAdd(int a, int b) {
        return a + b; // undefined behavior in some languages, but defined wraparound in Java for primitives
    }

    // 5) NaN comparison peculiarity: NaN is not equal to itself
    public static boolean isNaNEqualToItself() {
        return Double.NaN == Double.NaN;
    }

    // 6) Null dereference leads to NullPointerException (unchecked)
    public static void dereferenceNull() {
        String s = null;
        // This will throw NullPointerException
        //noinspection ConstantValue
        s.length();
    }

    // 7) Checked exception must be declared or caught
    public static void throwChecked() throws IOException {
        throw new IOException("boom");
    }

    // 8) Reference aliasing: mutating inside a method affects the original object
    public static void mutateFirstElement(int[] arr) {
        if (arr == null || arr.length == 0) return;
        arr[0]++;
    }

    // 9) String equality pitfall: == compares references, not contents
    public static boolean stringReferenceEqualityForEqualContents() {
        return new String("x") == new String("x"); // almost always false
    }

    // 10) AutoUnboxing null throws NullPointerException
    public static int unboxNull() {
        Integer x = null;
        // This will throw NullPointerException during unboxing
        return x;
    }
}

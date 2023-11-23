package be.ecam.pattern.concurrency;

public class ImproperSynchronization {
    private static int sharedValue = 0;

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 1000000; i++) {
                sharedValue++; // This operation is not atomic
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 1000000; i++) {
                sharedValue--; // This operation is not atomic
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Final sharedValue: " + sharedValue);
    }
}
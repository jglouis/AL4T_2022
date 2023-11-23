package be.ecam.pattern.concurrency;

public class ThreadJoinExample {
    private static int result = 0;

    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            result = 42;
        });

        thread.start();

        try {
            thread.join(); // Main thread may not see the update without proper synchronization (try remove the join)
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Result: " + result);
    }
}
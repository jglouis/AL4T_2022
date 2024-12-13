package be.ecam.pattern.concurrency;

public class Deadlock {
    public static void main(String[] args) {
        final String resource1 = "resource1";
        final String resource2 = "resource2";
        // t1 tries to lock resource1 then resource2
        Thread t1 = new Thread(() -> {
            synchronized (resource1) { // Lock resource1
                System.out.println("Thread 1: locked resource 1");

                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignored) {
                }

                synchronized (resource2) { // Lock resource2
                    System.out.println("Thread 1: locked resource 2");
                } // Unlock resource2
            } // Unlock resource1
        });

        // t2 tries to lock resource2 then resource1
        Thread t2 = new Thread(() -> {
            synchronized (resource2) { // Lock resource2
                System.out.println("Thread 2: locked resource 2");

                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignored) {
                }

                synchronized (resource1) { // Lock resource1
                    System.out.println("Thread 2: locked resource 1");
                } // Unlock resource1
            } // Unlock resource2
        });


        t1.start();
        t2.start();
    }
}
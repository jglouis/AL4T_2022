package be.ecam.pattern.concurrency.bugs;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Starvation: a thread is ready to run but has to wait for an unbounded amount
 * of time, because other threads keep grabbing the shared resource first.
 * It is not blocked forever like in a deadlock: it only runs when the others
 * happen to leave it a chance (or stop competing).
 * <p>
 * Story: a greedy thread uses the printer in a loop. As soon as it releases the
 * lock, it takes it again. A second thread only wants to print once, but with
 * an unfair lock the greedy thread almost always wins the race: the victim
 * typically has to wait until the greedy thread has printed all its pages.
 */
public class Starvation {

    // Unfair lock (the default, same behaviour as 'synchronized').
    // Try with 'true' (fair lock): the longest waiting thread gets the lock next.
    private static final ReentrantLock printer = new ReentrantLock(false);

    public static void main(String[] args) throws InterruptedException {
        Thread greedy = new Thread(() -> {
            // Limited to 20 pages so the program ends.
            // With an endless loop (e.g. a server), the victim could wait forever.
            for (int i = 1; i <= 20; i++) {
                printer.lock();
                try {
                    System.out.println("Greedy: printing page " + i);
                    sleep(100); // printing takes time
                } finally {
                    printer.unlock();
                }
                // No pause here: the greedy thread immediately asks for the printer again
            }
        });

        Thread victim = new Thread(() -> {
            long start = System.currentTimeMillis();
            printer.lock();
            try {
                long waited = System.currentTimeMillis() - start;
                System.out.println("Victim: FINALLY printing my page after " + waited + " ms!");
            } finally {
                printer.unlock();
            }
        });

        greedy.start();
        sleep(50); // make sure the greedy thread owns the printer first
        victim.start();

        greedy.join();
        victim.join();
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
        }
    }
}

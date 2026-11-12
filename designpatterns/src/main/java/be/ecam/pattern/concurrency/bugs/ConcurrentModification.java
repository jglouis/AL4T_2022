package be.ecam.pattern.concurrency.bugs;

import java.util.ArrayList;
import java.util.List;

/**
 * Concurrent modification: one thread iterates over a list while another
 * thread modifies it.
 * <p>
 * ArrayList is not thread-safe. Its iterator detects that the list changed
 * behind its back and throws a ConcurrentModificationException ("fail-fast").
 * Note: this detection is only best-effort, in other cases the iteration may
 * silently skip or repeat elements instead.
 */
public class ConcurrentModification {

    // Not thread-safe!
    // Try with: new java.util.concurrent.CopyOnWriteArrayList<>()
    // (the iterator then works on a snapshot of the list and never throws)
    private static final List<String> names = new ArrayList<>();

    public static void main(String[] args) {
        names.add("Alice");
        names.add("Bob");
        names.add("Charlie");

        // Reader: displays all the names, slowly
        Thread reader = new Thread(() -> {
            for (String name : names) { // uses an Iterator behind the scenes
                System.out.println("Reader: " + name);
                sleep(100);
            }
            System.out.println("Reader: done");
        });

        // Writer: adds a name while the reader is still iterating
        Thread writer = new Thread(() -> {
            sleep(150);
            names.add("Dave");
            System.out.println("Writer: added Dave");
        });

        reader.start();
        writer.start();
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
        }
    }
}

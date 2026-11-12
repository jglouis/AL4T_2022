package be.ecam.pattern.concurrency.bugs;

/**
 * Livelock: threads are NOT blocked (unlike a deadlock), they keep running and
 * reacting to each other... but none of them ever makes progress.
 * <p>
 * Story: two very polite people share a single spoon. Before eating, each one
 * checks whether the other is still hungry. If so, they hand the spoon over
 * ("after you!"). Since both are hungry, the spoon goes back and forth forever
 * and nobody ever eats.
 */
public class Livelock {

    static class Spoon {
        volatile Person owner;
    }

    static class Person {
        private final String name;
        volatile boolean hungry = true;

        Person(String name) {
            this.name = name;
        }

        void eatWith(Spoon spoon, Person partner) {
            while (hungry) {
                // Not my turn: wait a bit and check again
                if (spoon.owner != this) {
                    sleep(10);
                    continue;
                }

                // I have the spoon, but I'm polite: if my partner is hungry, I give it away
                if (partner.hungry) {
                    System.out.println(name + ": you eat first, " + partner.name + "!");
                    spoon.owner = partner;
                    sleep(500); // slow down so we can read the output
                    continue;
                }

                // Partner is not hungry anymore: I can finally eat
                System.out.println(name + ": I'm eating!");
                hungry = false;
                spoon.owner = partner;
            }
        }
    }

    public static void main(String[] args) {
        Person alice = new Person("Alice");
        Person bob = new Person("Bob");
        Spoon spoon = new Spoon();
        spoon.owner = alice;

        new Thread(() -> alice.eatWith(spoon, bob)).start();
        new Thread(() -> bob.eatWith(spoon, alice)).start();

        // Both threads are RUNNING (not BLOCKED), yet nobody ever eats.
        // Possible fix: break the symmetry, e.g. only be polite once,
        // or wait a random amount of time before giving the spoon away.
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
        }
    }
}

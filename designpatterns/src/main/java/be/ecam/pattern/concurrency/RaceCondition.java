package be.ecam.pattern.concurrency;

import java.util.ArrayList;
import java.util.List;

public class RaceCondition {
    // Critical section (shared memory accessed by different threads)
    private final static List<Integer> list = new ArrayList<>();

    private static final Object MONITOR = new Object();

    static {
        for (int i = 0; i < 100; i++) {
            list.add(i);
        }
    }

    public static void main(String[] args) {
        getConsumerThread().start();
        getConsumerThread().start();
    }

    static Thread getConsumerThread() {
        return new Thread(() -> {
//            synchronized (MONITOR) { // -> protect access to critical section
                while (!list.isEmpty()) {
                    list.remove(0);
                }
//            }
        });
    }
}

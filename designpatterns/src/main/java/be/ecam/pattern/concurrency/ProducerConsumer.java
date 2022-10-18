package be.ecam.pattern.concurrency;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ProducerConsumer {
    private static final BlockingQueue<Integer> buffer = new ArrayBlockingQueue<>(10);
//    private static final Object MONITOR = new Object();

    private static final Thread producer = new Thread(() -> {
        for (int i = 0; i < 10; i++) {
            try {
                buffer.put(i);
//                synchronized (MONITOR) {
                    System.out.println("producing " + i);
//                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    });

    private static final Thread consumer = new Thread(() -> {
        while (true) {
            try {
                int take = buffer.take();
//                synchronized (MONITOR) {
                    System.out.println("consuming " + take);
//                }
                if (take == 9) {
                    break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    });

    public static void main(String[] args) {
        producer.start();
        consumer.start();
    }
}
package be.ecam.pattern.concurrency.patterns;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceExample {

    public static void main(String[] args) {
        // 1. Create a fixed-size thread pool with 3 worker threads (similar to ThreadPool(3))
        try (ExecutorService executor = Executors.newFixedThreadPool(3)) {

            System.out.println("=== 1. Submitting Runnable tasks (fire-and-forget) ===");
            for (int i = 0; i < 5; i++) {
                int taskId = i;
                executor.execute(() -> {
                    System.out.println(Thread.currentThread().getName() + " executing Runnable task " + taskId);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ignored) {
                    }
                });
            }

            System.out.println("\n=== 2. Submitting Callable tasks (returning Future results) ===");
            List<Future<String>> futures = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                int taskId = i;
                Callable<String> task = () -> {
                    System.out.println(Thread.currentThread().getName() + " executing Callable task " + taskId);
                    Thread.sleep(500);
                    return "Result from task " + taskId + " (processed by " + Thread.currentThread().getName() + ")";
                };
                futures.add(executor.submit(task));
            }

            // Retrieve the results from Futures (blocking get call)
            for (Future<String> future : futures) {
                try {
                    String result = future.get();
                    System.out.println("Received: " + result);
                } catch (InterruptedException | ExecutionException e) {
                    System.err.println("Task failed: " + e.getMessage());
                }
            }

            // 3. Graceful shutdown of ExecutorService
            System.out.println("\n=== 3. Shutting down ExecutorService ===");
            executor.shutdown(); // Reject new tasks, allow queued/running tasks to finish
            try {
                // Wait up to 5 seconds for existing tasks to terminate
                if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                    executor.shutdownNow(); // Cancel currently executing tasks if timeout exceeded
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
            }
        }

        System.out.println("ExecutorService stopped.");
    }
}

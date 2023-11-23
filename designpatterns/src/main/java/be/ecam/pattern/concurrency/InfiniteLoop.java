package be.ecam.pattern.concurrency;

public class InfiniteLoop {
    private volatile boolean flag = false; // try to delete it

    public void setFlag() {
        flag = true;
    }

    public void doSomething() {
        while (!flag) {
            // Do some work
        }
        System.out.println("Flag is now true.");
    }

    public static void main(String[] args) {
        InfiniteLoop example = new InfiniteLoop();

        new Thread(() -> {
            example.doSomething();
        }).start();

        // Main thread sets the flag after a delay
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        example.setFlag(); // This write to 'flag' establishes a happens-before relationship

        // Without volatile, the loop in doSomething() might not see the updated value of 'flag'
    }
}

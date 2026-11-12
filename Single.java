public class Single {
    public static void main(String[] args) throws InterruptedException {
        long startTime = System.nanoTime();

        Thread[] threads = new Thread[1000];
        long[] totals = new long[1000];
        for (int i = 0; i < threads.length; i++) {
            final int index = i;
            threads[i] = new Thread(() -> {
                long total = 0;
                for (int number = 1; number <= 1_000_000; number++) {
                    total++;
                }
                totals[index] = total;
            });
        }
        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }
        long grandTotal = 0;
        for (long total : totals) {
            grandTotal += total;
        }
        double seconds = (System.nanoTime() - startTime) / 1_000_000_000.0;
        System.out.println("Total: " + grandTotal);
        System.out.println("Time: " + seconds + " seconds");
    }
}
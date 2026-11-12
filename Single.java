import java.util.ArrayList;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws Exception {
        long singleStartTime = System.nanoTime();

        long singleCount = 0;
        for (long i = 0; i < 1_000_000_000L; i++) {
            singleCount++;
        }

        double singleSeconds = (System.nanoTime() - singleStartTime) / 1_000_000_000.0;
        System.out.println("Single Count: " + singleCount);
        System.out.println("Single Time: " + singleSeconds + " seconds");
        System.out.println("------------------------------------------");

        runMulti();
    }

    public static void runMulti() throws Exception {
        long startTime = System.nanoTime();

        int workers = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(workers);
        ArrayList<Future<Long>> futures = new ArrayList<>();

        try {
            for (int i = 0; i < 1000; i++) {
                Callable<Long> task = () -> {
                    long count = 0;
                    for (int number = 1; number <= 1_000_000; number++) {
                        count++;
                    }
                    return count;
                };
                futures.add(executor.submit(task));
            }

            long total = 0;
            for (Future<Long> future : futures) {
                total += future.get();
            }
            double seconds = (System.nanoTime() - startTime) / 1_000_000_000.0;
            System.out.println("Multi Count: " + total);
            System.out.println("Multi Time: " + seconds + " seconds");
        } finally {
            executor.shutdown();
        }
        System.out.println("Project complete: counting and timer merged");
    }
}
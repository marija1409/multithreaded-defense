import java.util.*;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static final int MAX_TIME = 5;
    public static final Random RANDOM = new Random();
    public static final ExecutorService threadPool = Executors.newCachedThreadPool();
    public static final CyclicBarrier professorBarrier = new CyclicBarrier(2);
    public static  AtomicInteger totalScore = new AtomicInteger(0);
    public static  AtomicInteger studentCount = new AtomicInteger(0);
    public static final long startTime = System.currentTimeMillis();

    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        int N = scanner.nextInt();
        for (int i = 0; i < N; i++) {
            threadPool.execute(new Student(i + 1));
        }
        threadPool.shutdown();
        try {
            if (!threadPool.awaitTermination(MAX_TIME, TimeUnit.SECONDS)) {
                threadPool.shutdownNow();
            }
        } catch (InterruptedException e) {
            threadPool.shutdownNow();
        }

        double avgScore = studentCount.get() == 0 ? 0 : (double) totalScore.get() / studentCount.get();
        System.out.printf("Average Score: %.2f%n", avgScore);

    }
}

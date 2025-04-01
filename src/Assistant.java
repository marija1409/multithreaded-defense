import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Assistant {
    private static final Semaphore assistantSemaphore = new Semaphore(1);

    static void defend(int studentId, long arrivalTime) throws InterruptedException {
        long elapsedTime = (System.currentTimeMillis() - Main.startTime) / 1000;
        long remainingTime = Main.MAX_TIME - elapsedTime;

        if (remainingTime <= 0) return;

        if (!assistantSemaphore.tryAcquire(remainingTime, TimeUnit.SECONDS)) return;

        try {
            long defenseTime = Main.RANDOM.nextInt(500) + 500;
            if (elapsedTime + (defenseTime / 1000) > Main.MAX_TIME) return;

            Thread.sleep(defenseTime);
            int score = Main.RANDOM.nextInt(6) + 5;
            Main.totalScore.addAndGet(score);
            Main.studentCount.incrementAndGet();

            System.out.println("Thread: Student-" + studentId + " Arrival: " + arrivalTime + " Prof: Assistant " + " TTC: " + (defenseTime / 1000.0) + " Score:" + score);

        } finally {
            assistantSemaphore.release();
        }
    }
}

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Professor {
    private static final Semaphore professorSemaphore = new Semaphore(2);

    static void defend(int studentId, long arrivalTime) throws InterruptedException, BrokenBarrierException, TimeoutException {
        long elapsedTime = (System.currentTimeMillis() - Main.startTime) / 1000;
        long remainingTime = Main.MAX_TIME - elapsedTime;

        if (remainingTime <= 0) return;

        if (!professorSemaphore.tryAcquire(remainingTime, TimeUnit.SECONDS)) return;

        try {
            Main.professorBarrier.await(remainingTime, TimeUnit.SECONDS);

            long defenseTime = Main.RANDOM.nextInt(500) + 500;
            if (elapsedTime + (defenseTime / 1000) > Main.MAX_TIME) return;

            Thread.sleep(defenseTime);
            int score = Main.RANDOM.nextInt(6) + 5;
            Main.totalScore.addAndGet(score);
            Main.studentCount.incrementAndGet();

            System.out.println("Thread: Student-" + studentId + " Arrival: " + arrivalTime + " Prof: Professor " + " TTC: " + (defenseTime / 1000.0) + " Score:" + score);
        } finally {
            professorSemaphore.release();
        }
    }
}

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.TimeoutException;

public class Student implements Runnable {
    private final int id;

    public Student(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(Main.RANDOM.nextInt(1000));
            long arrivalTime = (System.currentTimeMillis() - Main.startTime) / 1000;
            //System.out.println("arrival time:" + arrivalTime);
            long remainingTime = Main.MAX_TIME - arrivalTime;

            if (remainingTime <= 0) {
                System.out.println("Student-"  + id + " arrived too late and left");
                return;
            }

            boolean goesToProfessor = Main.RANDOM.nextBoolean();
            if (goesToProfessor) {
                Professor.defend(id, arrivalTime);
            } else {
                Assistant.defend(id, arrivalTime);
            }

        } catch (InterruptedException e) {
            System.err.println("Student-" + id + " was interrupted");
            Thread.currentThread().interrupt();
        } catch (BrokenBarrierException | TimeoutException e) {
            System.err.printf("Student-" + id+ " experienced a synchronization error: " + e.getMessage());
        }
    }
}

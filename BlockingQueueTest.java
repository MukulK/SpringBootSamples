import org.junit.jupiter.api.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BlockingQueueTest {

    @Test
    public void testBlockingQueue() throws InterruptedException {
        int capacity = 5;
        BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(capacity);

        // CountDownLatch to synchronize threads
        CountDownLatch latch = new CountDownLatch(2);

        // Producer thread
        Runnable producer = () -> {
            try {
                for (int i = 1; i <= capacity; i++) {
                    blockingQueue.put(i);
                    Thread.sleep(100); // Simulate some processing time
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                latch.countDown();
            }
        };

        // Consumer thread
        Runnable consumer = () -> {
            try {
                for (int i = 1; i <= capacity; i++) {
                    int value = blockingQueue.take();
                    assertEquals(i, value);
                    Thread.sleep(100); // Simulate some processing time
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                latch.countDown();
            }
        };

        // Create a fixed-size thread pool
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        // Submit producer and consumer tasks
        executorService.submit(producer);
        executorService.submit(consumer);

        // Wait for both threads to finish
        latch.await();

        // Shutdown the thread pool
        executorService.shutdown();
    }
}

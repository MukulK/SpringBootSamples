import java.util.concurrent.*;

class Producer implements Callable<Void> {
    private BlockingQueue<Integer> queue;

    public Producer(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public Void call() throws Exception {
        for (int i = 0; i < 5; i++) {
            // Produce items (in this case, numbers) and add to the queue
            System.out.println("Producing: " + i);
            queue.put(i);
            Thread.sleep(100); // Simulate some work being done
        }
        return null;
    }
}

class Consumer implements Callable<Void> {
    private BlockingQueue<Integer> queue;

    public Consumer(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public Void call() throws Exception {
        while (true) {
            // Consume items from the queue
            int value = queue.take();
            System.out.println("Consuming: " + value);
            Thread.sleep(200); // Simulate some work being done
        }
    }
}

public class Main {
    public static void main(String[] args) {
        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>();

        // Create a ThreadPoolExecutor with two threads
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        // Create a producer and a consumer
        Callable<Void> producer = new Producer(queue);
        Callable<Void> consumer = new Consumer(queue);

        // Submit them to the executor
        Future<Void> producerFuture = executorService.submit(producer);
        Future<Void> consumerFuture = executorService.submit(consumer);

        // Shutdown the executor after a delay
        executorService.shutdown();
        executorService.shutdownNow();

        try {
            // Wait for the producer and consumer to finish
            producerFuture.get();
            consumerFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}

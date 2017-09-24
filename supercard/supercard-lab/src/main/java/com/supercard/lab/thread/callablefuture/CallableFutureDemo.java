package com.supercard.lab.thread.callablefuture;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.*;

public class CallableFutureDemo {

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newCachedThreadPool();

        executorService.submit(new Runnable() {

            @Override
            public void run() {

                Random random = new Random();

                Integer duration = random.nextInt(4000);

                System.out.println("Starting Runnable Thread ...");

                System.out.println("Runnable Thread sleeping: " + duration);

                try {
                    Thread.sleep(duration);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("Finished Runnable Thread ...");

            }

        });

        Future<Integer> futureResult = executorService.submit(new Callable<Integer>() {

            @Override
            public Integer call() throws Exception {

                Random random = new Random();

                Integer duration = random.nextInt(4000);

                if (duration > 2000) {
                    throw new IllegalArgumentException("Sleeping duration is too long: " + duration);
                }

                try {
                    Thread.sleep(duration);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                return duration;

            }

        });

        executorService.shutdown();

        try {

            Integer result = (Integer)futureResult.get();
            System.out.println("The future result is: " + result);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            System.out.println(e.getCause().getMessage());
        }

    }

}

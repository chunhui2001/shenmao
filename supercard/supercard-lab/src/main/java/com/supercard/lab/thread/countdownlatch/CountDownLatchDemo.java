package com.supercard.lab.thread.countdownlatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Processor implements Runnable {

    private CountDownLatch latch = null;

    public Processor(CountDownLatch latch) {
        this.latch = latch;
    }

    public void run() {

        System.out.println("Started..");

        try {
            Thread.sleep(3000);

        } catch (InterruptedException e) {

        }

        latch.countDown();

    }

}

public class CountDownLatchDemo {


    public static void main(String[] args) throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(3);

        ExecutorService executor = Executors.newFixedThreadPool(3);

        executor.submit(new Processor(latch));
        executor.submit(new Processor(latch));
        executor.submit(new Processor(latch));

        latch.await();
        executor.shutdown();

        System.out.println("Completed...");

    }

}

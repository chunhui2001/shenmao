package com.supercard.lab.thread.semaphore;

import java.util.concurrent.*;

public class SemaphoreDemo {

    public static void main(String[] args) throws InterruptedException {

        Connection.getInstance().connect();

        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i=0; i<200; i++) {

            executorService.submit(() -> Connection.getInstance().connect());

        }

        executorService.shutdown();executorService.awaitTermination(1, TimeUnit.DAYS);

    }

}

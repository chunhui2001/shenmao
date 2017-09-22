package com.supercard.lab.thread.counting;

import java.util.concurrent.atomic.AtomicInteger;

public class CountingAtomic {

    public static void main(String[] args) throws InterruptedException {

        final AtomicInteger counter = new AtomicInteger();

        class CountingThread extends Thread {

            public void run() {
                for (int i=0; i<10000; i++) {
                    counter.incrementAndGet();
                }
            }

        }

        CountingThread t1 = new CountingThread();
        CountingThread t2 = new CountingThread();
        CountingThread t3 = new CountingThread();
        CountingThread t4 = new CountingThread();

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        t1.join();
        t2.join();
        t3.join();
        t4.join();

        System.out.println(counter.get());

    }

}

package com.supercard.lab.thread.paulbutcher;

import java.util.concurrent.locks.ReentrantLock;

public class DiningPhilosophersReentrantLock {

    public static void main(String[] args) throws InterruptedException {

        PhilosopherReentrantLock[] philosophers = new PhilosopherReentrantLock[5];        // 五个哲学家
        ReentrantLock[] chopsticks = new ReentrantLock[5];              // 五只筷子

        for (int i = 0; i < chopsticks.length; ++i)
            chopsticks[i] = new ReentrantLock();

        for (int i = 0; i < philosophers.length; ++i) {
            philosophers[i] = new PhilosopherReentrantLock(chopsticks[i], chopsticks[(i + 1) % chopsticks.length]);
            philosophers[i].start();
        }

        for (int i = 0; i < philosophers.length; ++i)
            philosophers[i].join();

    }
}

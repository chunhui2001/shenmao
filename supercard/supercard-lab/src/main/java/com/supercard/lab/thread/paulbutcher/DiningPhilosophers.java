package com.supercard.lab.thread.paulbutcher;

public class DiningPhilosophers {

    public static void main(String[] args) throws InterruptedException {

        Philosopher[] philosophers = new Philosopher[5];        // 五个哲学家
        Chopstick[] chopsticks = new Chopstick[5];              // 五只筷子

        for (int i = 0; i < chopsticks.length; ++i)
            chopsticks[i] = new Chopstick(i);

        for (int i = 0; i < philosophers.length; ++i) {
            philosophers[i] = new Philosopher(chopsticks[i], chopsticks[(i + 1) % chopsticks.length]);
            philosophers[i].start();
        }

        for (int i = 0; i < philosophers.length; ++i)
            philosophers[i].join();

    }
}

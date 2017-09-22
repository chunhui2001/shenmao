package com.supercard.lab.thread.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

public class DiedlockInterruptible {

    public static void main(String[] args) throws InterruptedException {

        final ReentrantLock lock1 = new ReentrantLock();
        final ReentrantLock lock2 = new ReentrantLock();

        Thread thread1 = new Thread(() -> {

            try {
                lock1.lockInterruptibly();
                Thread.sleep(1000);
                lock2.lockInterruptibly();
            } catch (InterruptedException e) {

                System.out.println("thread1 interrupted");
            }

        });

        Thread thread2 = new Thread(() -> {

            try {
                lock2.lockInterruptibly();
                Thread.sleep(1000);
                lock1.lockInterruptibly();
            } catch (InterruptedException e) {

                System.out.println("thread2 interrupted");
            }

        });

        System.out.println("ReentrangLock before lock");

        thread1.start(); thread2.start();
        Thread.sleep(2000);
        thread1.interrupt(); thread2.interrupt();
        thread1.join(); thread2.join();

        System.out.println("ReentrangLock after lock");

    }

}

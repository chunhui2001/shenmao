package com.supercard.lab.thread.reentrantlock;

public class Diedlock {

    public static void main(String[] args) throws InterruptedException {

        final Object oo1 = new Object();
        final Object oo2 = new Object();

        Thread tt1 = new Thread(() -> {

            try {
                synchronized (oo1) {
                    Thread.sleep(1000);
                    synchronized (oo2) {

                    }
                }
            } catch (InterruptedException e) {

                System.out.println("tt1 interrupted");
            }

        });

        Thread tt2 = new Thread(() -> {

            try {
                synchronized (oo2) {
                    Thread.sleep(1000);
                    synchronized (oo1) {

                    }
                }
            } catch (InterruptedException e) {
                System.out.println("tt2 interrupted");
            }
        });

        System.out.println("before lock");

        tt1.start(); tt2.start();
        Thread.sleep(2000);
        tt1.interrupt(); tt2.interrupt();
        tt1.join(); tt2.join();

        System.out.println("after lock");

    }

}

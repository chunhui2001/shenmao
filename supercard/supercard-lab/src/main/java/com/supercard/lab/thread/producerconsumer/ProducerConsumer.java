package com.supercard.lab.thread.producerconsumer;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ProducerConsumer {

    private static BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);

    private static void producer() {

        Random ran = new Random();

        while (true) {
            try {
                queue.put(ran.nextInt(100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void consumer() {

        Random ran = new Random();

        while (true) {

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (ran.nextInt(10) == 0) {
                try {
                    System.out.println("Take value: " + queue.take() + ", queue size is: " + queue.size());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
//                System.out.println("skip");
            }

        }

    }

    public static void main(String[] args) throws InterruptedException {

        Thread producer = new Thread(ProducerConsumer::producer);
        Thread consumer = new Thread(ProducerConsumer::consumer);

        producer.start();
        consumer.start();
        producer.join();
        consumer.join();

    }

}










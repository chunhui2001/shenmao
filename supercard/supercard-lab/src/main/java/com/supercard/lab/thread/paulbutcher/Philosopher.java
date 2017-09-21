package com.supercard.lab.thread.paulbutcher;

import java.util.Random;

public class Philosopher extends Thread {

    private Object left, right;
    private Random random;
    private int thinkCount;

    public Philosopher(Object left, Object right) {
        this.left = left;
        this.right = right;
        this.random = new Random();
    }

    public void run() {

        try {

            while (true) {

                ++thinkCount;
                if (thinkCount % 2 == 0)
                    System.out.println("哲学家 [" + this + " " + this.getId() + "] has 思考了 " + thinkCount + " 次");

                Thread.sleep(random.nextInt(1000));     // 思考一段时间

                synchronized (left) {                   // 拿起左手边的筷子
                    synchronized (right) {              // 拿起右手边的筷子
                        System.out.println("哲学家 [" + this + " " + this.getId() + ": 进餐一段时间");
                        Thread.sleep(random.nextInt(1000));     // 进餐一段时间
                    }
                }

            }

        } catch (InterruptedException e) {

        } finally {

        }
    }


}

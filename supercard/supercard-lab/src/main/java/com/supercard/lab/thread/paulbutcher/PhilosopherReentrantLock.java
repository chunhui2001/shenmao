package com.supercard.lab.thread.paulbutcher;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class PhilosopherReentrantLock extends Thread {

    private ReentrantLock leftChopstick, rightChopstick;
    private Random random;
    private int thinkCount;

    public PhilosopherReentrantLock(ReentrantLock left, ReentrantLock right) {
        this.leftChopstick = left;
        this.rightChopstick = right;
        random = new Random();
    }

    public void run() {

        try {

            while (true) {

                ++thinkCount;
                if (thinkCount % 2 == 0)
                    System.out.println("哲学家 [" + this + " " + this.getId() + "] has 思考了 " + thinkCount + " 次");

                Thread.sleep(random.nextInt(1000));     // 思考一段时间

                leftChopstick.lock();                   // 拿起左手边的筷子

                try {

                    // 拿起右手边的筷子, 此处使用了 tryLock() 避免了无尽的死锁:
                    // [****] 这个方案并不能避免死锁，它只是提供了从死锁中恢复的手段
                    // [****] 如果所有死锁线程同时超时, 它极有可能在此陷入超时，死然死锁没有永远持续下去，
                    // [****] 但对资源的争夺情况没有得到任何改善
                    if (rightChopstick.tryLock(1000, TimeUnit.MICROSECONDS)) {

                        try {
                            System.out.println("[ReentrantedLock] 哲学家 [" + this + " " + this.getId() + ": 进餐一段时间");
                            Thread.sleep(random.nextInt(1000)); // Eat for a while
                        } finally {
                            rightChopstick.unlock();
                        }

                    } else {
                        // 没有拿到右手边的筷子，放弃并继续思考
                        System.out.println("[ReentrantedLock] 哲学家 [" + this + " " + this.getId() + ": 没有拿到右手边的筷子，放弃并继续思考");
                    }

                } finally { leftChopstick.unlock(); }

            }

        } catch (InterruptedException e) {

        } finally {

        }
    }

}

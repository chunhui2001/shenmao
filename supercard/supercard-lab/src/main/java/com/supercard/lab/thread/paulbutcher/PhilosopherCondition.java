package com.supercard.lab.thread.paulbutcher;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class PhilosopherCondition extends Thread {

    private boolean eating;
    private PhilosopherCondition left;
    private PhilosopherCondition right;

    private ReentrantLock table;
    private Condition condition;

    private int thinkCount;

    public PhilosopherCondition (ReentrantLock table) {
        this.eating = false;
        this.table = table;
        this.condition = table.newCondition();
    }

    public void setLeft(PhilosopherCondition left) {
        this.left = left;
    }

    public void setRight(PhilosopherCondition right) {
        this.right = right;
    }

    public void run() {

        try {
            while (true) {
                this.think();
                this.eating();
            }
        } catch (InterruptedException e) {}
    }

    private void think() throws InterruptedException{

        this.table.lock();

        try {

            this.eating = false;

            // 通知左右邻居可以进餐, 如果左右邻居此时正在等待，他们将被唤醒
            this.left.condition.signal();
            this.right.condition.signal();

        } finally {
            this.table.unlock();
        }

        ++thinkCount;
        if (thinkCount % 2 == 0)
            System.out.println("哲学家 [" + this + " " + this.getId() + "] has 思考了 " + thinkCount + " 次");


        Thread.sleep(1000);
    }

    private void eating() throws InterruptedException {

        // 开始进餐时，锁定餐桌
        this.table.lock();

        try {

            // 将竞争从筷子的争夺转换成对状态的判断
            // 仅当左右邻居没有进餐时，自己才可以进餐
            while (this.eating || this.right.eating)
                this.condition.await();

            eating = true;

        } finally {
            this.table.unlock();
        }

        System.out.println("哲学家 [" + this + " " + this.getId() + ": 进餐一段时间");

        Thread.sleep(1000);

    }

    public static void main(String[] args) throws InterruptedException {

        int philosopherCount = 500;                      // 哲学家数量

        ReentrantLock table = new ReentrantLock();      // 一个桌子
        PhilosopherCondition[] philosopherConditions = new PhilosopherCondition[philosopherCount];        // 哲学家队列

        for (int i = 0; i < philosopherConditions.length; ++i) {
            philosopherConditions[i] = new PhilosopherCondition(table);
        }

        for (int i = 0; i < philosopherConditions.length; ++i) {
            philosopherConditions[i].setLeft(philosopherConditions[(i + philosopherCount - 1) % philosopherCount]);
            philosopherConditions[i].setRight(philosopherConditions[(i + 1) % philosopherCount]);
            philosopherConditions[i].start();
        }

        for (int i = 0; i < philosopherConditions.length; ++i)
            philosopherConditions[i].join();

    }

}

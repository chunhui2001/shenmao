package com.supercard.lab;

public class Puzzle {

    static boolean answerReady = false;
    static int answer = 0;

    static Thread t1 = new Thread() {
        public void run() {

            /*
                在竞态条件下，当 answerReady 为 true 时，answer 可能等于 0!
                仿佛 18 和 19 行眼睁睁的颠倒了顺序, 这是因为:
                [****] 编译器的 "静态优化" 可能打乱代码的执行顺序
                [****] JVM 的 "动态优化" 也会打乱代码的执行顺序
                [****] 硬件可以通过 "乱序执行" 来优化其性能
             */
            answer = 42;
            answerReady = true;
        }
    };

    static Thread t2 = new Thread() {

        public void run() {
            if (answerReady) {
                System.out.println("The meaing of life is: " + answer);
            } else {
                System.out.println("I don't know the answer");
            }
        }

        /*
            有时一个线程产生的修改可能对另一个线程不可见，
            answerReady 可能不会变成 true，导致程序运行后无法退出
         */
        /* public void run() {
            while (!answerReady) {
                Thread.sleep(100);
                System.out.println("The meaing of life is: " + answer);
            }
        } */

    };

    public static void main(String[] args) throws InterruptedException {

        t1.start();Thread.yield();t2.start();
        t1.join(); t2.join();
    }

}

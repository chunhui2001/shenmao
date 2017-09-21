package com.supercard.lab;

public class HelloThread {

    public static void main(String[] args) throws InterruptedException {

        Thread myFirstThread = new Thread() {

            public void run() {
                System.out.println("Hello from my first thread from Keesh");
            }

        };

        myFirstThread.start();

        // 通知调度器，当前线程让出对处理器的占用
        // 如果不调用 yield, 由于创建新线程需要花费一些时间，那么 main 线程几乎肯定会先执行 pringln
        // 调用 yield 后，多次运行程序, main 线程和 myFirstThread 会交替执行
        Thread.yield();
//        Thread.sleep(1);

        System.out.println("Hello from main thread");

        myFirstThread.join();

    }
}

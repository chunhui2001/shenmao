package com.supercard.lab.thread.counting;

/*
    用锁达到线程互斥的目的，
    即某一时间至多有一个线程能持有锁
 */
public class CountingSynchronized {



    public static void main(String[] args) throws InterruptedException {

        class Counter {

            private int count = 0;

//            public void increment() { count++; }

            /*
                使用对象内置锁 synchornized,
                当线程进入 increment() 函数时将获取 Counter 对象级别的锁，在函数返回时释放该锁，
                即某一时刻只有一个线程可以执行 increment() 函数体,
                其它线程调用函数时将被阻塞直到锁被释放，
                对于这种只涉及一个变量的场景使用 java.util.concurrent.atomic 包是更好的选择
            */
            public synchronized void increment() { count++; }

            /*
                这种设计为其它调用 getCount() 的代码留下了隐患！！！
                因为:
                [****] 除了 increment() 函数, getCount() 方法也需要进行同步，
                [****] 由于内存可见性问题, 调用 getCount() 的线程可能得到一个失效的值,
                [****] 在当前程序中由于 getCount() 在 join() 之后被调用, 因此是线程安全的.
             */
            public int getCount() { return this.count; }

        }


        final Counter counter = new Counter();

        class CountingThread extends Thread {


            public void run() {

                for (int i=0; i<10000; i++) {
                    counter.increment();
                }
            }

        }


        CountingThread t1 = new CountingThread();
        CountingThread t2 = new CountingThread();
        CountingThread t3 = new CountingThread();
        CountingThread t4 = new CountingThread();

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        t1.join();
        t2.join();
        t3.join();
        t4.join();

        System.out.println(counter.getCount());

    }
}

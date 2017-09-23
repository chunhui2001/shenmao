package com.supercard.lab.thread.wordcount;

import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

public class WordCountConcurrentMap extends WordCountHelper implements Runnable {


    private BlockingQueue<PageAbstract> pages;
    private ConcurrentMap<String, Integer> wordcounts;
    private static ReentrantLock lock;

    public WordCountConcurrentMap(BlockingQueue<PageAbstract> pages, ConcurrentMap<String, Integer> wordcounts) {
        this.pages = pages;
        this.wordcounts = wordcounts;
    }

    @Override
    public void run() {

        try {

            while (true) {

                PageAbstract page = pages.take();

                if (page.isPoisonPill()) {
                    break;
                }

                Iterable<String> words = new Words(page.getText());

                for (String word : words) {
                    countWord(word);
                }

            }

        } catch (Exception e) {}

    }


    private void countWord(String word) {

        /* lock.lock();

        try {

            Integer currentCount = wordcounts.get(word);

            if (currentCount == null) {
                wordcounts.put(word, 1);
                return;
            }

            wordcounts.put(word, currentCount + 1);

        } finally {
            lock.unlock();
        } */

        while (true) {

            Integer currentCount = wordcounts.get(word);
            if (currentCount == null) {
                if (wordcounts.putIfAbsent(word, 1) == null)
                    break;
            } else if (wordcounts.replace(word, currentCount, currentCount + 1)) {
                break;
            }

        }

    }


    private static final int _NUM_COUNTERS = 8;

    public static void main(String[] args) throws InterruptedException {

        ArrayBlockingQueue<PageAbstract> pages = new ArrayBlockingQueue<>(1000);
        ConcurrentHashMap<String, Integer> counts = new ConcurrentHashMap<>();

        long begin = System.currentTimeMillis();

        Thread producer = new Thread(new Parser(_PAGE_COUNT, _FILE_NAME, pages));

//        ExecutorService productorExecutor = Executors.newCachedThreadPool();
        ExecutorService customerExecutor = Executors.newCachedThreadPool();


//        for (int i=0; i<_NUM_COUNTERS; i++) {
//            productorExecutor.execute(new Parser(_PAGE_COUNT, _FILE_NAME, pages));
//        }

        for (int i=0; i<_NUM_COUNTERS; i++) {
            customerExecutor.execute(new WordCounter(pages, counts));
        }

        producer.start();
        producer.join();


        for (int i=0; i<_NUM_COUNTERS; i++) {
            pages.put(new PagePoisonPill());
        }



        customerExecutor.shutdown();
        customerExecutor.awaitTermination(10L, TimeUnit.MINUTES);

//
//        productorExecutor.shutdown();
//        productorExecutor.awaitTermination(10L, TimeUnit.MINUTES);


        System.out.println("耗时: " + formatTime(System.currentTimeMillis() - begin));
        System.out.println("All words name's count: " + counts.size());

    }

}

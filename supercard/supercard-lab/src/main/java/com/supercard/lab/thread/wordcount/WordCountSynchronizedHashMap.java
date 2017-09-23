package com.supercard.lab.thread.wordcount;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

public class WordCountSynchronizedHashMap extends WordCountHelper implements Runnable {


    private BlockingQueue<PageAbstract> pages;
    private Map<String, Integer> wordcounts;
    private static ReentrantLock lock;

    public WordCountSynchronizedHashMap(BlockingQueue<PageAbstract> pages, Map<String, Integer> wordcounts) {
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

        lock.lock();

        try {

            Integer currentCount = wordcounts.get(word);

            if (currentCount == null) {
                wordcounts.put(word, 1);
                return;
            }

            wordcounts.put(word, currentCount + 1);

        } finally {
            lock.unlock();
        }

    }


    private static final int _NUM_COUNTERS = 2;

    public static void main(String[] args) throws InterruptedException {

        ArrayBlockingQueue<PageAbstract> pages = new ArrayBlockingQueue<PageAbstract>(100);
        HashMap<String, Integer> counts = new HashMap<>();

        long begin = System.currentTimeMillis();

        Thread producer = new Thread(new Parser(_PAGE_COUNT, _FILE_NAME, pages));

        ExecutorService customerExecutor = Executors.newCachedThreadPool();

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


        System.out.println("耗时: " + formatTime(System.currentTimeMillis() - begin));
        System.out.println("All words name's count: " + counts.size());

    }

}

package com.supercard.lab.thread.wordcount;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class WordCounter extends WordCountHelper implements Runnable {


    private BlockingQueue<PageAbstract> pages;
    private Map<String, Integer> wordcounts;

    public WordCounter(BlockingQueue<PageAbstract> pages, Map<String, Integer> wordcounts) {
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

        Integer currentCount = wordcounts.get(word);

        if (currentCount == null) {
            wordcounts.put(word, 1);
            return;
        }

        wordcounts.put(word, currentCount + 1);

    }

    public static void main(String[] args) throws InterruptedException {

        ArrayBlockingQueue<PageAbstract> pages = new ArrayBlockingQueue<PageAbstract>(100);
        HashMap<String, Integer> counts = new HashMap<>();

        long begin = System.currentTimeMillis();

        Thread producer = new Thread(new Parser(_PAGE_COUNT, _FILE_NAME, pages));
        Thread customer = new Thread(new WordCounter(pages, counts));

        customer.start();
        producer.start();
        producer.join();

        pages.put(new PagePoisonPill());
        customer.join();


        System.out.println("耗时: " + formatTime(System.currentTimeMillis() - begin));
        System.out.println("All words name's count: " + counts.size());

    }

}

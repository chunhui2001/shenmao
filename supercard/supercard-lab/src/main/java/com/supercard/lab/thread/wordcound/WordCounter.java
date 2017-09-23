package com.supercard.lab.thread.wordcound;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class WordCounter implements Runnable {


    public static String formatTime(Long ms) {

        Integer ss = 1000, mi = ss * 60, hh = mi * 60, dd = hh * 24;

        Long day = ms / dd;
        Long hour = (ms - day * dd) / hh;
        Long minute = (ms - day * dd - hour * hh) / mi;
        Long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        Long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        StringBuffer sb = new StringBuffer();
        if(day > 0) sb.append(day+"天");
        if(hour > 0) sb.append(hour+"小时");
        if(minute > 0) sb.append(minute+"分");
        if(second > 0) sb.append(second+"秒");

        if(milliSecond > 0) sb.append(milliSecond+"毫秒");

        return sb.toString();
    }

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

        String enwikiFilename = "/Users/keesh/Desktop/enwiki.xml";


        ArrayBlockingQueue<PageAbstract> pages = new ArrayBlockingQueue<PageAbstract>(100);
        HashMap<String, Integer> counts = new HashMap<>();

        long begin = System.currentTimeMillis();

        Thread producer = new Thread(new Parser(100000, enwikiFilename, pages));
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

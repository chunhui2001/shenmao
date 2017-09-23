package com.supercard.lab.thread.wordcount;

import java.util.concurrent.BlockingQueue;

public class Parser implements Runnable {

    private BlockingQueue<PageAbstract> queue;
    private final String filename;
    private final Integer pagecount;

    public Parser(final Integer pagecount, final String filename, BlockingQueue<PageAbstract> queue) {
        this.queue = queue;
        this.filename = filename;
        this.pagecount = pagecount;
    }

    @Override
    public void run() {

        try {

            Iterable<PageAbstract> pages = new WikiPages(this.pagecount, this.filename);
            for (PageAbstract page : pages) queue.put(page);

        } catch (Exception e) {}

    }

}

package com.supercard.lab.thread.wordcount;

public class PageAbstract {
    public String getTitle() { throw new UnsupportedOperationException(); }
    public String getText() { throw new UnsupportedOperationException(); }
    public boolean isPoisonPill() { return false; }
}

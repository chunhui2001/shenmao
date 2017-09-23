package com.supercard.lab.thread.wordcount;

public class WikiPage extends PageAbstract {

    private String title;
    private String text;

    public WikiPage(String title, String text) { this.title = title; this.text = text; }

    public String getTitle() { return title; }
    public String getText() { return text; }

}
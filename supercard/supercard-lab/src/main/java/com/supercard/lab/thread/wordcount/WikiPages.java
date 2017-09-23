package com.supercard.lab.thread.wordcount;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.util.Iterator;

public class WikiPages implements Iterable<PageAbstract> {

    private final int maxPages;
    private final String fileName;

    public WikiPages(int maxPages, String fileName) {
        this.maxPages = maxPages;
        this.fileName = fileName;
    }

    private class WikiPageIterator implements Iterator<PageAbstract> {

        private XMLEventReader reader;
        private int remainingPages;
        private boolean nosuchelement;

        public WikiPageIterator() throws Exception {
            remainingPages = maxPages;
            reader = XMLInputFactory.newInstance().createXMLEventReader(new FileInputStream(fileName));
        }

        public boolean hasNext() { return !nosuchelement && remainingPages > 0; }

        public PageAbstract next() {

            try {

                XMLEvent event;
                String title = "", text = "";

                while (true) {

                    event = reader.nextEvent();

                    final boolean isStartElement = event.isStartElement();
                    final boolean isPageElement = isStartElement && event.asStartElement().getName().getLocalPart().equals("page");

                    if (!isStartElement || !isPageElement) continue;

                    while (true) {

                        event = reader.nextEvent();

                        if (event.isStartElement()) {
                            String name = event.asStartElement().getName().getLocalPart();
                            if (name.equals("title"))
                                title = reader.getElementText();
                            else if (name.equals("text"))
                                text = reader.getElementText();
                        } else if (event.isEndElement()) {
                            if (event.asEndElement().getName().getLocalPart().equals("page")) {
                                --remainingPages;
//                                System.out.println(title);
                                return new WikiPage(title, text);
                            }
                        }
                    }

                }

            } catch (Exception e) {

            }

//            throw new NoSuchElementException();
//            return null;
            nosuchelement = true;
            return new PagePoisonPill();

        }

        public void remove() { throw new UnsupportedOperationException(); }

    }

    public Iterator<PageAbstract> iterator() {

        try {
            return new WikiPages.WikiPageIterator();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
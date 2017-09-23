package com.supercard.lab.thread.wordcount;

import com.fasterxml.jackson.core.JsonProcessingException;

import javax.xml.stream.XMLStreamException;
import java.io.FileNotFoundException;
import java.util.HashMap;

public class WordCount extends WordCountHelper {


    private static final HashMap<String, Integer> counts = new HashMap<>();

    private static void countWord(String word) {

        Integer currentCount = counts.get(word);

        if (currentCount == null) {
            counts.put(word, 1);
            return;
        }

        counts.put(word, currentCount + 1);

    }

    public static void main(String[] args) throws FileNotFoundException, XMLStreamException, JsonProcessingException {

        long begin = System.currentTimeMillis();

        Iterable<Page> pages = new Pages(_PAGE_COUNT, _FILE_NAME);       // 生产者

        for (Page page : pages) {

            if (page == null) break;

            Iterable<String> words = new Words(page.getText());         // 消费者

            for (String word : words) countWord(word);

        }


        System.out.println("耗时: " + formatTime(System.currentTimeMillis() - begin));

//        System.out.println(new ObjectMapper().writeValueAsString(counts));

        System.out.println("All words name's count: " + counts.size());

    }

}

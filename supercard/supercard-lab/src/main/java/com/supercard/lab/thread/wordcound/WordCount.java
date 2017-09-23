package com.supercard.lab.thread.wordcound;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

public class WordCount {

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

        String enwikiFilename = "/Users/keesh/Desktop/enwiki.xml";

        long begin = System.currentTimeMillis();

        Iterable<Page> pages = new Pages(100000, enwikiFilename);       // 生产者

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

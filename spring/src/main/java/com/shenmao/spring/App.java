package com.shenmao.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by keesh on 30/06/2017.
 */
public class App {

    public static void main(String[] args) {

        ApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");

        MessageService messageService
                    = (MessageService)context.getBean("messageService");

        MessagePrinter messagePrinter = new MessagePrinter(messageService);

        messagePrinter.printMessage();


    }
}

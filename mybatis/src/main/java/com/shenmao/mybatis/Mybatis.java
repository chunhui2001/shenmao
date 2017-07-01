package com.shenmao.mybatis;

import com.shenmao.mybatis.dao.UserMapper;
import com.shenmao.mybatis.entity.User;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.Date;

/**
 * Created by keesh on 01/07/2017.
 */
public class Mybatis {

    public static void  main(String[] args) throws IOException {

        ApplicationContext ctx =
                new ClassPathXmlApplicationContext("applicationContext.xml");

        MessagePrinter messagePrinter = ctx.getBean(MessagePrinter.class);
        messagePrinter.print();


        UserMapper userMapper = messagePrinter.getUserRepository();


        // select
        User user = (User)  userMapper.selectByPrimaryKey(312);

        System.out.println(
                new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(user));


        // insert
        User newUser = new User();
        newUser.setUserName("keesh.张 6767");
        newUser.setAddTime(new Date());

        userMapper.insert(newUser);

        System.out.println(newUser.getUserId() + " newUser Id saved");


        // update
        user.setUserName("keesh.张 6767 updated");
        userMapper.updateByPrimaryKeySelective(user);

        System.out.println(
                new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(user));


    }
}

package com.shenmao.mybatis;

import com.shenmao.mybatis.entity.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {

        ApplicationContext ctx =
                new ClassPathXmlApplicationContext("applicationContext.xml");

        MessagePrinter messagePrinter = ctx.getBean(MessagePrinter.class);

        messagePrinter.print();



        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        SqlSession session = sqlSessionFactory.openSession();

        // select
        try {

            User user = (User) session.selectOne("com.shenmao.mybatis.dao.UserMapper.selectByPrimaryKey", 312);

            // System.out.println(new ObjectMapper().writeValueAsString(user));
            System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(user));

        } finally {
            session.close();
        }

        // save
        session = sqlSessionFactory.openSession(true);

        try {

            User user = new User();

            user.setUserName("keesh.张 6767");
            user.setAddTime(new Date());

            // Integer affectRowCount = session.insert("com.shenmao.mybatis.dao.UserMapper.insert", user);
            Integer affectRowCount = session.insert("com.shenmao.mybatis.dao.UserMapper.insertSelective", user);

            System.out.println(user.getUserId() + " save user");

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            session.close();
        }


        // update
        session = sqlSessionFactory.openSession(true);

        try {

            User user = (User) session.selectOne("com.shenmao.mybatis.dao.UserMapper.selectByPrimaryKey", 315);

            user.setUserName("张春晖666");
            user.setAddTime(new Date());

            int affectRowCount = session.update("com.shenmao.mybatis.dao.UserMapper.updateByPrimaryKeySelective", user);

            System.out.println(affectRowCount + " update affectRowCount");

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            session.close();
        }



        // batch insert
        session = sqlSessionFactory.openSession(true);

        try {

            User user1 = new User();
            user1.setUserName("keesh.张1");
            user1.setAddTime(new Date());

            User user2 = new User();
            user2.setUserName("keesh.张2");
            user2.setAddTime(new Date());

            List<User> userList = new ArrayList<User>() {};
            userList.add(user1);
            userList.add(user2);

            Integer affectRowCount = session.insert("com.shenmao.mybatis.dao.UserMapper.insertBatch", userList);

            System.out.println(affectRowCount + " batch insert");

        } catch (Exception e) {
            System.out.println(e);
        } finally {
            session.close();
        }


    }
}

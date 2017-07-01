package com.shenmao.mybatis;

import com.shenmao.mybatis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by keesh on 01/07/2017.
 */
@Component
public class MessagePrinter {

    final private UserRepository userRepository;

    public void print() {
        System.out.println("Spring Message from Keesh.张");
    }

    @Autowired
    public MessagePrinter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserRepository getUserRepository() {
        return this.userRepository;
    }

}

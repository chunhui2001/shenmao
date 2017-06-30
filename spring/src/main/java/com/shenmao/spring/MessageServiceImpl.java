package com.shenmao.spring;

import org.springframework.stereotype.Service;

/**
 * Created by keesh on 30/06/2017.
 */
@Service("messageService")
public class MessageServiceImpl implements MessageService {
    @Override
    public String getMessage() {
        return "Hello World!";
    }
}

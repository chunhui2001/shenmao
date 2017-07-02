package com.shenmao.mybatis;

import com.shenmao.mybatis.repository.OrderItemRepository;
import com.shenmao.mybatis.repository.OrderRepository;
import com.shenmao.mybatis.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by keesh on 01/07/2017.
 */
@Component
public class MessagePrinter {

    final private UserRepository userRepository;
    final private OrderRepository orderRepository;
    final private OrderItemRepository orderItemRepository;

    public void print() {
        System.out.println("Spring Message from Keesh.张");
    }

    @Autowired
    public MessagePrinter(UserRepository userRepository, OrderRepository orderRepository, OrderItemRepository orderItemRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    public UserRepository getUserRepository() {
        return this.userRepository;
    }

    public OrderRepository getOrderRepository() { return this.orderRepository; }
    public OrderItemRepository getOrderItemRepository() { return this.orderItemRepository; }

}

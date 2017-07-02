package com.shenmao.mybatis;

import com.shenmao.mybatis.entity.Order;
import com.shenmao.mybatis.entity.OrderItem;
import com.shenmao.mybatis.repository.OrderItemRepository;
import com.shenmao.mybatis.repository.OrderRepository;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * Created by keesh on 02/07/2017.
 */
public class OrderLab {

    public static void main(String[] args) throws IOException {


        ApplicationContext ctx =
                new ClassPathXmlApplicationContext("applicationContext.xml");

        MessagePrinter messagePrinter = ctx.getBean(MessagePrinter.class);
        messagePrinter.print();

        // order
        Order order = new Order();

        order.setUserId(312);
        order.setCreatedAt(new Date());
        order.setOrderStatus("Active");
        order.setTotalMoney( new BigDecimal(600) );

        OrderRepository orderRepository = messagePrinter.getOrderRepository();
        OrderItemRepository orderItemRepository = messagePrinter.getOrderItemRepository();

        // order item
        OrderItem orderItem1 = new OrderItem();
        orderItem1.setOrderId(order.getOrderId());
        orderItem1.setCount(15);
        orderItem1.setProductId(10001);
        orderItem1.setPrice(new BigDecimal(13.25));
        orderItem1.setProductName("小米5k电视5");
        orderItem1.setProductUnit("台");

        OrderItem orderItem2 = new OrderItem();
        orderItem2.setOrderId(order.getOrderId());
        orderItem2.setCount(6);
        orderItem2.setProductId(10003);
        orderItem2.setPrice(new BigDecimal(6005));
        orderItem2.setProductName("小米电动车5s3 小米电动车5s3 小米电动车5s3 小米电动车5s3 小米电动车5s3 小米电动车5s3 小米电动车5s3 小米电动车5s3 小米电动车5s3 小米电动车5s3 小米电动车5s3 小米电动车5s3 小米电动车5s3 小米电动车5s3 小米电动车5s3 小米电动车5s3 小米电动车5s3 小米电动车5s3 小米电动车5s3 小米电动车5s3 小米电动车5s3 小米电动车5s3 小米电动车5s3 ");
        orderItem2.setProductUnit("台");


        Collection<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem1);
        orderItems.add(orderItem2);

        orderRepository.insert(order, orderItems);


        System.out.println(
                new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(order));





    }
}

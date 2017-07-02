package com.shenmao.mybatis.repository;

import com.shenmao.mybatis.dao.OrderMapper;
import com.shenmao.mybatis.entity.Order;
import com.shenmao.mybatis.entity.OrderItem;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * Created by keesh on 02/07/2017.
 */
@Repository("orderRepository")
public class OrderRepository implements OrderMapper {

    private SqlSessionTemplate sqlSessionTemplate = null;
    final private OrderItemRepository orderItemRepository;

    @Autowired
    public OrderRepository(SqlSessionTemplate sqlSessionTemplate, OrderItemRepository orderItemRepository) {
        this.sqlSessionTemplate = sqlSessionTemplate;
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public int deleteByPrimaryKey(Long orderId) {
        return 0;
    }

    @Override
    public int insert(Order record) {
        int affectRowCount = sqlSessionTemplate.insert(
                "com.shenmao.mybatis.dao.OrderMapper.insert", record);
        return affectRowCount;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int insert(Order record, Collection<OrderItem> items) {

        int affectRowCount = sqlSessionTemplate.insert(
                "com.shenmao.mybatis.dao.OrderMapper.insert", record);

        for (OrderItem orderItem : items) {
            orderItem.setOrderId(record.getOrderId());
            this.orderItemRepository.insert(orderItem);
        }

        return affectRowCount;
    }

    @Override
    public int insertSelective(Order record) {
        return 0;
    }

    @Override
    public Order selectByPrimaryKey(Long orderId) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(Order record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(Order record) {
        return 0;
    }
}

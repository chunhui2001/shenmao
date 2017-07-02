package com.shenmao.mybatis.dao;

import com.shenmao.mybatis.entity.Order;
import com.shenmao.mybatis.entity.OrderItem;

import java.util.Collection;

public interface OrderMapper {
    int deleteByPrimaryKey(Long orderId);

    int insert(Order record);

    public int insert(Order record, Collection<OrderItem> items);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Long orderId);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
}
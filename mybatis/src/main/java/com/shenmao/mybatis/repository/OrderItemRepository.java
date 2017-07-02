package com.shenmao.mybatis.repository;

import com.shenmao.mybatis.dao.OrderItemMapper;
import com.shenmao.mybatis.entity.OrderItem;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by keesh on 02/07/2017.
 */
@Repository("orderItemRepository")
public class OrderItemRepository implements OrderItemMapper {


    private SqlSessionTemplate sqlSessionTemplate = null;

    @Autowired
    public OrderItemRepository(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }



    @Override
    public int deleteByPrimaryKey(Long orderItemId) {
        return 0;
    }

    @Override
    public int insert(OrderItem record) {
        int affectRowCount = sqlSessionTemplate.insert(
                "com.shenmao.mybatis.dao.OrderItemMapper.insert", record);
        return affectRowCount;
    }

    @Override
    public int insertSelective(OrderItem record) {
        return 0;
    }

    @Override
    public OrderItem selectByPrimaryKey(Long orderItemId) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(OrderItem record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(OrderItem record) {
        return 0;
    }
}

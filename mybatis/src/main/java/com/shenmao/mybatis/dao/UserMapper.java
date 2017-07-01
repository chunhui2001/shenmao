package com.shenmao.mybatis.dao;

import com.shenmao.mybatis.entity.User;

import java.util.Collection;

public interface UserMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(User record);

    int insertBatch(Collection<User> records);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}
package com.shenmao.mybatis.repository;

import com.shenmao.mybatis.dao.UserMapper;
import com.shenmao.mybatis.entity.User;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by keesh on 01/07/2017.
 */
@Repository("userRepository")
public class UserRepository implements UserMapper {

    private SqlSessionTemplate sqlSessionTemplate = null;

    @Autowired
    public UserRepository(SqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    @Override
    public int deleteByPrimaryKey(Integer userId) {
        return 0;
    }

    @Override
    public int insert(User record) {
        List<User> userList = new ArrayList<User>() {};
        userList.add(record);
        int affectRowCount = this.insertBatch(userList);
        return affectRowCount > 0 ? userList.get(0).getUserId() : -1;
    }

    @Override
    public int insertBatch(Collection<User> records) {
        int affectRowCount = sqlSessionTemplate.insert(
                "com.shenmao.mybatis.dao.UserMapper.insertBatch", records);
        return affectRowCount;
    }

    @Override
    public int insertSelective(User record) {
        return 0;
    }

    @Override
    public User selectByPrimaryKey(Integer userId) {
        return sqlSessionTemplate.selectOne(
                "com.shenmao.mybatis.dao.UserMapper.selectByPrimaryKey", userId);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
    public int updateByPrimaryKeySelective(User record) {
        return sqlSessionTemplate.update(
                "com.shenmao.mybatis.dao.UserMapper.updateByPrimaryKeySelective", record);
    }

    @Override
    public int updateByPrimaryKey(User record) {
        return 0;
    }

}

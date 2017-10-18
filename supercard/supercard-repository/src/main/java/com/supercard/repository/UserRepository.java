package com.supercard.repository;


import com.supercard.tour.UserEntity;

import java.util.List;

//import com.supercard.netty.tour.UserEntity;

/**
 * Created by cc on 2017/7/20.
 */
public interface UserRepository {
    public List<UserEntity> list();
    public UserEntity find(Object pk);
    public UserEntity findByName(String userName);

    default public UserEntity processUser(UserEntity user) {

        if (user.getGender() == null) {
            user.setGender(UserEntity.Gender.MEN);
        }

        if (user.getPhoto() != null) {
            return user;
        }

        if (user.getGender().equals(UserEntity.Gender.MEN)) {
            user.setPhoto("/gallery/user-profile.jpg");
        }

        if (user.getGender().equals(UserEntity.Gender.WOMEN)) {
            user.setPhoto("/gallery/user-profile2.png");
        }

        return user;
    }
}

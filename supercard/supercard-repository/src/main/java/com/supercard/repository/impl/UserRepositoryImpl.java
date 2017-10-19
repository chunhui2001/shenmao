package com.supercard.repository.impl;

import com.supercard.repository.UserRepository;
import com.supercard.tour.UserEntity;
import com.supercard.tour.UserRoleEnum;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

//import com.supercard.netty.tour.UserEntity;
//import com.supercard.netty.tour.UserRoleEnum;

/**
 * Created by cc on 2017/7/20.
 */
public class UserRepositoryImpl implements UserRepository {

    public static List<UserEntity> UserList;

    static {

        UserList = new ArrayList<>();

        UserList.add(new UserEntity() {{
            setUserId(java.util.UUID.randomUUID().toString());
            setUserName("keesh.zhang");
            setShowName("张春辉");
            setPasswd("Cc");
            setPhoto("/gallery/keesh-photo.jpg");
            setCreatedAt(new Date());
            setLastLogin(null);
            setLastUpdated(new Date());
            setUserRoles(new ArrayList<UserRoleEnum>() {{
                add(UserRoleEnum.ROLE_SUPER_ADMIN);
                add(UserRoleEnum.ROLE_USER_ADMIN);
                add(UserRoleEnum.ROLE_USER);
                add(UserRoleEnum.ROLE_USER_VIP);
            }});
        }});

        UserList.add(new UserEntity() {{
            setUserId(java.util.UUID.randomUUID().toString());
            setUserName("chunhui2001");
            setShowName("管理员");
            setPasswd("Cc");
            setCreatedAt(new Date());
            setLastLogin(null);
            setLastUpdated(new Date());
            setUserRoles(new ArrayList<UserRoleEnum>() {{
                add(UserRoleEnum.ROLE_USER);
            }});
        }});

        UserList.add(new UserEntity() {{
            setUserId(java.util.UUID.randomUUID().toString());
            setUserName("tongtong");
            setShowName("张彤彤");
            setPasswd("Cc");
            setCreatedAt(new Date());
            setGender(Gender.WOMEN);
            setLastLogin(null);
            setLastUpdated(new Date());
            setUserRoles(new ArrayList<UserRoleEnum>() {{
                add(UserRoleEnum.ROLE_USER);
                add(UserRoleEnum.ROLE_USER_VIP);
            }});
        }});

    }

    @Override
    public List<UserEntity> list() {

        return UserList.stream().map(user -> processUser(user)).collect(Collectors.toList());

    }



    @Override
    public UserEntity find(Object pk) {

        try {
            UserEntity userEntity = UserList.stream().filter(user -> {
                return user.getUserId().equals(pk);
            }).findFirst().get();

            return processUser(userEntity);
        } catch (NoSuchElementException e) {
            return null;
        }

    }


    @Override
    public UserEntity findByName(String userName) {

        try {
            return UserList.stream().filter(user -> {
                return user.getUserName().equals(userName);
            }).findFirst().get();
        } catch (Exception e) {
            return null;
        }

    }
}

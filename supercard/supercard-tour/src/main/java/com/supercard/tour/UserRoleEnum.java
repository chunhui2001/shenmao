package com.supercard.tour;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cc on 2017/7/20.
 */
public enum UserRoleEnum {

    /* ROLE_SUPER_ADMIN("超级管理员", 1),
    ROLE_USER_ADMIN("管理员", 2),
    ROLE_USER("普通用户", 3),
    ROLE_USER_VIP("VIP用户", 4); */

//    ROLE_SUPER_ADMIN("ROLE_SUPER_ADMIN_USER", 1),
//    ROLE_USER_ADMIN("ROLE_ADMIN_USER", 2),
//    ROLE_USER("ROLE_USER", 3),
//    ROLE_USER_VIP("ROLE_VIP_USER", 4);


    ROLE_SUPER_ADMIN("ROLE_SUPER_ADMIN_USER", "超级管理员"),
    ROLE_USER_ADMIN("ROLE_ADMIN_USER", "管理员"),
    ROLE_USER("ROLE_USER", "普通用户"),
    ROLE_USER_VIP("ROLE_VIP_USER", "VIP用户");


    private String name;
    private String text;


    private UserRoleEnum(String name, String text) {
        this.name = name;
        this.text = text;
    }


    public static String getName(String text) {
        for (UserRoleEnum c : UserRoleEnum.values()) {
            if (c.getText().equals(text)) {
                return c.name;
            }
        }
        return null;
    }

    public static Integer[] getRoleIds(List<UserRoleEnum> userRoles) {
        return userRoles.stream().map(UserRoleEnum::getText).toArray(Integer[]::new);
    }

    public static UserRoleEnum getRole(int text) {
        for (UserRoleEnum c : UserRoleEnum.values()) {
            if (c.getText().equals(text)) {
                return c;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setIndex(String text) {
        this.text = text;
    }

    @JsonValue
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("roleName", getName());
        map.put("roleText", getText());
        return map;
    }

}

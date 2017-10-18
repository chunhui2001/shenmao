package com.supercard.tour;



import java.util.Date;
import java.util.List;

/**
 * Created by cc on 2017/7/20.
 */
public class UserEntity {


    public enum Gender {
        MEN, WOMEN
    }

    private String userId;
    private String userName;
    private String showName;
    private String passwd;
    private List<UserRoleEnum> userRoles;
    private String photo;
    private Date createdAt;
    private Gender gender;
    private Date lastUpdated;
    private Date lastLogin;

    public UserEntity() {
        super();
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getShowName() {
        return this.showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

//    public String getPasswd() {
//        return passwd;
//    }

    public boolean comparePasswd(String passwd) {
        return this.passwd.equals(passwd);
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public List<UserRoleEnum> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRoleEnum> userRoles) {
        this.userRoles = userRoles;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}

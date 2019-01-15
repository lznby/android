package com.lznbys.android.entity;

/**
 * 登录成功返回实体类
 */
public class LoginResponseEntity {
    /**
     * 返回的用户基本信息
     */
    UserBaseInfoEntity userBaseInfoEntity;
    /**
     * 登录成功的token
     */
    String token;

    public LoginResponseEntity() {
    }

    public LoginResponseEntity(UserBaseInfoEntity userBaseInfoEntity, String token) {
        this.userBaseInfoEntity = userBaseInfoEntity;
        this.token = token;
    }

    public UserBaseInfoEntity getUserBaseInfoEntity() {
        return userBaseInfoEntity;
    }

    public void setUserBaseInfoEntity(UserBaseInfoEntity userBaseInfoEntity) {
        this.userBaseInfoEntity = userBaseInfoEntity;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

package com.lznbys.android.entity;

/**
 * 用户账号信息基本表实体类
 * app_user_account_info
 */
public class UserAccountEntity {
    /**
     * 用户id
     */
    private String userId;
    /**
     * 用户昵称
     */
    private String userNickName;
    /**
     * 账号密码
     */
    private String userPassword;
    /**
     * 登录cookies
     */
    private String userCookies;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserCookies() {
        return userCookies;
    }

    public void setUserCookies(String userCookies) {
        this.userCookies = userCookies;
    }
}

package com.lznbys.android.entity;

/**
 * 粉丝基本信息
 *
 * app_user_base_info
 * app_user_follower_info
 */
public class UserFollowerInfoEntity {
    /**
     * 粉丝ID
     */
    private int userId;
    /**
     * 关注记录编号
     */
    private String id;
    /**
     * 粉丝昵称
     */
    private String userNickName;
    /**
     * 粉丝个性签名
     */
    private String userMotto;
    /**
     * 粉丝头像Url地址
     */
    private String userHeaderUrl;
    /**
     * 粉丝性别
     */
    private String userSex;
    /**
     * 粉丝国家
     */
    private String userCounty;
    /**
     * 粉丝城市
     */
    private String userCity;
    /**
     * 是否已经关注
     * 0:未关注
     * 1:已关注
     */
    private int isFollow;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getUserMotto() {
        return userMotto;
    }

    public void setUserMotto(String userMotto) {
        this.userMotto = userMotto;
    }

    public String getUserHeaderUrl() {
        return userHeaderUrl;
    }

    public void setUserHeaderUrl(String userHeaderUrl) {
        this.userHeaderUrl = userHeaderUrl;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getUserCounty() {
        return userCounty;
    }

    public void setUserCounty(String userCounty) {
        this.userCounty = userCounty;
    }

    public String getUserCity() {
        return userCity;
    }

    public void setUserCity(String userCity) {
        this.userCity = userCity;
    }

    public int getIsFollow() {
        return isFollow;
    }

    public void setIsFollow(int isFollow) {
        this.isFollow = isFollow;
    }
}

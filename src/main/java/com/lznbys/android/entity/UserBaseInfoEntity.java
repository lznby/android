package com.lznbys.android.entity;

/**
 * 用户基本信息表实体类
 * app_user_base_info
 */
public class UserBaseInfoEntity {
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 用户昵称
     */
    private String userNickName;
    /**
     * 用户手机号
     */
    private String userTel;
    /**
     * 用户年龄
     */
    private int userAge;
    /**
     * 用户国籍
     */
    private String userCounty;
    /**
     * 用户所在城市
     */
    private String userCity;
    /**
     * 用户性别
     */
    private String userSex;
    /**
     * 用户毕业院校
     */
    private String userSchool;
    /**
     * 用户学历
     */
    private String userEdu;
    /**
     * 用户邮箱
     */
    private String userEmail;
    /**
     * 用户生日
     */
    private String userBirthday;

    /**
     * 用户头像url
     */
    private String userHeaderUrl;

    /**
     * 用户个性签名
     */
    private String userMotto;
    /**
     * 是否关注:非字符串
     */
    private boolean follow;
    /**
     * 用户关注数
     */
    private int followCount;
    /**
     * 用户粉丝数
     */
    private int followerCount;
    /**
     * 用户主题订阅数
     */
    private int themeCount;

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

    public String getUserTel() {
        return userTel;
    }

    public void setUserTel(String userTel) {
        this.userTel = userTel;
    }

    public int getUserAge() {
        return userAge;
    }

    public void setUserAge(int userAge) {
        this.userAge = userAge;
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

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getUserSchool() {
        return userSchool;
    }

    public void setUserSchool(String userSchool) {
        this.userSchool = userSchool;
    }

    public String getUserEdu() {
        return userEdu;
    }

    public void setUserEdu(String userEdu) {
        this.userEdu = userEdu;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserBirthday() {
        return userBirthday;
    }

    public void setUserBirthday(String userBirthday) {
        this.userBirthday = userBirthday;
    }

    public String getUserHeaderUrl() {
        return userHeaderUrl;
    }

    public void setUserHeaderUrl(String userHeaderUrl) {
        this.userHeaderUrl = userHeaderUrl;
    }

    public String getUserMotto() {
        return userMotto;
    }

    public void setUserMotto(String userMotto) {
        this.userMotto = userMotto;
    }

    public boolean isFollow() {
        return follow;
    }

    public void setFollow(boolean follow) {
        this.follow = follow;
    }

    public int getFollowCount() {
        return followCount;
    }

    public void setFollowCount(int followCount) {
        this.followCount = followCount;
    }

    public int getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(int followerCount) {
        this.followerCount = followerCount;
    }

    public int getThemeCount() {
        return themeCount;
    }

    public void setThemeCount(int themeCount) {
        this.themeCount = themeCount;
    }
}

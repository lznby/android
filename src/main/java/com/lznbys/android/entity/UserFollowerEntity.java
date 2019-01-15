package com.lznbys.android.entity;

/**
 * 用户关注、粉丝、黑名单表实体类
 *
 * 对应表格:app_user_follower_info
 */
public class UserFollowerEntity {
    /**
     * 记录Id
     */
    private String id;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 关注的用户的ID
     */
    private String followId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFollowId() {
        return followId;
    }

    public void setFollowId(String followId) {
        this.followId = followId;
    }

}

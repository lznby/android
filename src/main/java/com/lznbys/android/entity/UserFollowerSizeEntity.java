package com.lznbys.android.entity;

/**
 * 用户粉丝数、关注数
 * 对应表格:无
 * 使用说明:数据为对app_user_follower_info的统计
 */
public class UserFollowerSizeEntity {
    /**
     * 关注数
     */
    private int followSize;
    /**
     * 粉丝数
     */
    private int followerSize;

    public UserFollowerSizeEntity() {
    }

    public UserFollowerSizeEntity(int followSize, int followerSize) {
        this.followSize = followSize;
        this.followerSize = followerSize;
    }

    public int getFollowSize() {
        return followSize;
    }

    public void setFollowSize(int followSize) {
        this.followSize = followSize;
    }

    public int getFollowerSize() {
        return followerSize;
    }

    public void setFollowerSize(int followerSize) {
        this.followerSize = followerSize;
    }
}

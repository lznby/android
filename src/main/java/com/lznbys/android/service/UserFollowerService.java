package com.lznbys.android.service;

import com.lznbys.android.entity.UserFollowInfoEntity;
import com.lznbys.android.entity.UserFollowerEntity;
import com.lznbys.android.entity.UserFollowerInfoEntity;
import com.lznbys.android.entity.UserFollowerSizeEntity;

import java.util.List;

/**
 * 用户关注及粉丝信息
 */
public interface UserFollowerService {
    /**
     * 新增关注
     *
     * @param userFollowerEntity        新增关注记录
     * @return                          新增结果:true|false
     */
    boolean insertUserFollowerInfo(UserFollowerEntity userFollowerEntity);

    /**
     * 取消关注
     *
     * @param followId                  被取消关注者ID
     * @param userId                    发起取消关注者userId
     * @return                          取消结果:true|false
     */
    boolean deleteUserFollowerInfo(String userId, String followId);

    /**
     * 检测是否已经关注
     *
     * @param userId                    用户ID
     * @param followId                  关注ID
     * @return                          检测结果:true|false
     */
    boolean isFollower(String userId, String followId);

    /**
     * 通过id检测是否已经关注
     */
    boolean isFollowerById(String id);

//    /**
//     * 根据用户ID查询所有关注信息
//     *
//     * @param userId                    用户userId
//     * @return                          所有关注者基本信息
//     */
//    List<UserFollowInfoEntity> findAllUserFollowByUserId(String userId);

    /**
     * 根据用户ID查询所有关注信息
     *
     * @param userId                    用户userId
     * @param queryId                   发起查询者的ID
     * @return                          所有关注者基本信息
     */
    List<UserFollowInfoEntity> findAllUserFollowByUserId(String userId,String queryId);

//    /**
//     * 更具用户ID查询所有粉丝信息
//     * @param userId                    用户userId
//     * @return                          所有粉丝基本信息
//     */
//    List<UserFollowerInfoEntity> findAllUserFollowerByUserId(String userId);
    /**
     * 更具用户ID查询所有粉丝信息
     * @param userId                    用户userId
     * @return                          所有粉丝基本信息
     */
    List<UserFollowerInfoEntity> findAllUserFollowerByUserId(String userId, String queryId);

    /**
     * 根据用户ID查询该用户关注及粉丝数
     *
     * @param userId                    用户userId
     * @return                          粉丝数
     */
    UserFollowerSizeEntity getFollowerSizeById(String userId);
}

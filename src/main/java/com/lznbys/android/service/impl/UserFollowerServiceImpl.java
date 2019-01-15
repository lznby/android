package com.lznbys.android.service.impl;

import com.lznbys.android.dao.UserFollowerDao;
import com.lznbys.android.entity.UserFollowInfoEntity;
import com.lznbys.android.entity.UserFollowerEntity;
import com.lznbys.android.entity.UserFollowerInfoEntity;
import com.lznbys.android.entity.UserFollowerSizeEntity;
import com.lznbys.android.service.UserFollowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户关注及粉丝信息实现类
 */
@Service("userFollower")
public class UserFollowerServiceImpl implements UserFollowerService {

    private final UserFollowerDao userFollowerDao;

    /**
     * 依赖注入
     *
     * @param userFollowerDao           初始化 UserFollowerDao
     */
    @Autowired
    public UserFollowerServiceImpl(UserFollowerDao userFollowerDao) {
        this.userFollowerDao = userFollowerDao;
    }

    /**
     * 添加关注
     *
     * @param userFollowerEntity        新增关注记录
     * @return                          添加结果:true|false
     */
    @Override
    public boolean insertUserFollowerInfo(UserFollowerEntity userFollowerEntity) {
        return userFollowerDao.insertUserFollowerInfo(userFollowerEntity)>=1;
    }

    /**
     * 取消关注
     *
     * @param followId                  被取消关注者ID
     * @param userId                    发起取消关注者ID
     * @return                          取消结果:true|false
     */
    @Override
    public boolean deleteUserFollowerInfo(String userId, String followId) {
        return userFollowerDao.deleteUserFollowerInfo(userId, followId)>=1;
    }

    /**
     * 检测是否已经关注
     *
     * @param userId                    发起关注者ID
     * @param followId                  被关注者ID
     * @return
     */
    @Override
    public boolean isFollower(String userId, String followId) {
        return userFollowerDao.isFollower(userId,followId).size()>=1;
    }

    /**
     * 通过id监测是否已经关注,存在关注记录
     *
     * @param id
     * @return
     */
    @Override
    public boolean isFollowerById(String id) {
        return userFollowerDao.isFollowerById(id).size()>=1;
    }

//    /**
//     * 获取所有关注者信息
//     *
//     * @param userId                    用户ID
//     * @return                          查询的用户ID者所有关注者信息
//     */
//    @Override
//    public List<UserFollowInfoEntity> findAllUserFollowByUserId(String userId) {
//        return userFollowerDao.findAllUserFollowByUserId(userId);
//    }

    /**
     * 获取所有关注者信息
     *
     * @param userId                    用户ID
     * @param queryId                   发起查询者的ID
     * @return                          查询的用户ID者所有关注者信息
     */
    @Override
    public List<UserFollowInfoEntity> findAllUserFollowByUserId(String userId,String queryId) {
        return userFollowerDao.findAllUserFollowByUserId(userId,queryId);
    }

//    /**
//     * 获取所有粉丝的信息
//     *
//     * @param userId                    用户ID
//     * @return                          查询的用户ID者所有粉丝信息
//     */
//    @Override
//    public List<UserFollowerInfoEntity> findAllUserFollowerByUserId(String userId) {
//        return userFollowerDao.findAllUserFollowerByUserId(userId);
//    }

    /**
     * 获取所有粉丝的信息
     *
     * @param userId                    用户ID
     * @return                          查询的用户ID者所有粉丝信息
     */
    @Override
    public List<UserFollowerInfoEntity> findAllUserFollowerByUserId(String userId, String queryId) {
        return userFollowerDao.findAllUserFollowerByUserId(userId, queryId);
    }

    /**
     *
     * @param userId                    用户userId
     * @return                          用户粉丝及关注数
     */
    @Override
    public UserFollowerSizeEntity getFollowerSizeById(String userId) {
        return userFollowerDao.getUserFollowerSizeById(userId);
    }
}

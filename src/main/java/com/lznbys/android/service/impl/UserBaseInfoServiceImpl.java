package com.lznbys.android.service.impl;

import com.lznbys.android.dao.UserBaseInfoDao;
import com.lznbys.android.entity.UserBaseInfoEntity;
import com.lznbys.android.service.UserBaseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户账号信息实现类
 */
@Service("userBaseInfo")
public class UserBaseInfoServiceImpl implements UserBaseInfoService {

    private final UserBaseInfoDao userBaseInfoDao;

    /**
     * 依赖注入
     *
     * @param userBaseInfoDao               初始化 UserBaseInfoDao
     */
    @Autowired
    public UserBaseInfoServiceImpl(UserBaseInfoDao userBaseInfoDao) {
        this.userBaseInfoDao = userBaseInfoDao;
    }

    /**
     * 新增用户基本信息
     * @param userBaseInfoEntity            用户基本信息实体类
     * @return                              true:新增成功           false:新增失败
     */
    @Override
    public boolean insertUserBaseInfo(UserBaseInfoEntity userBaseInfoEntity) {
        return userBaseInfoDao.insertUserBaseInfo(userBaseInfoEntity)>=1;
    }

    /**
     * 修改用户基本信息
     * @param userBaseInfoEntity            修改后的用户基本信息
     * @return                              返回修改后的用户信息
     */
    @Override
    public UserBaseInfoEntity updateUserBaseInfo(UserBaseInfoEntity userBaseInfoEntity,String userCookies) {
        return userBaseInfoDao.updateUserBaseInfo(userBaseInfoEntity,userCookies)>=1?userBaseInfoEntity:null;
    }

    /**
     * 1.查询 cookies 是否过期
     * 2.根据 cookies 查询用户基本信息
     *
     * @param userCookies                   用户 cookies
     * @return                              成功:用户基本信息
     */
    @Override
    public UserBaseInfoEntity findUserInfoByCookies(String userCookies) {
        return userBaseInfoDao.findUserInfoByCookies(userCookies);
    }

    /**
     * 根据昵称查询用户基本信息
     *
     * @param userId                        用户昵称
     * @return                              成功:用户基本信息
     */
    public UserBaseInfoEntity findUserInfoByUserId(String userId) {
        return userBaseInfoDao.findUserInfoByUserId(userId);
    }

    /**
     * 获取所有用户基本信息
     *
     * @return 所有用户基本信息
     */
    @Override
    public List<UserBaseInfoEntity> findAllUserBaseInfo() {
        return userBaseInfoDao.findAllUserBaseInfo();
    }

    /**
     * 修改用户统计数据
     *
     * @param userBaseInfoEntity    用户统计数据
     * @return  返回修改是否成功
     */
    @Override
    public boolean updateUserCountInfo(UserBaseInfoEntity userBaseInfoEntity) {
        int isUpdate = userBaseInfoDao.updateUserCountInfo(userBaseInfoEntity);
        return (isUpdate > 0);
    }
}

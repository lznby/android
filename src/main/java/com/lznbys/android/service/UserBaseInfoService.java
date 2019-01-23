package com.lznbys.android.service;

import com.lznbys.android.entity.UserBaseInfoEntity;

import java.util.List;

/**
 * 用户基本信息
 */
public interface UserBaseInfoService {

    /**
     * 新增用户基本信息
     *
     * @param userBaseInfoEntity            用户基本信息实体类
     * @return                              true:新增成功           false:新增失败
     */
    boolean insertUserBaseInfo(UserBaseInfoEntity userBaseInfoEntity);

    /**
     * 修改用户基本信息
     *
     * @param userBaseInfoEntity            修改后的用户基本信息
     * @return                              返回修改成功后的用户信息
     */
    UserBaseInfoEntity updateUserBaseInfo(UserBaseInfoEntity userBaseInfoEntity,String userCookies);

    /**
     * 1.查询 cookies 是否过期
     * 2.根据 cookies 查询用户基本信息
     *
     * @param userCookies                   用户 cookies
     * @return                              成功:用户基本信息
     */
    UserBaseInfoEntity findUserInfoByCookies(String userCookies);

    /**
     * 根据userId查询用户基本信息
     *
     * @param userId                  用户昵称
     * @return                        成功:用户基本信息
     */
    UserBaseInfoEntity findUserInfoByUserId(String userId);


    /**
     * 获取所有用户基本信息
     *
     * @return  所有用户基本信息
     */
    List<UserBaseInfoEntity> findAllUserBaseInfo();

    /**
     * 修改用户统计数据
     *
     * @param userBaseInfoEntity    用户统计数据
     * @return  返回修改结果
     */
    boolean updateUserCountInfo(UserBaseInfoEntity userBaseInfoEntity);
}

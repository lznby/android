package com.lznbys.android.service;

import com.lznbys.android.entity.UserBaseInfoEntity;

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

//    /**
//     * 查找用户基本信息（根据cookies和userName）
//     *
//     * @param userNickName                  用户昵称
//     * @param userCookies                   用户cookies
//     * @return                              返回用户信息:使用昵称查询返回公开信息;使用cookies查询返回所用信息
//     */
//    UserBaseInfoEntity findUserBaseInfo(String userNickName, String userCookies);

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
     * @return                              成功:用户基本信息
     */
    UserBaseInfoEntity findUserInfoByUserId(String userId);
}

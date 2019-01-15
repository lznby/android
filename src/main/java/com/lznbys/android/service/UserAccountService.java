package com.lznbys.android.service;

import com.lznbys.android.entity.UserAccountEntity;

/**
 * 用户登录信息
 */
public interface UserAccountService {

    /**
     * 创建新用户
     * @param userAccountEntity     账号基本信息类对象
     * @return                      true:创建成功       false:创建失败
     */
    boolean createUserAccount(UserAccountEntity userAccountEntity);

    /**
     * 检测昵称是否被占用
     * @param userNickName          待检测的用户昵称
     * @return                      返回查询到的用户信息
     */
    UserAccountEntity checkUserNickName(String userNickName);

    /**
     * 修改用户密码
     * @param newUserCookies        新cookies,通过服务器自动生成
     * @param newUserPassword       新密码
     * @param userPassword          用户旧密码
     * @param userCookies           用户旧cookies
     * @return                      true:密码修改成功    false:密码修改失败
     */
    boolean updateUserPassword(String newUserCookies,String newUserPassword,String userPassword,String userCookies);

    /**
     * 修改用户昵称
     * @param newUserNickName       用户新昵称
     * @param userCookies           用户cookies
     * @return                      true:昵称修改成功     false:昵称修改失败
     */
    boolean updateUserNickName(String newUserNickName,String userCookies);

    /**
     * 用户登录（修改cookies）
     * @param userCookies           新cookies
     * @param userNickName          用户昵称
     * @param userPassword          用户密码
     * @return                      true:用户登录成功     false:用户登录失败
     */
    boolean userLogin(String userCookies, String userNickName, String userPassword);

    /**
     * 用户登出（修改cookies）
     * @param userCookies           旧cookies
     * @param newCookies            新cookies
     * @return                      true:用户登出成功     false:用户登出失败
     */
    boolean userLoginOut(String userCookies, String newCookies);

}

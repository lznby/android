package com.lznbys.android.service.impl;

import com.lznbys.android.dao.UserAccountDao;
import com.lznbys.android.entity.UserAccountEntity;
import com.lznbys.android.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户基本信息实现类
 */
@Service("userAccount")
public class UserAccountServiceImpl implements UserAccountService {

    private final UserAccountDao userAccountDao;

    /**
     * 依赖注入
     * @param userAccountDao        初始化 UserAccountDao
     */
    @Autowired
    public UserAccountServiceImpl(UserAccountDao userAccountDao) {
        this.userAccountDao = userAccountDao;
    }

    /**
     * 注册
     * @param userAccountEntity     账号基本信息类对象
     * @return                      true:注册成功           false:注册失败
     */
    @Override
    public boolean createUserAccount(UserAccountEntity userAccountEntity) {
        if (checkUserNickName(userAccountEntity.getUserNickName())!=null) {
            return false;
        } else {
            return userAccountDao.insertUserAccount(userAccountEntity) != null;
        }
    }

    /**
     * 检测用户昵称
     * @param userNickName          待检测的用户昵称
     * @return                      true:昵称已被占用       false:昵称可用
     */
    @Override
    public UserAccountEntity checkUserNickName(String userNickName) {
        return userAccountDao.checkNickName(userNickName);
    }

    /**
     * 修改密码
     * @param newUserCookies        新cookies,通过服务器自动生成
     * @param newUserPassword       新密码
     * @param userPassword          用户旧密码
     * @param userCookies           用户旧cookies
     * @return                      true:密码修改成功       false:密码修改失败
     */
    @Override
    public boolean updateUserPassword(String newUserCookies, String newUserPassword, String userPassword, String userCookies) {
        return userAccountDao.updateUserPassword(newUserCookies,newUserPassword,userPassword,userCookies)>=1;
    }

    /**
     * 修改昵称
     * @param newUserNickName       用户新昵称
     * @param userCookies           用户cookies
     * @return                      true:昵称修改成功       false:昵称修改失败
     */
    @Override
    public boolean updateUserNickName(String newUserNickName, String userCookies) {
        return userAccountDao.updateUserNickName(newUserNickName, userCookies)>=1;
    }

    /**
     * 登录
     * @param userCookies           新cookies
     * @param userNickName          用户昵称
     * @param userPassword          用户密码
     * @return                      true:登录成功           false:登录失败
     */
    @Override
    public boolean userLogin(String userCookies, String userNickName, String userPassword) {
        return userAccountDao.updateUserAccountInfo(userCookies,userNickName,userPassword)>=1;
    }

    /**
     * 登出
     * @param userCookies           旧cookies
     * @param newCookies            新Cookies
     * @return                      true:登出成功           false:登出失败
     */
    @Override
    public boolean userLoginOut(String userCookies, String newCookies) {
        return userAccountDao.updateUserCookies(userCookies,newCookies)>=1;
    }
}

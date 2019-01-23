package com.lznbys.android.controller;

import com.lznbys.android.base.BaseResponseEntity;
import com.lznbys.android.base.ResponseCode;
import com.lznbys.android.entity.LoginResponseEntity;
import com.lznbys.android.entity.UserAccountEntity;
import com.lznbys.android.entity.UserBaseInfoEntity;
import com.lznbys.android.service.FilePathService;
import com.lznbys.android.service.UserAccountService;
import com.lznbys.android.service.UserBaseInfoService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * 处理用户账号信息 Request 并返回 ResponseEntity
 */

@RestController
@RequestMapping("/userAccount")
public class UserAccountController {

    @Autowired
    private UserAccountService userAccountService;
    @Autowired
    private UserBaseInfoService userBaseInfoService;
    @Autowired
    private FilePathService filePathService;

    /**
     * 注册接口
     *
     * @param userAccountEntity             请求参数
     * @return                              请求结果:成功|失败
     */
    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public BaseResponseEntity register(@ModelAttribute UserAccountEntity userAccountEntity) {

        if (userAccountEntity.getUserNickName() == null || userAccountEntity.getUserPassword() == null) {
            return new BaseResponseEntity(ResponseCode.NICKNAME_IS_NULL, ResponseCode.FAIL);
        } else {
            boolean created = userAccountService.createUserAccount(userAccountEntity);
            if (created) {
                //1.用于查询注册账号的账号userId
                UserAccountEntity userAccountSearcher = userAccountService.checkUserNickName(userAccountEntity.getUserNickName());
                //2.创建用户基本信息对象,用于初始化该用户基本信息
                UserBaseInfoEntity userBaseInfoEntity = new UserBaseInfoEntity();
                //3.设置用户基本信息表中的userId和userNikeName
                userBaseInfoEntity.setUserId(userAccountSearcher.getUserId());
                userBaseInfoEntity.setUserNickName(userAccountSearcher.getUserNickName());
                userBaseInfoEntity.setUserBirthday("1970-1-1");
                userBaseInfoEntity.setUserCounty("银河帝国");
                userBaseInfoEntity.setUserCity("地球村");
                //4.初始化该用户的基本信息
                userBaseInfoService.insertUserBaseInfo(userBaseInfoEntity);
                return new BaseResponseEntity<>(ResponseCode.ACCOUNT_CREATE_SUCCESS, ResponseCode.SUCCESS);
            } else {
                return new BaseResponseEntity(ResponseCode.ACCOUNT_CREATE_FAIL, ResponseCode.FAIL);
            }
        }

    }

    /**
     * 登录接口
     *
     * @param userNickName                  用户昵称
     * @param userPassword                  用户密码
     * @return                              成功:token及用户基本信息
     */
    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public BaseResponseEntity login(@Param("userNickName") String userNickName, @Param("userPassword") String userPassword) {

        System.out.print("userNickName:"+userNickName+"  "+"userPassword:"+userPassword);

        //1.系统生成 uuid 作为该用户的 cookies
        String userCookies = UUID.randomUUID().toString();

        //2.检测用户名和密码正确性
        boolean isSuccess = userAccountService.userLogin(userCookies, userNickName, userPassword);

        if (isSuccess) {
            //3.查询该用户的基本信息用于返回,同时返回token
            UserBaseInfoEntity userBaseInfoEntity = userBaseInfoService.findUserInfoByCookies(userCookies);
            LoginResponseEntity loginResponseEntity = new LoginResponseEntity(userBaseInfoEntity, userCookies);
            return new BaseResponseEntity<>(ResponseCode.REQUEST_SUCCESS_MSG, ResponseCode.SUCCESS, loginResponseEntity);
        } else {
            return new BaseResponseEntity(ResponseCode.REQUEST_FAIL_MSG, ResponseCode.FAIL);
        }
    }

    /**
     * 退出登录接口
     *
     * @param userCookies
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/loginOut")
    public BaseResponseEntity loginOut(@RequestHeader("userCookies") String userCookies) {
        //1.系统生成 uuid 作为该用户的 cookies
        String newCookies = UUID.randomUUID().toString();
        //2.查询Cookies是否有效
        UserBaseInfoEntity userBaseInfoEntity = userBaseInfoService.findUserInfoByCookies(userCookies);
        //3.更新Cookies
        boolean isLoginOut = userAccountService.userLoginOut(userCookies,newCookies);
        if (userBaseInfoEntity !=null) {
            if (isLoginOut) {
                return new BaseResponseEntity(ResponseCode.REQUEST_SUCCESS_MSG,ResponseCode.SUCCESS);
            } else {
                return new BaseResponseEntity(ResponseCode.REQUEST_FAIL_MSG,ResponseCode.FAIL);
            }
        } else {
            return new BaseResponseEntity(ResponseCode.COOKIES_OUT_TIME,ResponseCode.UN_LOGIN);
        }

    }

    /**
     * 修改昵称接口
     *
     * @param newUserNickName               新的用户昵称
     * @param userCookies                   用户cookies
     * @return                              成功:修改后的用户基本信息
     */
    @RequestMapping(method = RequestMethod.POST, value = "/updateUserNickName")
    public BaseResponseEntity updateUserNickName(@RequestHeader("userCookies") String userCookies, @Param("newUserNickName") String newUserNickName) {

        //1.判空处理,后续应使用正则过滤
        if ("".equals(newUserNickName.trim())) {
            return new BaseResponseEntity(ResponseCode.NAME_IS_NULL, ResponseCode.FAIL);
        }

        //2.修改用户昵称
        boolean isSuccess = userAccountService.updateUserNickName(newUserNickName, userCookies);

        if (isSuccess) {
            //3.查询修改后的用户基本信息
            UserBaseInfoEntity userBaseInfoEntity = userBaseInfoService.findUserInfoByCookies(userCookies);
            //4.返回修改后的用户基本信息
            return new BaseResponseEntity<>(ResponseCode.REQUEST_SUCCESS_MSG, ResponseCode.SUCCESS, userBaseInfoEntity);
        } else {
            return new BaseResponseEntity(ResponseCode.REQUEST_FAIL_MSG, ResponseCode.FAIL);
        }
    }

    /**
     * 修改密码接口
     *
     * @param userCookies                   用户登陆的cookies
     * @param userPassword                  旧密码
     * @param newUserPassword               新密码
     * @param rUserPassword                 确认密码
     * @return                              成功:请求结果码为0,要求用户重新登录
     */
    @RequestMapping(method = RequestMethod.POST, value = "/updateUserPassword")
    public BaseResponseEntity updateUserPassword(@RequestHeader("userCookies") String userCookies,
                                                 @Param("userPassword") String userPassword,
                                                 @Param("newUserPassword") String newUserPassword,
                                                 @Param("rUserPassword") String rUserPassword) {

        //1.对修改后的密码进行校验:判空处理;两次密码是否相同;
        if (StringUtils.isEmpty(newUserPassword) || StringUtils.isEmpty(rUserPassword)) {
            return new BaseResponseEntity(ResponseCode.PASSWORD_IS_NULL, ResponseCode.FAIL);
        } else {
            if (!newUserPassword.equals(rUserPassword)) {
                return new BaseResponseEntity(ResponseCode.PASSWORD_IS_DIFFERENT, ResponseCode.FAIL);
            }
        }

        //2.系统生成新的 uuid 作为用户的 cookies
        String newUserCookies = UUID.randomUUID().toString();

        //3.执行修改密码
        boolean isSuccess = userAccountService.updateUserPassword(newUserCookies, newUserPassword, userPassword, userCookies);
        if (isSuccess) {
            //4.密码修改成功:返回用户未登陆,让用户重新登录
            return new BaseResponseEntity(ResponseCode.REQUEST_SUCCESS_MSG, ResponseCode.UN_LOGIN);
        } else {
            //5.密码修改失败:返回错误原因
            return new BaseResponseEntity(ResponseCode.PASSWORD_ERROR, ResponseCode.FAIL);
        }
    }


}

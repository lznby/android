package com.lznbys.android.controller;

import com.lznbys.android.base.BaseResponseEntity;
import com.lznbys.android.base.ResponseCode;
import com.lznbys.android.entity.*;
import com.lznbys.android.service.ThemeService;
import com.lznbys.android.service.UserBaseInfoService;
import com.lznbys.android.service.UserFollowerService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * 处理粉丝及关注信息 Request 并返回 ResponseEntity
 */
@RestController
@RequestMapping("/userFollower")
public class UserFollowerController {

    @Autowired
    private UserFollowerService userFollowerService;
    @Autowired
    private UserBaseInfoService userBaseInfoService;
    @Autowired
    private ThemeService themeService;

    /**
     * 添加关注接口
     *
     * @param userCookies           用户cookies
     * @param userId                被关注者userId
     * @return                      请求结果
     */
    @RequestMapping(method = RequestMethod.POST, value = "follow")
    public BaseResponseEntity follow(@RequestHeader("userCookies") String userCookies,
                                     @Param("userId") String userId) {
        //1.根据userCookies检测账号是否合法
        UserBaseInfoEntity userBaseInfoSearch = userBaseInfoService.findUserInfoByCookies(userCookies);
        //2.根据userId检测关注对象是否存在
        UserBaseInfoEntity userBaseInfoFollow = userBaseInfoService.findUserInfoByUserId(userId);
        if (userBaseInfoSearch != null) {
            //3.生产UUID作为添加关注记录的id
            String id = UUID.randomUUID().toString();
            if (userBaseInfoFollow != null) {
                //4.监测是否已经关注
                boolean isFollower = userFollowerService.isFollower(userBaseInfoSearch.getUserId(),userId);
                if (isFollower) {
                    return new BaseResponseEntity(ResponseCode.ALREADY_FOLLOW,ResponseCode.FAIL);
                }
                UserFollowerEntity userFollowerInsert = new UserFollowerEntity();
                userFollowerInsert.setId(id);
                userFollowerInsert.setUserId(userBaseInfoSearch.getUserId());
                userFollowerInsert.setFollowId(userId);
                boolean isInsert = userFollowerService.insertUserFollowerInfo(userFollowerInsert);

                // 更新用户关注数
                userBaseInfoSearch.setFollowCount(userBaseInfoSearch.getFollowCount()+1);
                userBaseInfoService.updateUserCountInfo(userBaseInfoSearch);
                // 更新被关注者粉丝数
                userBaseInfoFollow.setFollowerCount(userBaseInfoFollow.getFollowerCount()+1);
                userBaseInfoService.updateUserCountInfo(userBaseInfoFollow);

                if (isInsert) {
                    return new BaseResponseEntity(ResponseCode.REQUEST_SUCCESS_MSG,ResponseCode.SUCCESS);
                } else {
                    return new BaseResponseEntity(ResponseCode.REQUEST_FAIL_MSG,ResponseCode.FAIL);
                }
            } else {
                return new BaseResponseEntity(ResponseCode.USER_NOT_FIND,ResponseCode.FAIL);
            }
        } else {
            return new BaseResponseEntity(ResponseCode.COOKIES_OUT_TIME,ResponseCode.UN_LOGIN);
        }
    }

    /**
     * 取消关注接口
     *
     * @param userCookies           用户cookie
     * @param userId                被取消关注者ID
     * @return                      请求结果
     */
    @RequestMapping(method = RequestMethod.POST, value = "unFollower")
    public BaseResponseEntity unFollower(@RequestHeader("userCookies")String userCookies,
                                         @Param("userId") String userId,
                                         @Param("followId") String followId) {
        //1.根据userCookies检测账号是否合法
        UserBaseInfoEntity userBaseInfoSearch = userBaseInfoService.findUserInfoByCookies(userCookies);
        //2.根据userId检测关注对象是否存在
        UserBaseInfoEntity userBaseInfoFollow = userBaseInfoService.findUserInfoByUserId(userId);
        //3.根据id检测关注记录是否存在
        boolean isFollower = userFollowerService.isFollower(userId,followId);
        if (userBaseInfoSearch!=null) {
            if (isFollower) {
                boolean isDelete = userFollowerService.deleteUserFollowerInfo(userBaseInfoSearch.getUserId(),followId);

                // 更新用户关注数
                userBaseInfoSearch.setFollowCount(userBaseInfoSearch.getFollowCount()-1);
                userBaseInfoService.updateUserCountInfo(userBaseInfoSearch);
                // 更新被关注者粉丝数
                userBaseInfoFollow.setFollowerCount(userBaseInfoFollow.getFollowerCount()-1);
                userBaseInfoService.updateUserCountInfo(userBaseInfoFollow);

                if (isDelete) {
                    return new BaseResponseEntity(ResponseCode.REQUEST_SUCCESS_MSG,ResponseCode.SUCCESS);
                } else {
                    return new BaseResponseEntity(ResponseCode.REQUEST_FAIL_MSG,ResponseCode.FAIL);
                }
            } else {
                return new BaseResponseEntity(ResponseCode.NOT_FOLLOWER,ResponseCode.FAIL);
            }
        } else {
            return new BaseResponseEntity(ResponseCode.COOKIES_OUT_TIME,ResponseCode.UN_LOGIN);
        }
    }

    /**
     * 获取关注列表信息
     *
     * @param userId                查询用户的userId
     * @return                      查询结果
     */
    @RequestMapping(method = RequestMethod.GET, value = "getFollows")
    public BaseResponseEntity<List<UserFollowInfoEntity>> getFollow(@RequestHeader("userCookies") String userCookies,
                                                                    @Param("userId") String userId,
                                                                    @Param("queryId") String queryId) {
        List<UserFollowInfoEntity> userFollowSearch = userFollowerService.findAllUserFollowByUserId(userId,queryId);
        if (userFollowSearch != null) {
            return new BaseResponseEntity<>(ResponseCode.REQUEST_SUCCESS_MSG,ResponseCode.SUCCESS,userFollowSearch);
        } else {
            return new BaseResponseEntity<>(ResponseCode.REQUEST_FAIL_MSG,ResponseCode.FAIL);
        }
    }

    /**
     * 获取粉丝列表信息
     *
     * @param userId                查询用户的userId
     * @return                      查询结果
     */
    @RequestMapping(method = RequestMethod.GET, value = "getFollowers")
    public BaseResponseEntity<List<UserFollowerInfoEntity>> getFollowers(@RequestHeader("userCookies") String userCookies,
                                                                         @Param("userId") String userId,
                                                                         @Param("queryId") String queryId) {
        List<UserFollowerInfoEntity> userFollowerSearch = userFollowerService.findAllUserFollowerByUserId(userId,queryId);
        if (userFollowerSearch != null) {
            return new BaseResponseEntity<>(ResponseCode.REQUEST_SUCCESS_MSG,ResponseCode.SUCCESS,userFollowerSearch);
        } else {
            return new BaseResponseEntity<>(ResponseCode.REQUEST_FAIL_MSG,ResponseCode.FAIL);
        }
    }

    /**
     * 通过用户userId查询用户粉丝、关注数、及主题关注数
     */
    @RequestMapping(method = RequestMethod.GET, value = "/getUserFollowerSize")
    public BaseResponseEntity<UserFollowerSizeEntity> getUserFollowerSize(@Param("userId") String userId) {
        UserFollowerSizeEntity userFollowerSizeSearch = userFollowerService.getFollowerSizeById(userId);
        if (userFollowerSizeSearch!=null) {
            List<ThemeSubEntity> themeSubEntities = themeService.findAllThemeSubByUserId(userId);
            userFollowerSizeSearch.setFollowThemeSize(themeSubEntities.size());
            return new BaseResponseEntity<>(ResponseCode.REQUEST_SUCCESS_MSG,ResponseCode.SUCCESS,userFollowerSizeSearch);
        } else {
            return new BaseResponseEntity<>(ResponseCode.REQUEST_FAIL_MSG,ResponseCode.FAIL);
        }
    }

}

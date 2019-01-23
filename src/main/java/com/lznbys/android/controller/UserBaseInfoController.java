package com.lznbys.android.controller;

import com.lznbys.android.base.BaseResponseEntity;
import com.lznbys.android.base.ResponseCode;
import com.lznbys.android.entity.*;
import com.lznbys.android.service.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 处理用户基本信息 Request 并返回 ResponseEntity
 */

@RestController
@RequestMapping("/userBaseInfo")
public class UserBaseInfoController {

    @Autowired
    private UserBaseInfoService userBaseInfoService;
    @Autowired
    private UserFollowerService userFollowerService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private FilePathService filePathService;
    @Autowired
    private ArticleThemeService articleThemeService;

    /**
     * 修改用户基本信息
     *
     * @param userCookies        用户cookies
     * @param userBaseInfoEntity 用户基本信息
     * @return 返回修改后的用户信息
     */
    @RequestMapping(method = RequestMethod.POST, value = "/updateUserBaseInfo")
    public BaseResponseEntity updateUserBaseInfo(@RequestHeader("userCookies") String userCookies,
                                                 @ModelAttribute UserBaseInfoEntity userBaseInfoEntity) {

        //1.查询修改账号的基本信息
        UserBaseInfoEntity userBaseInfoSearch = userBaseInfoService.findUserInfoByCookies(userCookies);

        if (userBaseInfoSearch != null) {
            //2.设置不能修改的 userId
            userBaseInfoEntity.setUserId(userBaseInfoSearch.getUserId());

            //3.修改后的用户信息
            UserBaseInfoEntity userBaseInfoResponse = userBaseInfoService.updateUserBaseInfo(userBaseInfoEntity, userCookies);

            //4.返回修改后的基本信息
            return new BaseResponseEntity<>(ResponseCode.REQUEST_SUCCESS_MSG, ResponseCode.SUCCESS, userBaseInfoResponse);

        } else {
            return new BaseResponseEntity(ResponseCode.REQUEST_FAIL_MSG, ResponseCode.FAIL);
        }
    }

    /**
     * 通过 cookies 获取用户信息
     *
     * @param userCookies 用户 cookies
     * @return 用户基本信息
     */
    @RequestMapping(method = RequestMethod.GET, value = "/getMineBaseInfo")
    public BaseResponseEntity getMineBaseInfo(@RequestHeader("userCookies") String userCookies) {
        //1.查询用户信息
        UserBaseInfoEntity userBaseInfoEntity = userBaseInfoService.findUserInfoByCookies(userCookies);
        //2.判断查询结果
        if (userBaseInfoEntity != null) {
            return new BaseResponseEntity<>(ResponseCode.REQUEST_SUCCESS_MSG, ResponseCode.SUCCESS, userBaseInfoEntity);
        } else {
            return new BaseResponseEntity(ResponseCode.REQUEST_FAIL_MSG, ResponseCode.FAIL);
        }
    }

    /**
     * 通过用户userId查询用户基本信息
     *
     * @param userId 用户userId
     * @return 用户基本信息
     */
    @RequestMapping(method = RequestMethod.GET, value = "/getUserBaseInfo")
    public BaseResponseEntity getUserBaseInfo(@Param("userId") String userId) {
        //1.查询用户信息
        UserBaseInfoEntity userBaseInfoEntity = userBaseInfoService.
                findUserInfoByUserId(userId);

        //2.判断查询结果
        if (userBaseInfoEntity != null) {

            //3.将私有信息清空(暂时清空联系方式)
            userBaseInfoEntity.setUserTel("");
            userBaseInfoEntity.setUserEmail("");

            return new BaseResponseEntity<>(ResponseCode.REQUEST_SUCCESS_MSG, ResponseCode.SUCCESS, userBaseInfoEntity);
        } else {
            return new BaseResponseEntity(ResponseCode.REQUEST_FAIL_MSG, ResponseCode.FAIL);
        }
    }


    /**
     * 获取个人主页信息
     *
     * @param userCookies 可省略
     * @param userId      必选
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/getPersonalHomePage")
    public BaseResponseEntity getPersonalHomePage(@RequestHeader("userCookies") String userCookies, @Param("userId") String userId) {
        PersonalHomePageEntity personalHomePageEntity = new PersonalHomePageEntity();
        //1.查询用户信息
        UserBaseInfoEntity userBaseInfoEntity = userBaseInfoService.findUserInfoByUserId(userId);
        UserBaseInfoEntity userBaseInfoByCookies = userBaseInfoService.findUserInfoByCookies(userCookies);
        //2.查询是否为本人
        if (userBaseInfoEntity != null) {
            boolean isSelf = false;
            if (userBaseInfoByCookies != null) {
                if (userBaseInfoEntity.getUserId().equals(userBaseInfoByCookies.getUserId())) {
                    isSelf = true;
                } else {
                    //非本人清空通讯信息
                    userBaseInfoEntity.setUserTel("");
                    userBaseInfoEntity.setUserEmail("");
                }
            }
            personalHomePageEntity.setSelf(isSelf);
            personalHomePageEntity.setUserBaseInfoEntity(userBaseInfoEntity);
            //3.查询资讯信息
            List<ArticleEntity> articleEntities = articleService.findArticleByUserId(userId);
            List<ArticleAllInfoEntity> articleAllInfoEntities = new ArrayList<>();
            for (ArticleEntity single : articleEntities) {
                List<FilePathEntity> filePathEntities = filePathService.findAllFilePathByFileAttribution(single.getFileAttribution());
                List<ThemeEntity> themeEntities = articleThemeService.findThemeByFileAttribution(single.getFileAttribution());
                ArticleAllInfoEntity articleAllInfoEntity = new ArticleAllInfoEntity();
                articleAllInfoEntity.setUserBaseInfoEntity(userBaseInfoEntity);
                articleAllInfoEntity.setFilePathEntities(filePathEntities);
                articleAllInfoEntity.setArticleEntity(single);
                articleAllInfoEntity.setThemeEntities(themeEntities);
                // 判断查询者是否收藏
                ArticleSubEntity isSub = articleService.checkArticleSub(single.getFileAttribution(), userBaseInfoByCookies != null ? userBaseInfoByCookies.getUserId() : "");
                if (isSub != null) {
                    articleAllInfoEntity.setLove(true);
                }
                articleAllInfoEntities.add(articleAllInfoEntity);
            }
            personalHomePageEntity.setArticleAllInfoEntities(articleAllInfoEntities);
            //4.查询用户关注与订阅数量
            UserFollowerSizeEntity userFollowerSizeEntity = userFollowerService.getFollowerSizeById(userId);
            personalHomePageEntity.setUserFollowerSizeEntity(userFollowerSizeEntity);
            return new BaseResponseEntity<>(ResponseCode.REQUEST_SUCCESS_MSG, ResponseCode.SUCCESS, personalHomePageEntity);
        } else {
            return new BaseResponseEntity(ResponseCode.REQUEST_FAIL_MSG, ResponseCode.FAIL);
        }
    }

    /**
     * 查询所有人基础信息,并返回是否关注 (发现-用户)
     *
     * @param userCookies   用户Cookies
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/getAllUserBaseInfo")
    public BaseResponseEntity getAllUserBaseInfo(@RequestHeader("userCookies") String userCookies) {
        //1.查询用户信息
        UserBaseInfoEntity userBaseInfoEntity = userBaseInfoService.findUserInfoByCookies(userCookies);
        List<UserBaseInfoEntity> userBaseInfoEntities = userBaseInfoService.findAllUserBaseInfo();
        if (userBaseInfoEntity != null) {
            for (int i = 0; i < userBaseInfoEntities.size(); i++) {
                boolean isFollow = userFollowerService.isFollower(userBaseInfoEntity.getUserId(),userBaseInfoEntities.get(i).getUserId());
                if (isFollow) {
                    userBaseInfoEntities.get(i).setFollow(true);
                }
            }
            return new BaseResponseEntity<>(ResponseCode.REQUEST_SUCCESS_MSG, ResponseCode.SUCCESS, userBaseInfoEntities);
        } else {
            return new BaseResponseEntity<>(ResponseCode.REQUEST_SUCCESS_MSG, ResponseCode.SUCCESS, userBaseInfoEntities);
        }
    }

}

package com.lznbys.android.controller;

import com.lznbys.android.base.BaseResponseEntity;
import com.lznbys.android.base.ResponseCode;
import com.lznbys.android.entity.ThemeEntity;
import com.lznbys.android.entity.ThemeSubEntity;
import com.lznbys.android.entity.UserBaseInfoEntity;
import com.lznbys.android.service.ThemeService;
import com.lznbys.android.service.UserBaseInfoService;
import com.lznbys.android.utlis.FileUtils;
import com.lznbys.android.utlis.TimeUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 主题信息
 */

@RestController
@RequestMapping("/theme")
public class ThemeController {

    @Autowired
    private UserBaseInfoService userBaseInfoService;
    @Autowired
    private ThemeService themeService;

    /**
     * 新增主题信息
     *
     * @param userCookies       用户Cookies
     * @param themeName         主题名称
     * @param themeNote         主题简介
     * @param themeImage        背景图
     * @param themeHeaderImage  主题头像
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/createTheme")
    public BaseResponseEntity createTheme(@RequestHeader("userCookies") String userCookies,
                                          @Param("themeName") String themeName,
                                          @Param("themeNote") String themeNote,
                                          @Param("themeImage") String themeImage,
                                          @Param("themeHeaderImage") String themeHeaderImage) {
        UserBaseInfoEntity userBaseInfoEntity = userBaseInfoService.findUserInfoByCookies(userCookies);
        // 创建时间
        String uploadTime = TimeUtils.getNowTime();
        String fileAttribution = UUID.randomUUID().toString();
        String themeId = UUID.randomUUID().toString();
        if (userBaseInfoEntity != null) {
            ThemeEntity checkCreate = themeService.findThemeByName(themeName);
            if (checkCreate == null) {
                ThemeEntity themeEntity = new ThemeEntity();
                themeEntity.setTime(uploadTime);
                themeEntity.setThemeHeaderImage(fileAttribution);
                themeEntity.setSubCount(0);
                themeEntity.setThemeId(themeId);
                themeEntity.setThemeImage(FileUtils.getOssFilePath(themeImage));
                themeEntity.setThemeHeaderImage(FileUtils.getOssFilePath(themeHeaderImage));
                themeEntity.setThemeName(themeName);
                themeEntity.setThemeNote(themeNote);
                boolean isInsert = themeService.createTheme(themeEntity);
                if (isInsert) {
                    return new BaseResponseEntity(ResponseCode.REQUEST_SUCCESS_MSG,ResponseCode.SUCCESS);
                } else {
                    return new BaseResponseEntity(ResponseCode.CREATE_FILE,ResponseCode.FAIL);
                }
            } else {
                // 主题名已被占用
                return new BaseResponseEntity(ResponseCode.THEME_NAME_HAVE,ResponseCode.FAIL);
            }
        } else {
            return new BaseResponseEntity(ResponseCode.COOKIES_OUT_TIME,ResponseCode.UN_LOGIN);
        }
    }


    /**
     * 新增主题关注(并更新关注主题人数)
     *
     * @param userCookies       用户Cookies
     * @param themeId           关注主题Id
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/followTheme")
    public BaseResponseEntity followTheme(@RequestHeader("userCookies") String userCookies,
                                          @Param("themeId") String themeId) {
        UserBaseInfoEntity userBaseInfoEntity = userBaseInfoService.findUserInfoByCookies(userCookies);
        String uploadTime = TimeUtils.getNowTime();
        String themeSubId = UUID.randomUUID().toString();
        if (userBaseInfoEntity != null) {
            // 检查是否已经关注
            ThemeSubEntity checkThemeSub = themeService.checkThemeSubByUserId(themeId,userBaseInfoEntity.getUserId());
            // 检查关注主题是否存在
            ThemeEntity themeEntity = themeService.findThemeById(themeId);
            if (checkThemeSub == null) {
                if (themeEntity != null) {
                    ThemeSubEntity themeSubEntity = new ThemeSubEntity();
                    themeSubEntity.setThemeId(themeId);
                    themeSubEntity.setTime(uploadTime);
                    themeSubEntity.setThemeSubId(themeSubId);
                    themeSubEntity.setUserId(userBaseInfoEntity.getUserId());
                    boolean isInsert = themeService.insertThemeSub(themeSubEntity);
                    if (isInsert) {
                        // 更新主题关注数
                        themeService.updateThemeCount(themeEntity.getThemeId(),String.valueOf(themeEntity.getSubCount()+1));
                        return new BaseResponseEntity(ResponseCode.REQUEST_SUCCESS_MSG,ResponseCode.SUCCESS);
                    } else {
                        return new BaseResponseEntity(ResponseCode.REQUEST_FAIL_MSG,ResponseCode.FAIL);
                    }
                } else {
                    return new BaseResponseEntity(ResponseCode.THEME_HAVE_ONT,ResponseCode.FAIL);
                }
            } else {
                return new BaseResponseEntity(ResponseCode.ALREADY_FOLLOW,ResponseCode.FAIL);
            }
        } else {
            return new BaseResponseEntity(ResponseCode.COOKIES_OUT_TIME,ResponseCode.UN_LOGIN);
        }
    }

    /**
     * 取消关注主题(同时更新主题关注人数)
     *
     * @param userCookies   发起者Cookies
     * @param themeId       主题Id
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/unfollowTheme")
    public BaseResponseEntity unfollowTheme(@RequestHeader("userCookies") String userCookies,
                                            @Param("themeId") String themeId) {
        UserBaseInfoEntity userBaseInfoEntity = userBaseInfoService.findUserInfoByCookies(userCookies);
        // 检查关注主题是否存在
        ThemeEntity themeEntity = themeService.findThemeById(themeId);
        if (userBaseInfoEntity != null) {
            // 检查是否已经关注
            ThemeSubEntity checkThemeSub = themeService.checkThemeSubByUserId(themeId,userBaseInfoEntity.getUserId());
            if (checkThemeSub != null) {
                boolean isDelete = themeService.deleteThemeSub(themeId,userBaseInfoEntity.getUserId());
                if (isDelete) {
                    themeService.updateThemeCount(themeId,String.valueOf(themeEntity.getSubCount()-1));
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
     * 查询某人所有订阅主题记录及信息(返回theme详情信息)
     *
     * @param userCookies       查询者Cookies(当为空时,返回的主题默认为未关注)
     * @param userId            被查询者Id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/findAllFlowThemeInfo")
    public BaseResponseEntity<List<ThemeEntity>> findAllFlowThemeInfo(@RequestHeader("userCookies") String userCookies,
                                                   @Param("userId") String userId) {
        UserBaseInfoEntity userBaseInfoEntity = userBaseInfoService.findUserInfoByCookies(userCookies);
        if (userBaseInfoEntity != null) {
            List<ThemeSubEntity> themeSubEntities = themeService.findAllThemeSubByUserId(userId);
            List<ThemeEntity> themeEntities = new ArrayList<>();
            for (ThemeSubEntity single : themeSubEntities) {
                ThemeEntity themeEntity = themeService.findThemeById(single.getThemeId());
                ThemeSubEntity isFollow = themeService.checkThemeSubByUserId(single.getThemeId(),userBaseInfoEntity.getUserId());
                if (isFollow != null) {
                    themeEntity.setFollowed(true);
                } else {
                    themeEntity.setFollowed(false);
                }
                themeEntities.add(themeEntity);
            }
            return new BaseResponseEntity<>(ResponseCode.REQUEST_SUCCESS_MSG,ResponseCode.SUCCESS,themeEntities);
        } else {
            List<ThemeSubEntity> themeSubEntities = themeService.findAllThemeSubByUserId(userId);
            List<ThemeEntity> themeEntities = new ArrayList<>();
            for (ThemeSubEntity single : themeSubEntities) {
                ThemeEntity themeEntity = themeService.findThemeById(single.getThemeId());
                themeEntity.setFollowed(false);
                themeEntities.add(themeEntity);
            }
            return new BaseResponseEntity<>(ResponseCode.REQUEST_SUCCESS_MSG,ResponseCode.SUCCESS,themeEntities);
        }
    }

    /**
     * 查询所有主题信息
     *
     * @param userCookies       用户Cookies(不为空时,返回该用户是否订阅该主题,反则都未订阅)
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/findAllTheme")
    public BaseResponseEntity<List<ThemeEntity>> findAllTheme(@RequestHeader("userCookies") String userCookies) {
        UserBaseInfoEntity userBaseInfoEntity = userBaseInfoService.findUserInfoByCookies(userCookies);
        if (userBaseInfoEntity != null) {
            List<ThemeEntity> themeSearch = themeService.findAllTheme();
            List<ThemeEntity> themeEntities = new ArrayList<>();
            for (ThemeEntity single: themeSearch) {
                ThemeSubEntity themeSubEntity = themeService.checkThemeSubByUserId(single.getThemeId(),userBaseInfoEntity.getUserId());
                if (themeSubEntity != null) {
                    single.setFollowed(true);
                    themeEntities.add(single);
                } else {
                    single.setFollowed(false);
                    themeEntities.add(single);
                }
            }
            return new BaseResponseEntity<>(ResponseCode.REQUEST_SUCCESS_MSG,ResponseCode.SUCCESS,themeEntities);
        } else {
            List<ThemeEntity> themeEntities = themeService.findAllTheme();
            return new BaseResponseEntity<>(ResponseCode.REQUEST_SUCCESS_MSG,ResponseCode.SUCCESS,themeEntities);
        }
    }

}

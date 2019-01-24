package com.lznbys.android.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lznbys.android.base.BaseResponseEntity;
import com.lznbys.android.base.ResponseCode;
import com.lznbys.android.entity.*;
import com.lznbys.android.service.*;
import com.lznbys.android.utlis.FileUtils;
import com.lznbys.android.utlis.TimeUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 资讯信息
 */

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private UserBaseInfoService userBaseInfoService;
    @Autowired
    private FilePathService filePathService;
    @Autowired
    private ArticleThemeService articleThemeService;

    /**
     * 新建资讯接口
     *
     * @param userCookies    用户Cookies
     * @param uploadingFiles 资讯文件
     * @param title          资讯标题(可缺省,暂时无用)
     * @param content        资讯文字内容
     * @param themeId       主题Id(json格式上传,可以有多个)
     * @param type           资讯类型
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/createArticle")
    public BaseResponseEntity createArticle(@RequestHeader("userCookies") String userCookies,
                                            @RequestParam("files") MultipartFile[] uploadingFiles,
                                            @Param("title") String title,
                                            @Param("content") String content,
                                            @Param("themeId") String themeId,
                                            @Param("type") String type) {
        // 用户判读是否登录,以及获取用户基本信息
        UserBaseInfoEntity userBaseInfoEntity = userBaseInfoService.findUserInfoByCookies(userCookies);
        if (uploadingFiles != null) {
            // 1.所属编号(资讯的唯一编号)
            String fileAttribution = UUID.randomUUID().toString();

            // 2.上传时间
            String uploadTime = TimeUtils.getNowTime();

            for (MultipartFile uploadedFile : uploadingFiles) {
                // 文件唯一编号
                String fileId = UUID.randomUUID().toString();

                // 文件名称
                String fileTruePath = fileId + FileUtils.getFileType(uploadedFile);
                File file = new File(FileUtils.getFilePath(fileTruePath));
                try {
                    // 保存文件
                    uploadedFile.transferTo(file);
                    // 文件信息对象
                    FilePathEntity filePathEntity = new FilePathEntity();
                    // 文件编号(含后缀)
                    filePathEntity.setFileId(fileTruePath);
                    // 存储路径
                    filePathEntity.setFilePath(FileUtils.getVisiblePath(fileTruePath));
                    // 归属编号
                    filePathEntity.setFileAttribution(fileAttribution);
                    // 文件类型
                    filePathEntity.setFileType(2);
                    // 公开私有
                    filePathEntity.setFilePrivate(1);
                    // 设置上传时间
                    filePathEntity.setTime(uploadTime);
                    // 设置上传者信息(数值型)
                    filePathEntity.setUserId(userBaseInfoEntity.getUserId());

                    filePathService.createFilePath(filePathEntity);
                } catch (IOException e) {
                    e.printStackTrace();
                    return new BaseResponseEntity(e.getMessage(), ResponseCode.FAIL);
                }
            }

            // 3.文件上传成功后,存储资讯信息
            ArticleEntity articleEntity = new ArticleEntity();
            articleEntity.setFileAttribution(fileAttribution);
            articleEntity.setTime(uploadTime);
            articleEntity.setTitle(title);
            articleEntity.setContent(content);
            articleEntity.setUserId(userBaseInfoEntity.getUserId());
            articleEntity.setThemeId("");
            articleEntity.setReadCount("0");
            articleEntity.setLoveCount("0");
            articleEntity.setCommentCount("0");
            // 设置资讯类型
            articleEntity.setType(type);
            boolean isCreate = articleService.createArticle(articleEntity);
            if (!isCreate) {
                return new BaseResponseEntity(ResponseCode.ARTICLE_SAVE_FAIL, ResponseCode.FAIL);
            }
            if (!StringUtils.isEmpty(themeId)) {
                // 5. 保存所属主题可以有多个
                Gson gson = new Gson();
                List<String> themeList = gson.fromJson(themeId, new TypeToken<List<String>>() {
                }.getType());
                if (saveTheme(fileAttribution, themeList))
                    return new BaseResponseEntity(ResponseCode.HAVENT_THEME, ResponseCode.FAIL);
            }

            // 4.最后存储,资讯所属主题
            return new BaseResponseEntity(ResponseCode.REQUEST_SUCCESS_MSG, ResponseCode.SUCCESS);

        } else {
            return new BaseResponseEntity(ResponseCode.COOKIES_OUT_TIME, ResponseCode.UN_LOGIN);
        }
    }

    /**
     * 删除资讯接口
     *
     * @param userCookies     用户Cookies
     * @param fileAttribution 资讯Id
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/deleteArticle")
    public BaseResponseEntity deleteArticle(@RequestHeader("userCookies") String userCookies,
                                            @Param("fileAttribution") String fileAttribution) {
        // 1.用户判读是否登录,以及获取用户基本信息
        UserBaseInfoEntity userBaseInfoEntity = userBaseInfoService.findUserInfoByCookies(userCookies);
        if (userBaseInfoEntity != null) {
            ArticleEntity articleEntity = articleService.findArticleByFileAttribution(fileAttribution);
            if (articleEntity != null) {
                // 2.判断资讯创建者与发起删除者是否为同一人
                if (articleEntity.getUserId().equals(userBaseInfoEntity.getUserId())) {
                    boolean isArticleDelete = articleService.deleteArticle(fileAttribution);
                    boolean isArticleThemeDelete = articleThemeService.deleteArticleTheme(fileAttribution);
                    // 3.删除资讯所属主题以及资讯内容(应该把资讯的图片资源给删掉,暂时没删除)
                    if (isArticleDelete && isArticleThemeDelete) {
                        return new BaseResponseEntity(ResponseCode.REQUEST_SUCCESS_MSG, ResponseCode.SUCCESS);
                    } else {
                        return new BaseResponseEntity(ResponseCode.REQUEST_FAIL_MSG, ResponseCode.FAIL);
                    }
                } else {
                    return new BaseResponseEntity(ResponseCode.COOKIES_OUT_TIME, ResponseCode.UN_LOGIN);
                }
            } else {
                return new BaseResponseEntity(ResponseCode.REQUEST_FAIL_MSG, ResponseCode.FAIL);
            }
        } else {
            return new BaseResponseEntity(ResponseCode.COOKIES_OUT_TIME, ResponseCode.UN_LOGIN);
        }
    }

    /**
     * 根据Id获取资讯详情(资讯内容)
     *
     * @param fileAttribution 要查询的资讯Id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/getArticleByFileAttribution")
    public BaseResponseEntity<List<ArticleAllInfoEntity>> getArticleByFileAttribution(@RequestHeader("userCookies") String userCookies,
                                                                                      @Param("fileAttribution") String fileAttribution) {
        UserBaseInfoEntity userBaseInfoEntity = userBaseInfoService.findUserInfoByCookies(userCookies);
        List<ArticleEntity> allArticles = new ArrayList<>();
        allArticles.add(articleService.findArticleByFileAttribution(fileAttribution));
        List<ArticleAllInfoEntity> articleAllInfoEntities = new ArrayList<>();
        getArticleAllInfo(allArticles, articleAllInfoEntities, userBaseInfoEntity);
        return new BaseResponseEntity<>(ResponseCode.REQUEST_SUCCESS_MSG, ResponseCode.SUCCESS, articleAllInfoEntities);
    }


    /**
     * 获取所有资讯资讯(暂时充当-发现资讯的接口)
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/getAllArticle")
    public BaseResponseEntity<List<ArticleAllInfoEntity>> getAllArticle(@RequestHeader("userCookies") String userCookies) {
        UserBaseInfoEntity userBaseInfoEntity = userBaseInfoService.findUserInfoByCookies(userCookies);
        List<ArticleEntity> allArticles = articleService.findAllArticle();
        List<ArticleAllInfoEntity> articleAllInfoEntities = new ArrayList<>();
        getArticleAllInfo(allArticles, articleAllInfoEntities, userBaseInfoEntity);
        return new BaseResponseEntity<>(ResponseCode.REQUEST_SUCCESS_MSG, ResponseCode.SUCCESS, articleAllInfoEntities);
    }

    /**
     * 创建资讯使用OSS存放文件
     *
     * @param userCookies 上传者Cookies信息
     * @param files       OSS文件存储地址的Json
     * @param title       资讯标题(可以省略)
     * @param content     资讯正文内容
     * @param themeId    资讯所属主题
     * @param type        资讯类型
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/createArticleByOss")
    public BaseResponseEntity createArticle(@RequestHeader("userCookies") String userCookies,
                                            @Param("files") String files,
                                            @Param("title") String title,
                                            @Param("content") String content,
                                            @Param("themeId") String themeId,
                                            @Param("type") String type) {

        // 用户判读是否登录,以及获取用户基本信息
        UserBaseInfoEntity userBaseInfoEntity = userBaseInfoService.findUserInfoByCookies(userCookies);
        if (!files.isEmpty()) {
            // 1.所属编号(资讯的唯一编号)
            String fileAttribution = UUID.randomUUID().toString();

            // 2.上传时间
            String uploadTime = TimeUtils.getNowTime();

            Gson gson = new Gson();
            List<String> ossPaths = gson.fromJson(files, new TypeToken<List<String>>() {
            }.getType());

            for (String fileName : ossPaths) {
                // 文件唯一编号
                String fileId = UUID.randomUUID().toString();
                // 文件名称
                String fileTruePath = FileUtils.getOssFilePath(fileId);
                // 文件信息对象
                FilePathEntity filePathEntity = new FilePathEntity();
                // 文件编号(含后缀)
                filePathEntity.setFileId(fileName);
                // 存储路径
                filePathEntity.setFilePath(FileUtils.getOssFilePath(fileName));
                // 归属编号
                filePathEntity.setFileAttribution(fileAttribution);
                // 文件类型
                filePathEntity.setFileType(2);
                // 公开私有
                filePathEntity.setFilePrivate(1);
                // 设置上传时间
                filePathEntity.setTime(uploadTime);
                // 设置上传者信息(数值型)
                filePathEntity.setUserId(userBaseInfoEntity.getUserId());
                filePathService.createFilePath(filePathEntity);
            }
            // 3.文件上传成功后,存储资讯信息
            ArticleEntity articleEntity = new ArticleEntity();
            articleEntity.setFileAttribution(fileAttribution);
            articleEntity.setTime(uploadTime);
            articleEntity.setTitle(title);
            articleEntity.setContent(content);
            articleEntity.setUserId(userBaseInfoEntity.getUserId());
            articleEntity.setThemeId("");
            articleEntity.setReadCount("0");
            articleEntity.setLoveCount("0");
            articleEntity.setCommentCount("0");
            // 设置资讯类型
            articleEntity.setType(type);
            boolean isCreate = articleService.createArticle(articleEntity);
            if (!isCreate) {
                return new BaseResponseEntity(ResponseCode.ARTICLE_SAVE_FAIL, ResponseCode.FAIL);
            }
            if (!StringUtils.isEmpty(themeId)) {
                // 4. 保存所属主题可以有多个
                List<String> themeList = gson.fromJson(themeId, new TypeToken<List<String>>() {}.getType());
                if (saveTheme(fileAttribution, themeList))
                    return new BaseResponseEntity(ResponseCode.HAVENT_THEME, ResponseCode.FAIL);
            }

            // 4.最后存储,资讯所属主题
            return new BaseResponseEntity(ResponseCode.REQUEST_SUCCESS_MSG, ResponseCode.SUCCESS);

        } else {
            return new BaseResponseEntity(ResponseCode.COOKIES_OUT_TIME, ResponseCode.UN_LOGIN);
        }

    }


    /************************************************资讯收藏**************************************************/

    /**
     * 资讯收藏
     *
     * @param userCookies     用户Cookies
     * @param fileAttribution 资讯编号
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/articleSub")
    public BaseResponseEntity articleSub(@RequestHeader("userCookies") String userCookies,
                                         @Param("fileAttribution") String fileAttribution) {
        // 1.判断用户是否登录
        UserBaseInfoEntity userBaseInfoEntity = userBaseInfoService.findUserInfoByCookies(userCookies);
        if (userBaseInfoEntity != null) {
            // 2.判断资讯是否存在
            ArticleEntity articleEntity = articleService.findArticleByFileAttribution(fileAttribution);
            if (articleEntity != null) {
                String articleSubId = UUID.randomUUID().toString();
                String createTime = TimeUtils.getNowTime();
                ArticleSubEntity articleSubEntity = new ArticleSubEntity();
                articleSubEntity.setArticleSubId(articleSubId);
                articleSubEntity.setFileAttribution(fileAttribution);
                articleSubEntity.setUserId(userBaseInfoEntity.getUserId());
                articleSubEntity.setTime(createTime);
                boolean isInsert = articleService.insertArticleSub(articleSubEntity);
                if (isInsert) {
                    // 3.更新资讯统计数值
                    int readCount = Integer.valueOf(articleEntity.getLoveCount()) + 1;
                    articleService.updateArticleSubCount(fileAttribution, articleEntity.getReadCount(), String.valueOf(readCount), articleEntity.getCommentCount());
                    return new BaseResponseEntity(ResponseCode.REQUEST_SUCCESS_MSG, ResponseCode.SUCCESS);
                } else {
                    return new BaseResponseEntity(ResponseCode.FIAL_WAIT_A_MINI, ResponseCode.FAIL);
                }
            } else {
                return new BaseResponseEntity(ResponseCode.ARTICLE_HAVE_NOT, ResponseCode.FAIL);
            }
        } else {
            return new BaseResponseEntity(ResponseCode.COOKIES_OUT_TIME, ResponseCode.UN_LOGIN);
        }
    }


    /**
     * 取消资讯收藏
     *
     * @param userCookies     用户Cookies
     * @param fileAttribution 资讯编号
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, value = "/articleUnSub")
    public BaseResponseEntity articleUnSub(@RequestHeader("userCookies") String userCookies,
                                           @Param("fileAttribution") String fileAttribution) {
        // 1.判断用户是否登录
        UserBaseInfoEntity userBaseInfoEntity = userBaseInfoService.findUserInfoByCookies(userCookies);
        if (userBaseInfoEntity != null) {
            // 2.删除资讯订阅信息
            boolean isDelete = articleService.deleteArticleSub(userBaseInfoEntity.getUserId(), fileAttribution);
            if (isDelete) {
                ArticleEntity articleEntity = articleService.findArticleByFileAttribution(fileAttribution);
                int readCount = Integer.valueOf(articleEntity.getLoveCount()) - 1;
                // 3.更新资讯统计信息
                articleService.updateArticleSubCount(fileAttribution, articleEntity.getReadCount(), String.valueOf(readCount), articleEntity.getCommentCount());
                return new BaseResponseEntity(ResponseCode.REQUEST_SUCCESS_MSG, ResponseCode.SUCCESS);
            } else {
                return new BaseResponseEntity(ResponseCode.FIAL_WAIT_A_MINI, ResponseCode.FAIL);
            }
        } else {
            return new BaseResponseEntity(ResponseCode.COOKIES_OUT_TIME, ResponseCode.UN_LOGIN);
        }
    }


    /**
     * 查询用户所有收藏的资讯信息
     *
     * @param userCookies 用户Cookies
     * @param userId      被查者Id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/getAllSubArticle")
    public BaseResponseEntity<List<ArticleAllInfoEntity>> getAllSubArticle(@RequestHeader("userCookies") String userCookies,
                                               @Param("userId") String userId) {
        UserBaseInfoEntity userBaseInfoEntity = userBaseInfoService.findUserInfoByCookies(userCookies);

        List<ArticleSubEntity> allSubArticle = articleService.getAllSubArticle(userId);
        // 1.存放返回的资讯数据
        List<ArticleAllInfoEntity> articleAllInfoEntities = new ArrayList<>();

        for (ArticleSubEntity single : allSubArticle) {
            ArticleAllInfoEntity entity = new ArticleAllInfoEntity();
            if (userBaseInfoEntity != null) {
                entity.setLove(articleService.checkArticleSub(single.getFileAttribution(),userBaseInfoEntity.getUserId())!=null);
            }
            entity.setThemeEntities(articleThemeService.findThemeByFileAttribution(single.getFileAttribution()));
            entity.setFilePathEntities(filePathService.findAllFilePathByFileAttribution(single.getFileAttribution()));
            entity.setArticleEntity(articleService.findArticleByFileAttribution(single.getFileAttribution()));
            entity.setUserBaseInfoEntity(userBaseInfoService.findUserInfoByUserId(entity.getArticleEntity().getUserId()));
            articleAllInfoEntities.add(entity);
        }
        return new BaseResponseEntity<>(ResponseCode.REQUEST_SUCCESS_MSG, ResponseCode.SUCCESS,articleAllInfoEntities);
    }

    /************************************************公用函数**************************************************/

    /**
     * 保存主题
     *
     * @param fileAttribution 资讯编号
     * @param themeList       主题集合
     * @return
     */
    private boolean saveTheme(String fileAttribution, List<String> themeList) {
        for (String theme : themeList) {
            ArticleThemeEntity articleThemeEntity = new ArticleThemeEntity();
            String belongId = UUID.randomUUID().toString();
            articleThemeEntity.setBelongId(belongId);
            articleThemeEntity.setFileAttribution(fileAttribution);
            articleThemeEntity.setThemeId(theme);
            boolean isInsert = articleThemeService.createArticleTheme(articleThemeEntity);
            if (!isInsert) {
                // 返回是否保存成功
                return true;
            }
        }
        return false;
    }

    /**
     * 获取所有资讯信息
     *
     * @param allArticles            资讯编号
     * @param articleAllInfoEntities 资讯存储引用
     */
    private void getArticleAllInfo(List<ArticleEntity> allArticles, List<ArticleAllInfoEntity> articleAllInfoEntities, UserBaseInfoEntity requestUserInfo) {
        for (ArticleEntity single : allArticles) {
            UserBaseInfoEntity userBaseInfoEntity = userBaseInfoService.findUserInfoByUserId(single.getUserId());
            List<FilePathEntity> filePathEntities = filePathService.findAllFilePathByFileAttribution(single.getFileAttribution());
            List<ThemeEntity> themeEntities = articleThemeService.findThemeByFileAttribution(single.getFileAttribution());
            ArticleAllInfoEntity articleAllInfoEntity = new ArticleAllInfoEntity();
            articleAllInfoEntity.setArticleEntity(single);
            articleAllInfoEntity.setFilePathEntities(filePathEntities);
            articleAllInfoEntity.setThemeEntities(themeEntities);
            articleAllInfoEntity.setUserBaseInfoEntity(userBaseInfoEntity);
            // 1.判断用户是否收藏过该资讯
            ArticleSubEntity isSub = articleService.checkArticleSub(single.getFileAttribution(), requestUserInfo != null ? requestUserInfo.getUserId() : "");
            if (isSub != null) {
                articleAllInfoEntity.setLove(true);
            }
            articleAllInfoEntities.add(articleAllInfoEntity);
        }
    }

}

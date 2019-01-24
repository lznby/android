package com.lznbys.android.service.impl;

import com.lznbys.android.dao.ArticleDao;
import com.lznbys.android.entity.ArticleEntity;
import com.lznbys.android.entity.ArticleSubEntity;
import com.lznbys.android.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 资讯信息实现类
 */
@Service("article")
public class ArticleServiceImpl implements ArticleService {

    private final ArticleDao articleDao;

    /**
     * 依赖注入
     *
     * @param articleDao
     */
    @Autowired
    public ArticleServiceImpl(ArticleDao articleDao) {
        this.articleDao = articleDao;
    }

    /**
     * 创建资讯
     *
     * @param articleEntity 资讯信息对象
     * @return
     */
    @Override
    public boolean createArticle(ArticleEntity articleEntity) {
        int isInsert = articleDao.insertArticle(articleEntity);
        return (isInsert > 0);
    }

    /**
     * 根据资讯Id删除资讯
     *
     * @param fileAttribution 资讯Id
     * @return
     */
    @Override
    public boolean deleteArticle(String fileAttribution) {
        int isDelete = articleDao.deleteArticle(fileAttribution);
        return (isDelete > 0);
    }

    /**
     * 根据用户Id查询该用户创建的所有资讯
     *
     * @param userId 用户Id
     * @return
     */
    @Override
    public List<ArticleEntity> findArticleByUserId(String userId) {
        return articleDao.findArticleByUserId(userId);
    }

    /**
     * 查询所有资讯
     *
     * @return
     */
    @Override
    public List<ArticleEntity> findAllArticle() {
        return articleDao.findAllArticle();
    }

    /**
     * 根据fileAttribution查询资讯信息
     *
     * @return
     */
    @Override
    public ArticleEntity findArticleByFileAttribution(String fileAttribution) {
        ArticleEntity articleEntity = articleDao.findArticleByFileAttribution(fileAttribution);
        return articleEntity;
    }

    /***********************************************资讯数据统计***************************************************/


    /**
     * 收藏资讯
     *
     * @param articleSubEntity      收藏记录信息
     * @return
     */
    @Override
    public boolean insertArticleSub(ArticleSubEntity articleSubEntity) {
        int isInsert = articleDao.insertArticleSub(articleSubEntity);
        return (isInsert > 0);
    }

    /**
     * 取消资讯收藏
     *
     * @param userId                取消收藏用户Id
     * @param fileAttribution       取消收藏资讯Id
     * @return
     */
    @Override
    public boolean deleteArticleSub(String userId, String fileAttribution) {
        int isDelete = articleDao.deleteArticleSub(userId,fileAttribution);
        return (isDelete > 0);
    }

    /**
     * 检查是否已经收藏
     *
     * @param fileAttribution       资讯Id
     * @param userId                用户Id
     * @return
     */
    @Override
    public ArticleSubEntity checkArticleSub(String fileAttribution, String userId) {
        return articleDao.checkArticleSub(fileAttribution,userId);
    }

    /**
     * 修改资讯统计数据
     *
     * @param fileAttribution       修改的资讯编号
     * @param readCount             用户阅读数
     * @param loveCount             用户收藏数
     * @param commentCount          用户评论数
     * @return
     */
    @Override
    public boolean updateArticleSubCount(String fileAttribution, String readCount, String loveCount, String commentCount) {
        int isUpdate = articleDao.updateArticleCount(fileAttribution,readCount,loveCount,commentCount);
        return (isUpdate > 0);
    }

    /**
     * 查询某个用户所有的收藏记录
     *
     * @param userId                被查询者Id
     * @return                      订阅记录
     */
    @Override
    public List<ArticleSubEntity> getAllSubArticle(String userId) {
        return articleDao.getAllSubArticle(userId);
    }
}

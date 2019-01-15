package com.lznbys.android.service.impl;

import com.lznbys.android.dao.ArticleDao;
import com.lznbys.android.entity.ArticleEntity;
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
}

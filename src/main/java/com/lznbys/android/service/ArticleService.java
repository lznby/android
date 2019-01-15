package com.lznbys.android.service;

import com.lznbys.android.entity.ArticleEntity;

import java.util.List;

/**
 * 资讯信息
 */
public interface ArticleService {

    /**
     * 创建资讯内容
     *
     * @param articleEntity     资讯信息对象
     * @return
     */
    boolean createArticle(ArticleEntity articleEntity);

    /**
     * 根据资讯编号删除资讯内容
     *
     * @param fileAttribution   资讯Id
     * @return
     */
    boolean deleteArticle(String fileAttribution);

    /**
     * 根据用户Id查询该用户所有创建的资讯信息
     *
     * @param userId       用户Id
     * @return
     */
    List<ArticleEntity> findArticleByUserId(String userId);

    /**
     * 查询所有资讯信息
     *
     * @return
     */
    List<ArticleEntity> findAllArticle();

    /**
     * 根据fileAttribution查询资讯信息
     *
     * @param fileAttribution 资讯Id
     * @return
     */
    ArticleEntity findArticleByFileAttribution(String fileAttribution);

}

package com.lznbys.android.service;

import com.lznbys.android.entity.ArticleEntity;
import com.lznbys.android.entity.ArticleSubEntity;

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


    /***********************************************资讯数据统计***************************************************/

    /**
     * 收藏资讯
     *
     * @param articleSubEntity      收藏记录信息
     * @return
     */
    boolean insertArticleSub(ArticleSubEntity articleSubEntity);

    /**
     * 取消资讯收藏
     *
     * @param userId                取消收藏用户Id
     * @param fileAttribution       取消收藏资讯Id
     * @return
     */
    boolean deleteArticleSub(String userId, String fileAttribution);

    /**
     * 检查是否已经收藏
     *
     * @param fileAttribution       资讯Id
     * @param userId                用户Id
     * @return
     */
    ArticleSubEntity checkArticleSub(String fileAttribution, String userId);

    /**
     * 修改资讯统计数据
     *
     * @param fileAttribution       修改的资讯编号
     * @param readCount             用户阅读数
     * @param loveCount             用户收藏数
     * @param commentCount          用户评论数
     * @return
     */
    boolean updateArticleSubCount(String fileAttribution, String readCount, String loveCount, String commentCount);

}

package com.lznbys.android.dao;

import com.lznbys.android.entity.ArticleEntity;
import com.lznbys.android.entity.ArticleSubEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 普通图文资讯表 CURD接口类
 *
 * 对应表：app_article
 */
@Repository
public interface ArticleDao {

    /**
     * 新增资讯
     *
     * @param articleEntity     资讯内容
     * @return
     */
    @Insert("INSERT INTO app_article " +
            "(fileAttribution, time, title, content, userId, readCount, loveCount, commentCount, type) " +
            "VALUES (#{articleEntity.fileAttribution}, #{articleEntity.time}, #{articleEntity.title}, #{articleEntity.content}, #{articleEntity.userId}, #{articleEntity.readCount}, #{articleEntity.loveCount}, #{articleEntity.commentCount}, #{articleEntity.type})"
    )
    Integer insertArticle(@Param("articleEntity") ArticleEntity articleEntity);


    /**
     * 根据资讯编号删除资讯
     *
     * @param fileAttribution
     * @return
     */
    @Delete("DELETE FROM app_article WHERE app_article.fileAttribution = #{fileAttribution}")
    Integer deleteArticle(@Param("fileAttribution") String fileAttribution);

    /**
     * 根据用户id查询该用户创建的所有资讯
     *
     * @param userId    用户Id
     * @return
     */
    @Select("SELECT * FROM app_article WHERE app_article.userId = #{userId}")
    @ResultType(ArticleEntity.class)
    List<ArticleEntity> findArticleByUserId(@Param("userId") String userId);


    /**
     * 查询所有资讯信息
     *
     * @return
     */
    @Select("SELECT * FROM app_article ORDER BY app_article.time DESC")
    @ResultType(ArticleEntity.class)
    List<ArticleEntity> findAllArticle();

    /**
     * 根据fileAttribution查询资讯信息
     *
     * @param fileAttribution    资讯
     * @return
     */
    @Select("SELECT * FROM app_article WHERE app_article.fileAttribution = #{fileAttribution}")
    @ResultType(ArticleEntity.class)
    ArticleEntity findArticleByFileAttribution(@Param("fileAttribution") String fileAttribution);

    /*******************************************资讯收藏**************************************************/

    /**
     * 收藏资讯
     *
     * @param articleSubEntity  收藏记录信息
     * @return
     */
    @Insert("INSERT INTO app_article_sub (articleSubId,fileAttribution,time,userId) " +
            "VALUES (#{articleSubEntity.articleSubId},#{articleSubEntity.fileAttribution},#{articleSubEntity.time},#{articleSubEntity.userId})"
    )
    Integer insertArticleSub(@Param("articleSubEntity") ArticleSubEntity articleSubEntity);

    /**
     * 取消资讯收藏
     *
     * @param userId            取消收藏用户Id
     * @param fileAttribution   取消收藏资讯Id
     * @return
     */
    @Delete("DELETE FROM app_article_sub WHERE (app_article_sub.userId = #{userId} AND app_article_sub.fileAttribution = #{fileAttribution})")
    Integer deleteArticleSub(@Param("userId") String userId, @Param("fileAttribution") String fileAttribution);

    /**
     * 检查是否已经收藏
     *
     * @param fileAttribution   资讯Id
     * @param userId            用户Id
     * @return
     */
    @Select("SELECT * FROM app_article_sub WHERE (app_article_sub.fileAttribution = #{fileAttribution} AND app_article_sub.userId = #{userId});")
    @ResultType(ArticleSubEntity.class)
    ArticleSubEntity checkArticleSub(@Param("fileAttribution") String fileAttribution, @Param("userId") String userId);

    /**
     * 修改资讯收藏、阅读、评论数
     *
     * @param fileAttribution   修改统计数值的资讯编号
     * @param readCount         阅读数
     * @param loveCount         收藏数
     * @param commentCount      评论数
     * @return
     */
    @Update("UPDATE app_article " +
            "SET readCount = #{readCount},loveCount = #{loveCount},commentCount = #{commentCount} " +
            "WHERE fileAttribution = #{fileAttribution}"
    )
    Integer updateArticleCount(@Param("fileAttribution") String fileAttribution,
                               @Param("readCount") String readCount,
                               @Param("loveCount") String loveCount,
                               @Param("commentCount") String commentCount);

    /**
     * 根据用户id查询用户收藏的所有资讯
     *
     * @param userId        被查询这用户Id
     * @return              订阅记录
     */
    @Select("SELECT * FROM app_article_sub WHERE (app_article_sub.userId = #{userId});")
    @ResultType(ArticleSubEntity.class)
    List<ArticleSubEntity> getAllSubArticle(@Param("userId") String userId);

}

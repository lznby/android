package com.lznbys.android.dao;

import com.lznbys.android.entity.ArticleEntity;
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


    /**
     * 查询某主题下所有分类
     */
/*    @Results(value = {
            @Result(column = "fileAttribution=fileAttribution", property = "filePath", javaType = FilePathEntity.class, many = @Many(select = "com.lznbys.android.dao.FilePathDao.findFilePathByFileId"))
    })
    //,app_file_path;
    @Select("SELECT * FROM app_article")
    @ResultType(ArticleAllInfoEntity.class)
    List selectAll();*/
}

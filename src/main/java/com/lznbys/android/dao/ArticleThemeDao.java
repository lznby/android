package com.lznbys.android.dao;

import com.lznbys.android.entity.ArticleThemeEntity;
import com.lznbys.android.entity.ThemeEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 资讯所属主题表
 *
 * app_theme_article
 */
@Repository
public interface ArticleThemeDao {

    /**
     * 新增资讯所属主题归属
     *
     * @param articleThemeEntity
     * @return
     */
    @Insert("Insert INTO app_theme_article " +
            "(belongId,themeId,fileAttribution) " +
            "VALUES (#{articleThemeEntity.belongId},#{articleThemeEntity.themeId},#{articleThemeEntity.fileAttribution})"
    )
    Integer insertArticleTheme(@Param("articleThemeEntity")ArticleThemeEntity articleThemeEntity);

    /**
     * 删除资讯所属主题归属
     *
     * @param fileAttribution
     * @return
     */
    @Delete("DELETE FROM app_theme_article WHERE (app_theme_article.fileAttribution = #{fileAttribution});")
    Integer deleteArticleTheme(@Param("fileAttribution") String fileAttribution);

    /**
     * 根据资讯Id查询主题归属
     *
     * @param fileAttribution
     * @return
     */
    @Select("SELECT * FROM app_theme,app_theme_article WHERE (app_theme_article.fileAttribution = #{fileAttribution} and app_theme.themeId = app_theme_article.themeId);")
    @ResultType(ThemeEntity.class)
    List<ThemeEntity> findThemeByFileAttribution(@Param("fileAttribution") String fileAttribution);
}

package com.lznbys.android.service;

import com.lznbys.android.entity.ArticleEntity;
import com.lznbys.android.entity.ArticleThemeEntity;
import com.lznbys.android.entity.ThemeEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 资讯所属主题表
 *
 * app_theme_article
 */
public interface ArticleThemeService {
    /**
     * 新增资讯归属主题
     *
     * @param articleThemeEntity     资讯归属主题记录
     * @return
     */
    boolean createArticleTheme(ArticleThemeEntity articleThemeEntity);

    /**
     * 删除资讯归属主题记录(按资讯Id)
     *
     * @param fileAttribution   资讯Id
     * @return
     */
    boolean deleteArticleTheme(String fileAttribution);

    /**
     * 根据资讯Id查询主题信息
     *
     * @param fileAttribution   资讯Id
     * @return
     */
    List<ThemeEntity> findThemeByFileAttribution(String fileAttribution);

    /**
     * 根据主题Id查询该主题下所有资讯
     *
     * @param themeId      主题Id
     * @return             资讯信息
     */
    List<ArticleEntity> findAllArticleByThemeId(@Param("themeId") String themeId);
}

package com.lznbys.android.service;

import com.lznbys.android.entity.ArticleThemeEntity;
import com.lznbys.android.entity.ThemeEntity;

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
}

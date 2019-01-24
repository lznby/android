package com.lznbys.android.service.impl;

import com.lznbys.android.dao.ArticleThemeDao;
import com.lznbys.android.entity.ArticleEntity;
import com.lznbys.android.entity.ArticleThemeEntity;
import com.lznbys.android.entity.ThemeEntity;
import com.lznbys.android.service.ArticleThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 资讯所属主题
 *
 * app_theme_article
 */
@Service("articleTheme")
public class ArticleThemeServiceImpl implements ArticleThemeService {

    private final ArticleThemeDao articleThemeDao;

    /**
     * 依赖注入
     *
     * @param articleThemeDao
     */
    @Autowired
    public ArticleThemeServiceImpl(ArticleThemeDao articleThemeDao) {
        this.articleThemeDao = articleThemeDao;
    }

    /**
     * 新增资讯归属主题
     *
     * @param articleThemeEntity     资讯归属主题记录
     * @return
     */
    @Override
    public boolean createArticleTheme(ArticleThemeEntity articleThemeEntity) {
        int isInsert = articleThemeDao.insertArticleTheme(articleThemeEntity);
        return (isInsert>0);
    }

    /**
     * 删除资讯归属主题记录(按资讯Id)
     *
     * @param fileAttribution   资讯Id
     * @return
     */
    @Override
    public boolean deleteArticleTheme(String fileAttribution) {
        int isDelete = articleThemeDao.deleteArticleTheme(fileAttribution);
        return (isDelete>0);
    }

    /**
     * 根据资讯Id查询主题信息
     *
     * @param fileAttribution   资讯Id
     * @return
     */
    @Override
    public List<ThemeEntity> findThemeByFileAttribution(String fileAttribution) {
        List<ThemeEntity> themeEntities = articleThemeDao.findThemeByFileAttribution(fileAttribution);
        return themeEntities;
    }

    /**
     * 根据主题Id查询该主题下所有资讯
     *
     * @param themeId      主题Id
     * @return
     */
    @Override
    public List<ArticleEntity> findAllArticleByThemeId(String themeId) {
        return articleThemeDao.findAllArticleByThemeId(themeId);
    }
}

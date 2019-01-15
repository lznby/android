package com.lznbys.android.service.impl;

import com.lznbys.android.dao.ThemeDao;
import com.lznbys.android.entity.ThemeEntity;
import com.lznbys.android.entity.ThemeSubEntity;
import com.lznbys.android.service.ThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 主题信息
 */
@Service("theme")
public class ThemeServiceImpl implements ThemeService {

    private final ThemeDao themeDao;

    /**
     * 依赖注入
     *
     * @param themeDao
     */
    @Autowired
    public ThemeServiceImpl(ThemeDao themeDao) {
        this.themeDao = themeDao;
    }

    /**
     *
     * @param themeEntity  新建主题对象
     * @return
     */
    @Override
    public boolean createTheme(ThemeEntity themeEntity) {
        return false;
    }

    /**
     *
     * @param themeId
     * @param subCount
     * @return
     */
    @Override
    public boolean updateThemeCount(String themeId, String subCount) {
        return false;
    }

    /**
     *
     * @param themeId   被查询主题Id
     * @return
     */
    @Override
    public ThemeEntity findThemeById(String themeId) {
        return null;
    }

    /**
     *
     * @param themeSubEntity    订阅主题记录
     * @return
     */
    @Override
    public boolean insertThemeSub(ThemeSubEntity themeSubEntity) {
        return false;
    }

    /**
     *
     * @param themeId           取消订阅的编号
     * @param userId            取消订阅者编号
     * @return
     */
    @Override
    public boolean deleteThemeSub(String themeId, String userId) {
        return false;
    }

    /**
     *
     * @param themeId       被查询的主题Id
     * @param userId        被查询者用户Id
     * @return
     */
    @Override
    public ThemeSubEntity checkThemeSubByUserId(String themeId, String userId) {
        return null;
    }

    /**
     *
     * @param userId           被查询者Id
     * @return
     */
    @Override
    public List<ThemeSubEntity> findAllThemeSubByUserId(String userId) {
        return null;
    }

    /**
     *
     * @return
     */
    @Override
    public List<ThemeEntity> findAllTheme() {
        return null;
    }
}

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
     * 新建主题
     *
     * @param themeEntity  新建主题对象
     * @return
     */
    @Override
    public boolean createTheme(ThemeEntity themeEntity) {
        int isInsert = themeDao.insertTheme(themeEntity);
        return isInsert > 0;
    }

    /**
     * 更新主题关注数
     *
     * @param themeId
     * @param subCount
     * @return
     */
    @Override
    public boolean updateThemeCount(String themeId, String subCount) {
        int isUpdate = themeDao.updateThemeSubCount(themeId,subCount);
        return isUpdate > 0;
    }

    /**
     * 根据主题编号查询主题详情
     *
     * @param themeId   被查询主题Id
     * @return
     */
    @Override
    public ThemeEntity findThemeById(String themeId) {
        return themeDao.findThemeById(themeId);
    }

    /**
     * 添加主题订阅记录
     *
     * @param themeSubEntity    订阅主题记录
     * @return
     */
    @Override
    public boolean insertThemeSub(ThemeSubEntity themeSubEntity) {
        int isInsert = themeDao.insertThemeSub(themeSubEntity);
        return isInsert > 0;
    }

    /**
     * 取消主题订阅
     *
     * @param themeId           取消订阅的编号
     * @param userId            取消订阅者编号
     * @return
     */
    @Override
    public boolean deleteThemeSub(String themeId, String userId) {
        int isDelete = themeDao.deleteThemeSub(themeId, userId);
        return isDelete > 0;
    }

    /**
     * 查询是否订阅主题
     *
     * @param themeId       被查询的主题Id
     * @param userId        被查询者用户Id
     * @return
     */
    @Override
    public ThemeSubEntity checkThemeSubByUserId(String themeId, String userId) {
        return themeDao.checkThemeSubByUserId(themeId, userId);
    }

    /**
     * 根据用户Id查询所有订阅记录
     *
     * @param userId           被查询者Id
     * @return
     */
    @Override
    public List<ThemeSubEntity> findAllThemeSubByUserId(String userId) {
        return themeDao.findAllThemeSubByUserId(userId);
    }

    /**
     * 查询所有主题详情
     *
     * @return
     */
    @Override
    public List<ThemeEntity> findAllTheme() {
        return themeDao.findAllTheme();
    }

    /**
     * 根据主题名称查询主题详情
     *
     * @param themeName     查询的主题名称
     * @return
     */
    @Override
    public ThemeEntity findThemeByName(String themeName) {
        return themeDao.findThemeByName(themeName);
    }
}

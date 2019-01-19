package com.lznbys.android.service;

import com.lznbys.android.entity.ThemeEntity;
import com.lznbys.android.entity.ThemeSubEntity;

import java.util.List;

/**
 * 主题基本信息
 */
public interface ThemeService {
    /**
     * 新建主题
     *
     * @param themeEntity  新建主题对象
     * @return
     */
    boolean createTheme(ThemeEntity themeEntity);

    /**
     * 修改主题订阅数目
     *
     * @param themeId
     * @param subCount
     * @return
     */
    boolean updateThemeCount(String themeId, String subCount);

    /**
     * 根据主题编号查询主题详情
     *
     * @param themeId   被查询主题Id
     * @return
     */
    ThemeEntity findThemeById(String themeId);

    /**
     * 订阅主题
     *
     * @param themeSubEntity    订阅主题记录
     * @return
     */
    boolean insertThemeSub(ThemeSubEntity themeSubEntity);

    /**
     * 取消订阅
     *
     * @param themeId           取消订阅的编号
     * @param userId            取消订阅者编号
     * @return
     */
    boolean deleteThemeSub(String themeId, String userId);

    /**
     * 查询是否已经订阅
     *
     * @param themeId       被查询的主题Id
     * @param userId        被查询者用户Id
     * @return
     */
    ThemeSubEntity checkThemeSubByUserId(String themeId, String userId);

    /**
     * 查询某人所有订阅记录
     *
     * @param userId           被查询者Id
     * @return
     */
    List<ThemeSubEntity> findAllThemeSubByUserId(String userId);

    /**
     * 查询所有主题信息
     *
     * @return
     */
    List<ThemeEntity> findAllTheme();

    /**
     * 根据主题名称搜索主题详情
     *
     * @param themeName     查询的主题名称
     * @return
     */
    ThemeEntity findThemeByName(String themeName);


}

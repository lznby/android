package com.lznbys.android.dao;

import com.lznbys.android.entity.ThemeEntity;
import com.lznbys.android.entity.ThemeSubEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 主题信息表: app_theme
 *
 * 用户订阅主题关系表: app_theme_sub
 */
@Repository
public interface ThemeDao {
    /**
     * 新建主题
     *
     * @param themeEntity       主题对象
     * @return
     */
    @Insert("INSERT INTO app_theme (themeId,time,themeName,themeNote,themeImage,fileAttribution,subCount) " +
            "VALUES (#{themeEntity.themeId},#{themeEntity.time},#{themeEntity.themeName},#{themeEntity.themeNote},#{themeEntity.themeImage},#{themeEntity.fileAttribution},#{themeEntity.subCount})")
    Integer insertTheme(@Param("themeEntity") ThemeEntity themeEntity);

    /**
     * 修改主题订阅数
     *
     * @param themeId           修改订阅数的主题编号
     * @param subCount          修改后主题的订阅数目
     * @return
     */
    @Update("UPDATE app_theme " +
            "SET subCount = #{subCount} " +
            "WHERE themeId = #{themeId}")
    Integer updateThemeSubCount(@Param("themeId") String themeId,@Param("subCount") String subCount);

    /**
     * 根据主题编号查询主题详情
     *
     * @param themeId
     * @return
     */
    @Select("SELECT * FROM app_theme WHERE app_theme.themeId = #{themeId}")
    @ResultType(ThemeEntity.class)
    ThemeEntity findThemeById(@Param("themeId") String themeId);

    /**
     * 查询某人所有订阅记录
     *
     * @param userId        查询某个人所有的订阅记录
     * @return
     */
    @Select("SELECT * FROM app_theme_sub WHERE (app_theme_sub.userId = #{userId})")
    @ResultType(ThemeSubEntity.class)
    List<ThemeSubEntity> findAllThemeSubByUserId(@Param("userId") String userId);


    /**
     * 查询所有主题信息
     *
     * @return
     */
    @Select("SELECT * FROM app_theme;")
    @ResultType(ThemeEntity.class)
    List<ThemeEntity> findAllTheme();


    /**
     * 订阅主题
     *
     * @param themeSubEntity    主题订阅关系对象
     * @return
     */
    @Insert("INSERT INTO app_theme_sub (themeSubId,themeId,userId,time,app_theme_subcol) " +
            "VALUES (#{themeSubEntity.themeSubId},#{themeSubEntity.themeId},#{themeSubEntity.userId},#{themeSubEntity.time},#{themeSubEntity.app_theme_subcol})")
    Integer insertThemeSub(@Param("themeSubEntity") ThemeSubEntity themeSubEntity);

    /**
     * 取消订阅
     *
     * @param themeId           被取消订阅的主题编号
     * @param userId            取消订阅者编号
     * @return
     */
    @Delete("DELETE FROM app_theme_sub WHERE (app_theme_sub.themeId = #{themeId} AND (app_theme_sub.userId = #{userId}))")
    Integer deleteThemeSub(@Param("themeId") String themeId, @Param("userId") String userId);

    /**
     * 查询是否已经订阅
     *
     * @param themeId       被订阅的主题编号
     * @param userId        判断该人是否订阅
     * @return
     */
    @Select("SELECT * FROM app_theme_sub WHERE (app_theme_sub.themeId = #{themeId} AND (app_theme_sub.userId = #{userId}))")
    @ResultType(ThemeSubEntity.class)
    ThemeSubEntity checkThemeSubByUserId(@Param("themeId") String themeId, @Param("userId") String userId);



}

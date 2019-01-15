package com.lznbys.android.entity;

/**
 * 用户订阅主题关系表
 *
 * app_theme_sub
 */
public class ThemeSubEntity {
    /**
     * 订阅主题记录
     */
    String themeSubId;
    /**
     * 被订阅主题Id
     */
    String themeId;
    /**
     * 发起订阅主题的用户Id
     */
    String userId;
    /**
     * 订阅时间
     * YYYY-MM-DD hh:mm:ss
     */
    String time;

    public ThemeSubEntity() {
    }

    public ThemeSubEntity(String themeSubId, String themeId, String userId, String time) {
        this.themeSubId = themeSubId;
        this.themeId = themeId;
        this.userId = userId;
        this.time = time;
    }

    public String getThemeSubId() {
        return themeSubId;
    }

    public void setThemeSubId(String themeSubId) {
        this.themeSubId = themeSubId;
    }

    public String getThemeId() {
        return themeId;
    }

    public void setThemeId(String themeId) {
        this.themeId = themeId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

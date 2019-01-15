package com.lznbys.android.entity;

/**
 * 资讯所属主题表
 *
 * app_theme_article
 */
public class ArticleThemeEntity {
    /**
     * UUID系统随机生成标记唯一
     */
    String belongId;
    /**
     * 主题id
     */
    String themeId;
    /**
     * 资讯Id
     */
    String fileAttribution;

    public String getBelongId() {
        return belongId;
    }

    public void setBelongId(String belongId) {
        this.belongId = belongId;
    }

    public String getThemeId() {
        return themeId;
    }

    public void setThemeId(String themeId) {
        this.themeId = themeId;
    }

    public String getFileAttribution() {
        return fileAttribution;
    }

    public void setFileAttribution(String fileAttribution) {
        this.fileAttribution = fileAttribution;
    }
}

package com.lznbys.android.entity;


/**
 * 资讯收藏关系表
 *
 * app_article_sub
 */
public class ArticleSubEntity {
    /**
     * 资讯收藏唯一编号
     */
    private String articleSubId;
    /**
     * 资讯Id
     */
    private String fileAttribution;
    /**
     * 收藏时间
     */
    private String time;
    /**
     * 发起收藏者Id
     */
    private String userId;

    public ArticleSubEntity() {
    }

    public String getArticleSubId() {
        return articleSubId;
    }

    public void setArticleSubId(String articleSubId) {
        this.articleSubId = articleSubId;
    }

    public String getFileAttribution() {
        return fileAttribution;
    }

    public void setFileAttribution(String fileAttribution) {
        this.fileAttribution = fileAttribution;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

package com.lznbys.android.service;

import com.lznbys.android.entity.CommentEntity;

import java.util.List;

/**
 * 评论Service
 */
public interface CommentService {

    /**
     * 创建资讯
     *
     * @param commentEntity     评论信息
     * @return
     */
    boolean createComment(CommentEntity commentEntity);

    /**
     * 根据资讯Id获取该资讯所有评论
     *
     * @param fileAttribution   资讯id
     * @return
     */
    List<CommentEntity> findAllCommentByFileAttribution(String fileAttribution);
}

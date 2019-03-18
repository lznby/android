package com.lznbys.android.service.impl;

import com.lznbys.android.dao.CommentDao;
import com.lznbys.android.entity.CommentEntity;
import com.lznbys.android.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("comment")
public class CommentServiceImpl implements CommentService {

    private final CommentDao commentDao;

    @Autowired
    public CommentServiceImpl(CommentDao commentDao) {
        this.commentDao = commentDao;
    }

    /**
     * 添加评论
     *
     * @param commentEntity 评论信息
     * @return
     */
    @Override
    public boolean createComment(CommentEntity commentEntity) {
        int isInsert = commentDao.insertComment(commentEntity);
        return isInsert > 0;
    }

    /**
     * 根据资讯id查询所有评论
     *
     * @param fileAttribution 资讯id
     * @return
     */
    @Override
    public List<CommentEntity> findAllCommentByFileAttribution(String fileAttribution) {
        return commentDao.findAllCommentByFileAttribution(fileAttribution);
    }
}

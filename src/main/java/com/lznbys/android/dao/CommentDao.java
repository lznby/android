package com.lznbys.android.dao;

import com.lznbys.android.entity.CommentEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 评论表单, CURD接口类
 *
 * 对应表格 app_comment
 */
@Repository
public interface CommentDao {

    /**
     * 创建一条评论
     *
     * @param commentEntity    评论内容
     * @return
     */
    @Insert("INSERT INTO app_comment " +
            "(comment, userId, toUserId, fileAttribution, toCommentId, time) " +
            "VALUES (#{commentEntity.comment}, #{commentEntity.userId}, #{commentEntity.toUserId}, " +
            "#{commentEntity.fileAttribution}, #{commentEntity.toCommentId}, #{commentEntity.time});"
    )
    Integer insertComment(@Param("commentEntity")CommentEntity commentEntity);

    /**
     * 根据资讯Id查该资讯所有评论
     *
     * @param fileAttribution   评论资讯Id
     * @return
     */
    @Select("SELECT * FROM app_comment WHERE app_comment.fileAttribution = #{fileAttribution}")
    List<CommentEntity> findAllCommentByFileAttribution(String fileAttribution);

    /**
     * 根据评论Id更新该评论的点赞数
     *
     * @param commentId   评论id
     * @param loveCount   点赞数
     * @return
     */
    @Update("UPDATE app_comment " +
            "SET loveCount = #{loveCount} " +
            "WHERE commentId = #{commentId}")
    Integer updateComment(int commentId, int loveCount);
}

package com.lznbys.android.dao;

import com.lznbys.android.entity.FilePathEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 文件路径表 CURD接口类
 * app_file_path
 */
@Repository
public interface FilePathDao {

    /**
     * 新增文件位置
     *
     * @param filePathEntity    文件路径基本信息
     * @return
     */
    @Insert("INSERT INTO app_file_path " +
            "(fileId, filePath, fileType, filePrivate, fileAttribution, userId, time) " +
            "VALUES (#{filePathEntity.fileId},#{filePathEntity.filePath},#{filePathEntity.fileType},#{filePathEntity.filePrivate},#{filePathEntity.fileAttribution},#{filePathEntity.userId},#{filePathEntity.time})")
    Integer insertFilePath(@Param("filePathEntity")FilePathEntity filePathEntity);

    /**
     * 删除文件信息
     *
     * @param fileAttribution
     * @return
     */
    @Delete("DELETE FROM app_file_path WHERE app_file_path.fileAttribution = #{fileAttribution}")
    Integer deleteFilePath(@Param("fileAttribution") String fileAttribution);

    /**
     * 查询文件信息(根据归属编号查询用户信息)
     *
     * @param fileAttribution
     * @return
     */
    @Select("SELECT * FROM app_file_path WHERE app_file_path.fileAttribution = #{fileAttribution}")
    @ResultType(FilePathEntity.class)
    List<FilePathEntity> findFilePathByFileId(@Param("fileAttribution") String fileAttribution);

}

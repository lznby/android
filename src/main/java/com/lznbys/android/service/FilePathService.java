package com.lznbys.android.service;

import com.lznbys.android.entity.FilePathEntity;

import java.util.List;

/**
 * 文件路径信息
 */
public interface FilePathService {
    /**
     * 插入文件信息
     * @param filePathEntity
     * @return
     */
    boolean createFilePath(FilePathEntity filePathEntity);

    /**
     * 查询所有所属文件信息
     */
    List<FilePathEntity> findAllFilePathByFileAttribution(String fileAttribution);

}

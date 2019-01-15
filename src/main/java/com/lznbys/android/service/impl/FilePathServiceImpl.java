package com.lznbys.android.service.impl;

import com.lznbys.android.dao.FilePathDao;
import com.lznbys.android.entity.FilePathEntity;
import com.lznbys.android.service.FilePathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 文件信息实现类
 */
@Service("filePath")
public class FilePathServiceImpl implements FilePathService {

    private final FilePathDao filePathDao;

    /**
     * 依赖注入
     * @param filePathDao
     */
    @Autowired
    public FilePathServiceImpl(FilePathDao filePathDao) {
        this.filePathDao = filePathDao;
    }

    /**
     * 插入文件信息
     * @param filePathEntity
     * @return
     */
    @Override
    public boolean createFilePath(FilePathEntity filePathEntity) {
        int isInsert = filePathDao.insertFilePath(filePathEntity);
        return (isInsert>0) ;
    }

    /**
     * 根据文件归属编号查询文件信息
     * @param fileAttribution
     * @return
     */
    @Override
    public List<FilePathEntity> findAllFilePathByFileAttribution(String fileAttribution) {
        return filePathDao.findFilePathByFileId(fileAttribution);
    }
}

package com.lznbys.android.controller;

import com.lznbys.android.base.BaseResponseEntity;
import com.lznbys.android.base.ResponseCode;
import com.lznbys.android.entity.FilePathEntity;
import com.lznbys.android.service.FilePathService;
import com.lznbys.android.service.UserBaseInfoService;
import com.lznbys.android.utlis.FileUtils;
import com.lznbys.android.utlis.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/fileUpload")
public class FileUploadController {

    @Autowired
    private FilePathService filePathService;

    @Autowired
    private UserBaseInfoService userBaseInfoService;

    /**
     * 上传多文件并存储到数据库
     *
     * @param uploadingFiles
     * @return
     */
    @PostMapping("/uploadImage")
    public BaseResponseEntity uploadImage(@RequestParam("files") MultipartFile[] uploadingFiles) {
            // 1.所属编号
            String fileAttribution = UUID.randomUUID().toString();

            // 2.上传时间
            String uploadTime = TimeUtils.getNowTime();

            for(MultipartFile uploadedFile : uploadingFiles) {
                // 文件唯一编号
                String fileId = UUID.randomUUID().toString();

                // 文件名称
                String fileTruePath = fileId + FileUtils.getFileType(uploadedFile);
                File file = new File(FileUtils.getFilePath(fileTruePath));
                try {
                    // 保存文件
                    uploadedFile.transferTo(file);
                    // 文件信息对象
                    FilePathEntity filePathEntity = new FilePathEntity();
                    // 文件编号(含后缀)
                    filePathEntity.setFileId(fileTruePath);
                    // 存储路径
                    filePathEntity.setFilePath(FileUtils.getVisiblePath(fileTruePath));
                    // 归属编号
                    filePathEntity.setFileAttribution(fileAttribution);
                    // 文件类型
                    filePathEntity.setFileType(2);
                    // 公开私有
                    filePathEntity.setFilePrivate(1);
                    // 设置上传时间
                    filePathEntity.setTime(uploadTime);
                    // 设置上传者信息(数值型)
                    filePathEntity.setUserId("0");

                    filePathService.createFilePath(filePathEntity);
                } catch (IOException e) {
                    e.printStackTrace();
                    return new BaseResponseEntity(ResponseCode.REQUEST_FAIL_MSG, ResponseCode.FAIL);
                }
            }
            return new BaseResponseEntity(ResponseCode.REQUEST_SUCCESS_MSG, ResponseCode.SUCCESS);

    }

}

package com.lznbys.android.utlis;

import org.springframework.web.multipart.MultipartFile;

public class FileUtils {

    /**
     * 存储路径
     */
    private static String fileRoot = "/data/WWWroot/default/static/image/";
//    private static String fileRoot = "D:/";
    /**
     * 访问路径
     */
    private static String visitRoot = "http://39.108.138.218/static/image/";
//    private static String visitRoot = "D:/";

    /**
     * OSS图片访问路径
     */
    private static String ossRoot = "https://lznby-image.oss-cn-shenzhen.aliyuncs.com/";

    /**
     * 获取文件后缀
     */
    public static String getFileType (MultipartFile file) {
        // 1.获取文件类型
        String filePath = (file.getContentType()).replace('/','.');
        // 2.获取文件后缀名
        String fileType = filePath.substring(filePath.indexOf("."));
        return fileType;
    }

    /**
     * 获取文件存储路径
     */
    public static String getFilePath (String path) {
        return fileRoot + path;
    }

    /**
     * 获取文件访问路径
     */
    public static String getVisiblePath (String path) {
        return visitRoot + path;
    }

    /**
     * 获取OSS完整的访问路径
     */
    public static String getOssFilePath(String path) {
        return ossRoot + path;
    }

    /*********************************************以上为配置信息*********************************************************/
    /*********************************************以下为工具方法*********************************************************/


    public static String getFileContentType(String fileName) {
        String subType = getSubType(fileName);
        String type = getType(fileName);
        return type + "/" + subType;
    }

    /**
     * Returns the high-level media type, such as "text", "image", "audio", "video", or "application".
     * @return
     */
    public static String getType(String fileName) {
        String subType = getSubType(fileName);
        if ("png".equals(subType) || "gif".equals(subType) || "jpeg".equals(subType) || "bmp".equals(subType) || "jpg".equals(subType)) {
            return "image";
        } else if ("mp4".equals(subType) || "avi".equals(subType) || "flv".equals(subType) || "3gp".equals(subType) || "wmv".equals(subType) || "mov".equals(subType)) {
            return "video";
        } else if ("mp3".equals(subType) || "wma".equals(subType) || "flac".equals(subType) || "wav".equals(subType) || "ape".equals(subType)) {
            return "audio";
        } else {
            return "multipart";
        }
    }

    /**
     * Returns a specific media subtype, such as "plain" or "png", "mpeg", "mp4" or "xml".
     *
     * @return
     */
    public static String getSubType(String fileName) {
        return (fileName.substring(fileName.lastIndexOf("."),fileName.length())).replaceFirst(".","");
    }

}

package com.lznbys.android.base;

/**
 * 请求状态返回状态码
 */
public interface ResponseCode {
    /**
     * 请求成功
     */
    int SUCCESS = 1;

    /**
     * 请求失败
     */
    int FAIL = -1;

    /**
     * cookies 过期
     */
    int UN_LOGIN = 0;

    /**
     * 其他错误
     */
    int ERROR = 2;


    /**
     * 用户名和密码不能为空
     */
    String NICKNAME_IS_NULL = "用户名和密码不能为空";

    /**
     * 该昵称已被注册
     */
    String NICKNAME_IS_HAVE = "该用户昵称已被注册";

    /**
     * 账号创建失败
     */
    String ACCOUNT_CREATE_FAIL = "账号已被注册";

    /**
     * 账号创建成功
     */
    String ACCOUNT_CREATE_SUCCESS = "账号创建成功";

    /**
     * 请求成功
     */
    String REQUEST_SUCCESS_MSG = "请求成功";

    /**
     * 请求失败
     */
    String REQUEST_FAIL_MSG = "请求失败";

    /**
     * 不存在该主题
     */
    String HAVENT_THEME = "主题记录失败";

    /**
     * 主题名已被占用
     */
    String THEME_NAME_HAVE = "主题名已被占用";

    /**
     * 文件上传异常
     */
    String FILE_UPLOAD_ERROR = "文件上传异常";

    /**
     * 资讯保存失败
     */
    String ARTICLE_SAVE_FAIL = "资讯保存失败";

    /**
     * 昵称不能为空
     */
    String NAME_IS_NULL = "昵称不能为空";

    /**
     * 密码错误
     */
    String PASSWORD_ERROR = "密码错误";

    /**
     * 密码不能为空
     */
    String PASSWORD_IS_NULL = "密码不能为空";

    /**
     * 两次密码不相同
     */
    String PASSWORD_IS_DIFFERENT = "两次密码不相同";

    /**
     * 用户不存在
     */
    String USER_NOT_FIND = "用户不存在";

    /**
     * Cookies过期
     */
    String COOKIES_OUT_TIME = "Cookies过期,请重新登录！";

    /**
     * 已经关注
     */
    String ALREADY_FOLLOW = "当前对象已将在您的关注列表中！";

    /**
     * 没有关注
     */
    String NOT_FOLLOWER = "当前对象未在关注列表当中";

    /**
     * 创建失败
     */
    String CREATE_FILE = "创建失败";

    /**
     * 主题不存在
     */
    String THEME_HAVE_ONT = "主题不存在";
}

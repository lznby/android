package com.lznbys.android.utlis;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间工具类
 */
public class TimeUtils {

    /**
     * 获取当前时间年月日时分秒
     *
     * @return
     */
    public static String getNowTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(new Date());
    }
}

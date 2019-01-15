package com.lznbys.android.base;

/**
 * 自定义异常,用来全局处理异常,并返回json
 */
public class BaseException extends Exception{

    public BaseException(String message) {
        super(message);
    }

}

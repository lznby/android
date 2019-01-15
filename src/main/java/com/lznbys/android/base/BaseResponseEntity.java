package com.lznbys.android.base;

/**
 * @author Lznby
 * @apiNote 响应的基础
 */
public class BaseResponseEntity<T> {
    /**
     * 错误消息
     */
    private String message;
    /**
     * 错误代码
     */
    private int code;
    /**
     * 数据
     */
    private T data;

    public BaseResponseEntity() {
    }

    public BaseResponseEntity(String message, int code) {
        this.message = message;
        this.code = code;
    }

    public BaseResponseEntity(String message, int code, T data) {
        this.message = message;
        this.code = code;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

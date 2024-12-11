package com.litiron.code.lineage.sql.common;

/**
 * @description: 自定义程序统一返回结构体
 * @author: Litiron
 * @create: 2024-03-17 12:12
 **/
public class Rest<T> {

    /**
     * 状态码
     */
    private int code;

    /**
     * 数据总数
     */
    private Long count;

    /**
     * 消息
     */
    private String message;

    /**
     * 数据
     */
    private T data;

    public Rest() {
    }

    public Rest(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Rest(int code, String message, T data, Long count) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.count = count;
    }

    /**
     * 成功返回
     *
     * @param data 数据
     * @param <T>  数据类型
     * @return 返回结果
     */
    public static <T> Rest<T> success(T data) {
        return new Rest<>(200, null, data);
    }

    public static <T> Rest<T> success(String message) {
        return new Rest<>(200, message, null);
    }

    public static <T> Rest<T> success(T data, String message) {
        return new Rest<>(200, message, data);
    }

    public static <T> Rest<T> success(T data, Long count) {
        return new Rest<>(200, null, data, count);
    }

    public static <T> Rest<T> success() {
        return new Rest<>(200, "success", null);
    }

    public static <T> Rest<T> error(int code, String message) {
        return new Rest<>(code, message, null);
    }

    public static <T> Rest<T> error(String message) {
        return new Rest<>(500, message, null);
    }


    public static <T> Rest<T> error(String message, T data) {
        return new Rest<>(500, message, data);
    }

    public static <T> Rest<T> error(int code, String message, T data) {
        return new Rest<>(code, message, data);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

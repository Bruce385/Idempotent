package com.pay.common.message;

import lombok.Data;

/**
 * @ClassName: JsonResult
 * @Description: 统一返回对象
 * @author: Bruce cyc
 * @date: 2020/11/30 1:46
 * @Copyright:
 */
@Data
public class JsonResult<T> {

    /**
     * 通信数据
     */
    private T data;
    /**
     * 通信状态
     */
    private boolean flag = true;
    /**
     * 状态码，0000代表无错误，错误代码应该用枚举定义。
     */
    private String code;
    /**
     * 通信描述
     */
    private String msg = "";

    /**
     * 通过静态方法获取实例
     */
    public static <T> JsonResult<T> of(T data) {
        return new JsonResult<>(data);
    }

    public static <T> JsonResult<T> of(T data, boolean flag) {
        return new JsonResult<>(data, flag);
    }

    public static <T> JsonResult<T> of(T data, boolean flag, String msg) {
        return new JsonResult<>(data, flag, msg);
    }

    public static <T> JsonResult<T> of(T data, boolean flag, String code, String msg) {
        return new JsonResult<>(data, flag, code, msg);
    }

    @Deprecated
    public JsonResult() {

    }

    private JsonResult(T data) {
        this.data = data;
    }

    private JsonResult(T data, boolean flag) {
        this.data = data;
        this.flag = flag;
    }

    private JsonResult(T data, boolean flag, String msg) {
        this.data = data;
        this.flag = flag;
        this.msg = msg;
    }

    private JsonResult(T data, boolean flag, String code, String msg) {
        this.data = data;
        this.flag = flag;
        this.code = code;
        this.msg = msg;
    }
}

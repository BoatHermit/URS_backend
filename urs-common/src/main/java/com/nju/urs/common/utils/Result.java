package com.nju.urs.common.utils;

import com.nju.urs.common.enums.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Data;


/**
 * API统一返回结果类
 */
@Data
@AllArgsConstructor
public class Result {

    private boolean success;
    private int code;
    private String msg;
    private Object data;

    private Result(boolean success, int code, String msg) {
        this.success = success;
        this.code = code;
        this.msg = msg;
    }

    private Result(boolean success, ResultCode resultCode) {
        this.success = success;
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
    }

    private Result(boolean success, ResultCode resultCode, Object data) {
        this.success = success;
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
        this.data = data;
    }

    public static Result success() {
        return new Result(true, ResultCode.SUCCESS);
    }

    public static Result success(Object data) {
        return new Result(true, ResultCode.SUCCESS, data);
    }

    public static Result fail() {
        return new Result(false, ResultCode.FAILED);
    }

    public static Result fail(ResultCode resultCode) {
        return new Result(false, resultCode);
    }

    public static Result fail(int code, String message) {
        return new Result(false, code, message);
    }
}

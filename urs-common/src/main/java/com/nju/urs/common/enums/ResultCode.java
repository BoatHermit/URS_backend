package com.nju.urs.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * API返回的状态码表
 */
@AllArgsConstructor
@Getter
public enum ResultCode {

    // 请求成功
    SUCCESS(200, "请求成功"),
    // 请求失败
    FAILED(201, "操作失败"),
    // token失效
    TOKEN_FAILED(202, "用户登陆已过期"),
    // 系统异常
    SYSTEM_FAILED(203,"系统异常"),

    //登录注册异常
    PARAMS_ERROR(10001,"用户名或密码不能为空"),
    ACCOUNT_PWD_NOT_EXIST(10002,"用户名或密码错误"),
    ACCOUNT_EXIST(10004,"账号已存在"),
    LOGIN_FAIL(10003,"登陆失败"),

    NO_PERMISSION(70001,"无访问权限"),
    SESSION_TIME_OUT(90001,"会话超时"),
    NO_LOGIN(90002,"未登录");

    private final int code;
    private final String msg;
}

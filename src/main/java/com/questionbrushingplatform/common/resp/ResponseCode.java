package com.questionbrushingplatform.common.resp;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 状态码枚举
 * @author wenruohan
 */
@Getter
@AllArgsConstructor
public enum ResponseCode implements BaseExceptionInterface {
    SUCCESS(0, "响应成功" ),
    NO_LOGIN(1000,"没有登陆"),
    NO_SAVE(4100,"保存失败"),
    SYSTEM_ERROR(5000,"系统错误"),
    NO_AUTH(1234,"没有权限"),
    NO_QUERY(3255,"没有找到"),
    PARAMETER_ERROR(999,"参数错误"),
    OVER_FLOW(777,"栈溢出"),
    OUT_OF_BOUNDS(12306,"数组越界"),
    SQL_Syntax_ERROR(12306,"SQL语法错误"),
    HTTP_ERROR(1001,"HTTP请求错误"),
    RPC_ERROR(1001,"RPC调用错误"),
    NO_DATA(1001,"没有数据"),
    REDIS_FAIL(1002,"redis链接失败"),
    ALREADY_EXIST(1003,"数据已存在"),
    PARAM_NOT_EXIST(1004,"参数不存在"),
    ALREADY_EXIST_MESSAGE (1005,"数据已存在"),
    QUESTION_BANK_NOT_EMPTY(1006,"该题库中存在题目，无法删除")
    ;


    private final int code;

    private final String message;
}

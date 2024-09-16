package com.questionbrushingplatform.common.resp;

import lombok.Data;

import java.io.Serializable;

/**
 * 基础响应类
 * @author wenruohan
 */
@Data
public class BaseResponse<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private int code;

    private String message;

    private T data;

    public BaseResponse(int code, String msg, T data) {
        this.code = code;
        this.message = msg;
        this.data = data;
    }

    public BaseResponse(int code, String msg) {
        this.code = code;
        this.message = msg;
    }

    public BaseResponse(ResponseCode code) {
        this.code = code.getCode();
        this.message = code.getMessage();
    }

    public BaseResponse(ResponseCode code,T data) {
        this.code = code.getCode();
        this.message = code.getMessage();
        this.data = data;
    }
}

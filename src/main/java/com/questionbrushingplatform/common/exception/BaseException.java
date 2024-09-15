package com.questionbrushingplatform.common.exception;

/**
 * 业务异常
 * @author 永
 */
public class BaseException extends RuntimeException {

    public BaseException() {
    }

    public BaseException(String msg) {
        super(msg);
    }
}
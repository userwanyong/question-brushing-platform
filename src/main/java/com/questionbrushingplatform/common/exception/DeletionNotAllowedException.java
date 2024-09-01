package com.questionbrushingplatform.common.exception;

/**
 * 不允洗删除异常
 */
public class DeletionNotAllowedException extends BaseException {

    public DeletionNotAllowedException(String msg) {
        super(msg);
    }

}
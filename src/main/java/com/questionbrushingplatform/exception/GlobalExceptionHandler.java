package com.questionbrushingplatform.exception;


import cn.dev33.satoken.util.SaResult;
import com.questionbrushingplatform.common.constant.MessageConstant;
import com.questionbrushingplatform.common.resp.BaseResponse;
import com.questionbrushingplatform.common.resp.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 * @author 永
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获业务异常
     * @param ex
     * @return
     */
//    @ExceptionHandler
//    public BaseResponse<String> exceptionHandler(BaseException ex){
//        log.error("异常信息：{}", ex.getMessage());
//        return new BaseResponse<>(ResponseCode.SYSTEM_ERROR, ex.getMessage());
////        return Result.error(ex.getMessage());
//    }

    /**
     * 处理SQL异常
     * @param ex
     * @return
     */
    @ExceptionHandler
    public BaseResponse<String> exceptionHandler(SQLIntegrityConstraintViolationException ex){
        //Duplicate entry 'leiyi' for key 'employee.idx_username'
        String message = ex.getMessage();
        if (message.contains("Duplicate entry")){
            String[] split = message.split(" ");
            String username = split[2];
            return new BaseResponse<>(ResponseCode.SYSTEM_ERROR, username+ MessageConstant.ALREADY_EXISTS);
//            return Result.error(username+ MessageConstant.ALREADY_EXISTS);
        }else {
            return new BaseResponse<>(ResponseCode.SYSTEM_ERROR);
//            return Result.error(MessageConstant.UNKNOWN_ERROR);
        }

    }

    /**
     * 处理全局saToken异常
     * @param e
     * @return
     */
    @ExceptionHandler
    public SaResult handlerException(Exception e) {
        e.printStackTrace();
        return SaResult.error(e.getMessage());
    }

}

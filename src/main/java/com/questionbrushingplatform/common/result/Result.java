//package com.questionbrushingplatform.common.result;
//
//import lombok.Data;
//
//import java.io.Serializable;
//
///**
// * 后端统一返回结果
// * @author 永
// * @param <T>
// */
//@Data
//public class Result<T> implements Serializable {
//
//    private Integer code; //编码：1成功，0和其它数字为失败
//    private String error_msg; //错误信息
//    private T data; //数据
//
//    public static <T> Result<T> success() {
//        Result<T> result = new Result<T>();
//        result.code = 1;
//        return result;
//    }
//
//    public static <T> Result<T> success(T object) {
//        Result<T> result = new Result<T>();
//        result.data = object;
//        result.code = 1;
//        return result;
//    }
//
//    public static <T> Result<T> error(String error_msg) {
//        Result result = new Result();
//        result.error_msg = error_msg;
//        result.code = 0;
//        return result;
//    }
//
//}

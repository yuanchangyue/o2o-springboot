package com.changyue.o2o.exceptions;

/**
 * @program: o2o
 * @description: 平台账号操作异常类
 * @author: YuanChangYue
 * @create: 2019-09-24 22:11
 */
public class LocalAuthOperationException extends RuntimeException {
    public LocalAuthOperationException(String message) {
        super(message);
    }
}

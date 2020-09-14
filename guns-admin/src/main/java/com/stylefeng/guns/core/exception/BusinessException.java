package com.stylefeng.guns.core.exception;

import com.stylefeng.guns.core.common.exception.BizExceptionEnum;

public class BusinessException extends RuntimeException{

    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
    }
    public BusinessException(BizExceptionEnum bizExceptionEnum) {
        super(bizExceptionEnum.getMessage());
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    protected BusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

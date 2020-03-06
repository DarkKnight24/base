package com.movie.base.exception.impl;


import com.movie.base.exception.IBusinessException;

/**
 * 系统发生业务类的异常，直接显示给用户
 * <p>
 * 不需要记录日志或者提示相关人员
 */
public class BusinessException extends RuntimeException implements IBusinessException {
    private static final long serialVersionUID = 4841002170027340733L;

    public BusinessException() {
        super();
    }

    public BusinessException(final String message) {
        super(message);
    }

    public BusinessException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public BusinessException(final Throwable cause) {
        super(cause);
    }

}

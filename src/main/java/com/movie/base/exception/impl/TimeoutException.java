package com.movie.base.exception.impl;

import com.movie.base.exception.ITimeoutException;

import javax.servlet.http.HttpServletResponse;

/**
 * 用户登录超时的异常，直接显示给用户
 * <p>
 * 不需要记录日志或者提示相关人员
 */
public class TimeoutException extends RuntimeException implements ITimeoutException {
    private static final long serialVersionUID = -1217490019899825218L;

    public TimeoutException() {
        super();
    }

    public TimeoutException(final String message) {
        super(message);
    }

    public TimeoutException(final String message, HttpServletResponse response) {
        super(message);
    }

    public TimeoutException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public TimeoutException(final Throwable cause) {
        super(cause);
    }
}

package com.movie.base.exception.impl;

import com.movie.base.exception.IParamException;

/**
 * 系统参数异常，直接显示给用户
 * <p>
 * 不需要记录日志或者提示相关人员
 */
public class ParamException extends RuntimeException implements IParamException {
    private static final long serialVersionUID = -4404703264042956031L;

    public ParamException() {
        super();
    }

    public ParamException(final String message) {
        super(message);
    }

    public ParamException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ParamException(final Throwable cause) {
        super(cause);
    }
}

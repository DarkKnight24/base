package com.movie.base.utils;

import com.movie.base.exception.IBusinessException;
import com.movie.base.exception.IParamException;
import com.movie.base.exception.impl.BusinessException;
import com.movie.base.exception.impl.ParamException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.validation.BindException;

import java.io.IOException;
import java.sql.SQLException;

import static com.movie.base.utils.StringUtil.format;
import static com.movie.base.utils.Util.isEmpty;

/**
 * 异常处理工具类
 */
public final class ExceptionUtil {

    /**
     * 根据不同的异常种类，返回对应中文意义
     * <p>
     * 对于weException直接返回message，对于其他常见异常返回常用信息
     *
     * @param th 异常
     * @return 异常信息
     */
    public static String getSimpleMessage(final Throwable th) {
        if (Util.isEmpty(th)) {
            return "";
        }
        if (th instanceof IBusinessException || th instanceof IParamException) {
            return th.getMessage();
        } else if (th instanceof NullPointerException) {
            return "调用了未经初始化的对象或者是不存在的对象！";
        } else if (th instanceof IOException) {
            return "IO异常！";
        } else if (th instanceof ClassNotFoundException) {
            return "指定的类不存在！";
        } else if (th instanceof ArithmeticException) {
            return "数学运算异常！";
        } else if (th instanceof ArrayIndexOutOfBoundsException) {
            return "数组下标越界!";
        } else if (th instanceof IllegalArgumentException) {
            return "方法的参数错误！";
        } else if (th instanceof ClassCastException) {
            return "类型强制转换错误！";
        } else if (th instanceof SecurityException) {
            return "违背安全原则异常！";
        } else if (th instanceof SQLException) {
            return "操作数据库异常！";
        } else if (th instanceof NoSuchMethodError) {
            return "方法未找到异常！";
        } else if (th instanceof TypeMismatchException) {
            return "参数类型不匹配！";
        } else if (th instanceof BindException) {
            return "参数数据绑定失败！";
        } else if (th instanceof InternalError) {
            return "Java虚拟机发生了内部错误";
        } else if (th instanceof IllegalStateException) {
            return getIllegalStateExceptionMessage(th);
        } else if (th instanceof RuntimeException) {
            return getRuntimeExceptionMessage(th);
        } else {
            return getOtherExceptionMessage(th);
        }
    }

    /**
     * 参数为空异常
     *
     * @param th
     * @return
     */
    private static String getIllegalStateExceptionMessage(Throwable th) {
        String message = th.toString();
        //整形参数为空异常：Optional long parameter 'id' is present but cannot be translated into a null value due to being declared as a primitive type. Consider declaring it as object wrapper for the corresponding primitive type.
        if (message.contains("is present but cannot be translated into a null value")) {
            int startIndex = message.indexOf(" '") + 1;
            int endIndex = message.indexOf("' ") + 1;
            String param = "";
            if (endIndex > startIndex) {
                param = message.substring(startIndex, endIndex);
            } else {
                message = "程序内部错误，操作失败！";
            }
            message = "参数：" + param + " 不可为空！";
        } else {
            message = "程序内部错误，操作失败！";
        }
        return message;
    }

    /**
     * 得到其他异常信息
     *
     * @param th
     * @return
     */
    private static String getOtherExceptionMessage(Throwable th) {
        String message = th.toString();
        if (message.contains("NoHandlerFoundException")) {
            message = "无法处理的请求！请检查请求URL";
        } else if (message.contains("com.netflix.zuul.exception.ZuulException: Filter threw Exception")
        ) {
            message = "服务中断，操作失败！";
        } else {
            message = "程序内部错误，操作失败！";
        }
        return message;
    }

    /**
     * 得到运行时异常信息
     *
     * @param th
     * @return
     */
    private static String getRuntimeExceptionMessage(Throwable th) {
        String message = th.getMessage();
        if (!Util.isEmpty(message)) {
            int startIndex = message.indexOf(":") + 1;
            int endIndex = message.indexOf("\r");
            if (endIndex == -1) {
                endIndex = message.indexOf("\n");
            }
            if (endIndex > startIndex) {
                message = message.substring(startIndex, endIndex);
            } else {
                if (message.contains("NoHandlerFoundException")) {
                    message = "没有找到资源！";
                } else {
                    message = "程序内部错误，操作失败！";
                }
            }
        } else {
            message = "程序内部错误，操作失败！";
        }
        return message;
    }

    /**
     * 生成businessException
     *
     * @param message 异常信息
     * @return 业务异常
     */
    public static BusinessException bEx(final String message, final Object... args) {
        return new BusinessException(format(message, args));
    }

    /**
     * 生成businessException
     *
     * @param message 异常信息
     * @param e       包装的其他异常
     * @return 业务异常
     */
    public static BusinessException bEx(final String message, final Throwable e, final Object... args) {
        return new BusinessException(format(message, args), e);
    }

    /**
     * 生成IParamException
     *
     * @param message 异常信息
     * @return 数据异常
     */
    public static ParamException pEx(final String message, final Object... args) {
        return new ParamException(format(message, args));
    }

    /**
     * 生成IParamException
     *
     * @param message 异常信息
     * @param e       包装的其他异常
     * @return 数据异常
     */
    public static ParamException pEx(final String message, final Throwable e, final Object... args) {
        return new ParamException(format(message, args), e);
    }

    /**
     * 判断是否为空，如果为空则抛出业务异常
     *
     * @param obj     判断对象
     * @param msg     提示信息
     * @param objects 对象
     */
    public static void checkNull(final Object obj, final String msg, final Object... objects) {
        if (obj == null) {
            throw pEx(msg, objects);
        }
    }

    /**
     * 判断主键是否为空
     * 其实主要是用于参数校验的时候校验主键合法性
     *
     * @param id      主键
     * @param msg     提示信息
     * @param objects 对象列表
     * @see #checkId(long, String)
     */
    @Deprecated
    public static void checkId(final long id, final String msg, final Object... objects) {
        if (id <= 0) {
            throw pEx(msg, objects);
        }
    }

    /**
     * 判断主键是否为空
     * <p>
     * <pre>
     * 		ExceptionUtil.checkId(userId,"用户");
     * 		如果有异常，异常信息：用户id:2不允许为空
     * </pre>
     *
     * @param id  主键
     * @param msg 提示信息
     */
    public static void checkId(final long id, final String msg) {
        if (id <= 0) {
            throw pEx(msg + "id:{0}不允许为空", id);
        }
    }

    /**
     * 判断是否为空，如果为空则抛出数据异常
     *
     * @param obj 判断对象
     * @param msg 提示信息
     */
    public static void checkNullBEx(final Object obj, final String msg, final Object... objects) {
        if (obj == null) {
            throw pEx(msg, objects);
        }
    }

    /**
     * 判断是否为空，如果为空则抛出业务异常
     *
     * @param obj 判断对象
     * @param msg 提示信息
     */
    public static void checkEmptyBEx(final Object obj, final String msg, final Object... objects) {
        if (isEmpty(obj)) {
            throw bEx(msg, objects);
        }
    }

    /**
     * 判断是否为空，如果为空则抛出数据异常
     *
     * @param obj 判断对象
     * @param msg 提示信息
     */
    public static void checkEmpty(final Object obj, final String msg, final Object... objects) {
        if (isEmpty(obj)) {
            throw pEx(msg, objects);
        }
    }
}

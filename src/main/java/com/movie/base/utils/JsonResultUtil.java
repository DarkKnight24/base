package com.movie.base.utils;

import com.movie.base.exception.IBusinessException;
import com.movie.base.exception.ITimeoutException;
import org.springframework.validation.BindException;

import java.util.Map;

/**
 * json结果工具
 * <p>
 * 用于返回结果为json结果的工具。
 * 主要用于前后端数据对接
 */
public class JsonResultUtil {
    private static final String EMPTY = "";
    private static final String DATA = "data";
    /**
     * 消息Key
     */
    private static final String MESSAGE_KEY = "message";
    /**
     * 编码Key
     */
    private static final String CODE_KEY = "code";

    /**
     * 同dwz框架配合的状态返回值
     * <p>
     * SUCCESS 成功
     * FAIL 失败
     * TIMEOUT 用户登录信息超时
     *
     * @author 庄君祥
     * @Date 2012-5-1
     */
    public static enum StatusCode {
        SUCCESS("200", "成功"), FAIL("300", "失败"), TIMEOUT("301", "超时"), FAIL_BUSINESS("302", "业务失败");
        private String key;
        private String value;

        private StatusCode(final String key, final String value) {
            this.key = key;
            this.value = value;
        }

        public String key() {
            return key;
        }

        public String value() {
            return value;
        }
    }

    /**
     * 构建成功后的返回对象
     * <p>
     * 消息为空时，不提示，不为空则进行提示
     *
     * @param data 成功消息
     * @return json对象
     */
    public static Map<String, Object> data(final Object data) {
        Map<String, Object> map = MapUtil.map();
        map.put(CODE_KEY, StatusCode.SUCCESS.key());
        map.put(MESSAGE_KEY, EMPTY);
        if (!Util.isEmpty(data)) {
            map.put(DATA, data);
        }
        return map;
    }

    /**
     * 构建成功后的返回对象
     * <p>
     * 消息为空时，不提示，不为空则进行提示
     *
     * @param message 成功消息
     * @return json对象
     */
    public static Map<String, Object> success(final String message) {
        Map<String, Object> map = MapUtil.map();
        map.put(CODE_KEY, StatusCode.SUCCESS.key());
        map.put(MESSAGE_KEY, message);
        map.put(DATA, EMPTY);
        return map;
    }

    /**
     * 构建超时返回json对象
     *
     * @return 超时json对象
     */
    public static Map<String, String> timeOut(final String message) {
        Map<String, String> map = MapUtil.map();
        map.put(CODE_KEY, StatusCode.TIMEOUT.key());
        if (Util.isEmpty(message)) {
            map.put(MESSAGE_KEY, "登录超时，请重新登录");
        } else {
            map.put(MESSAGE_KEY, message);
        }
        return map;
    }

    /**
     * 返回错误信息
     * <p>
     * 返回错误信息，并根据情况确定是否需要关闭窗口
     *
     * @param message
     * @return 错误json对象
     */
    public static Map<String, String> error(final String message) {
        return error(message, StatusCode.FAIL.key());
    }

    /**
     * 返回错误信息
     * <p>
     * 返回错误信息，并根据情况确定是否需要关闭窗口
     *
     * @param message
     * @return 错误json对象
     */
    public static Map<String, String> error(final String message, String code) {
        Map<String, String> map = MapUtil.map();
        map.put(CODE_KEY, code);
        map.put(MESSAGE_KEY, message);
        return map;
    }

    /**
     * 将异常转换为错误信息
     *
     * @param th 异常
     * @return 错误信息json对象
     */
    public static Map<String, String> toJsonMap(final Throwable th) {
        if (th instanceof ITimeoutException) {
            return timeOut(th.getMessage());
        }
        if (th instanceof IBusinessException) {
            return error(ExceptionUtil.getSimpleMessage(th), StatusCode.FAIL_BUSINESS.key());
        }
        if (th instanceof BindException) {
//			BindException bindException = (BindException)th ;
            return error(ExceptionUtil.getSimpleMessage(th) + ((BindException) th).getBindingResult().toString(), StatusCode.FAIL_BUSINESS.key());
        }
        return error(ExceptionUtil.getSimpleMessage(th), StatusCode.FAIL.key());
    }
}
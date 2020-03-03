/**
 * AspUtil.java
 * com.we.core.web.util
 * Copyright (c) 2017, 北京聚智未来科技有限公司版权所有.
 */

package com.movie.base.utils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * 切面工具
 *
 * @author ZhuangJunxiang(529272571 @ qq.com)
 * @Date 2017年7月20日
 */
public class AspectUtil {
    /**
     * 获取方法
     *
     * @param joinPoint 加入点
     * @return 方法
     */
    public static Method getMethod(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        return methodSignature.getMethod();
    }
}

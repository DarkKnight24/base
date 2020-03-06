package com.movie.base.aop;

import com.movie.base.utils.AspectUtil;
import com.movie.base.utils.JsonResultUtil;
import com.movie.base.utils.JsonUtil;
import com.movie.base.utils.Util;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * json返回切面
 * <p>
 * 用于处理json返回结果
 *
 * @author Mengcc
 */
@Component
@Aspect
@Order(2)
public class JsonReturnAspect {
    /**
     * 设置分页默认值
     * <p>
     * 如果分页没有设置值，则默认从系统的配置文件里读取
     *
     * @param pjp 切点
     */
    @Around(value = "@annotation(org.springframework.web.bind.annotation.ResponseBody)")
    @Order(1)
//	@Pointcut("!execution(* springfox.documentation.swagger.web.ApiResourceController..*.*(..))")
    public Object warp(final ProceedingJoinPoint pjp) throws Throwable {
        Object list = pjp.proceed();

        if (excludeSwagger(list)) {
            return list;
        }

        if (isReturnVoid(pjp)) {
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            if (requestAttributes == null) {
                return list;
            }
            HttpServletResponse response = ((ServletRequestAttributes) requestAttributes)
                    .getResponse();
            response.setContentType("application/json;charset=UTF-8");
            if (isNeedWrap(pjp)) {
                response.getWriter().write(JsonUtil.toJson(JsonResultUtil.data("操作成功")));
            }
            return list;
        }

        return JsonResultUtil.data(list);
    }

    /**
     * 排除swagger
     * <p>
     * 排除swagger框架中对象
     * </p>
     *
     * @param list
     * @return
     */
    private boolean excludeSwagger(Object list) {
        if (!Util.isEmpty(list)) {
            if (list.toString().contains("springfox.documentation.swagger.web")) {
                return true;
            }
            if (list instanceof ResponseEntity && !Util.isEmpty(((ResponseEntity) list).getBody())) {
                String claz = ((ResponseEntity) list).getBody().getClass().toString();
                if (claz.contains("springfox.documentation.swagger.web")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 是否需要包裹
     *
     * @param pjp 切点
     * @return true表示不需要
     */
    private boolean isNeedWrap(final ProceedingJoinPoint pjp) {
        Method method = AspectUtil.getMethod(pjp);
        return true;
    }

    /**
     * 是否返回空
     *
     * @param pjp
     * @return true:返回类型为void，false：返回类型不是void
     */
    private boolean isReturnVoid(ProceedingJoinPoint pjp) {
        Method method = AspectUtil.getMethod(pjp);
        Class<?> returnType = method.getReturnType();
        return "void".equals(returnType.getName());
    }
}

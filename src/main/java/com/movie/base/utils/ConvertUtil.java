package com.movie.base.utils;

import org.nutz.castor.Castors;
import org.nutz.castor.FailToCastObjectException;
import org.nutz.lang.Lang;
import org.nutz.lang.Mirror;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;

import static org.nutz.castor.Castors.me;

/**
 * 类型转换工具类
 */
public final class ConvertUtil {

    /**
     * 对象转换成字符串
     *
     * @param o 对象
     * @return 字符串, 如果为null，则返回空串
     */
    public static String obj2str(final Object o) {
        if (o == null) {
            return "";
        }
        return me().castToString(o);
    }

    /**
     * 对象转换成long值
     *
     * @param o 对象
     * @return 对应值，如果转换异常或为null，则返回-1
     */
    public static long obj2long(final Object o) {
        if (o == null) {
            return -1;
        }
        Long re = null;
        try {
            re = me().castTo(o, Long.class);
            if (re == null) {
                return -1;
            }
        } catch (Exception e) {
            return -1;
        }
        return re;
    }

    /**
     * 对象转换成int值
     *
     * @param o 对象
     * @return 对应值，如果转换异常或为null，则返回-1
     */
    public static int obj2int(final Object o) {
        if (o == null) {
            return -1;
        }
        Integer re = null;
        try {
            re = me().castTo(o, Integer.class);
            if (re == null) {
                return -1;
            }
        } catch (Exception e) {
            return -1;
        }
        return re;
    }

    /**
     * 对象转换成long值
     *
     * @param o 对象
     * @return 对应值，如果转换异常或为null，则返回-1
     */
    public static double obj2double(final Object o) {
        if (o == null) {
            return -1;
        }
        Double re = null;
        try {
            re = me().castTo(o, Double.class);
            if (re == null) {
                return -1;
            }
        } catch (Exception e) {
            return -1;
        }
        return re;
    }

    /**
     * 通用的将对象转换为map的方法
     * <p>
     * 只将对象的原生属性转换为map的String,
     * 如果是数组、list、map、pojo等则不处理
     *
     * @param obj 待转化的对象
     * @return map
     */
    public static Map<String, String> obj2SimpleMap(final Object obj) {
        return obj2Map(obj, true);
    }

    /**
     * 通用的将对象转换为map的方法
     * <p>
     * 如果isSimple为true，则支持数组、list、map、pojo等处理<br/>
     * 否则则不支持
     *
     * @param obj 待转化的对象
     * @param isSimple 是否简单处理
     * @return map
     */
    /**
     * 通用的将对象转换为map的方法
     * <p>
     * 如果isSimple为true，则支持数组、list、map、pojo等处理<br/>
     * 否则则不支持
     *
     * @param obj      待转化的对象
     * @param isSimple 是否简单处理
     * @return map
     */
    private static Map<String, String> obj2Map(final Object obj, final boolean isSimple) {
        Mirror<?> mirror = Mirror.me(obj.getClass());
        Field[] flds = mirror.getFields();
        Map<String, String> map = MapUtil.map();
        for (Field fld : flds) {
            Object v = mirror.getValue(obj, fld);
            if (Util.isEmpty(v)) {
                continue;
            }
            Mirror<?> mr = Mirror.me(fld.getType());

            if (isSimple && (mr.isContainer() || mr.isPojo())) {
                continue;
            }
            if (mr.isNumber() || mr.isBoolean() || mr.isChar() || mr.isStringLike() || mr.isEnum()
                    || mr.isDateTimeLike()) {
                map.put(fld.getName(), Castors.me().castToString(v));
            } else {
                map.put(fld.getName(), JsonUtil.toJson(v));
            }
        }
        return map;
    }

    private static Object isUser() {

        // TODO Auto-generated method stub
        return null;

    }

    /**
     * 通用的将对象转换为map的方法
     * <p>
     * 只将对象的原生属性转换为map的String,
     * 同时支持数组、list、map、pojo等处理
     *
     * @param obj 待转化的对象
     * @return map
     */
    public static Map<String, String> obj2Map(final Object obj) {
        return obj2Map(obj, false);
    }

    /**
     * map转换为对象
     *
     * @param src    源map
     * @param toType 对象类别
     * @return 对象
     * @throws FailToCastObjectException
     */
    public static <T> T map2Object(final Map<?, ?> src, final Class<T> toType) throws FailToCastObjectException {
        return Lang.map2Object(src, toType);
    }

    /**
     * 类型转换
     *
     * @param src    原对象
     * @param toType 类型
     * @return 转换后对象
     */
    public static <T> T cast(final Object src, final Class<T> toType) {
        return me().castTo(src, toType);
    }

    /**
     * 转为DTO
     *
     * @param obj   对象
     * @param clazz 类名
     * @return 对象
     */
    public static <T> Object obj2Dto(Object obj, Class<T> clazz) {
        if (null == obj) {
            return null;
        }

        if (obj.getClass().isArray()) {
            List<Object> list = new ArrayList<>();
            int len = Array.getLength(obj);
            for (int i = 0; i < len; i++) {
                list.add(item2Dto(Array.get(obj, i), clazz));
            }
            return list;
        }

        if (obj instanceof Collection) {
            List<Object> list = new ArrayList<>();
            for (Iterator<T> it = ((Collection<T>) obj).iterator(); it.hasNext(); ) {
                list.add(item2Dto(it.next(), clazz));
            }
            return list;
        }

        return item2Dto(obj, clazz);
    }

    /**
     * 单实体转Dto
     *
     * @param obj   对象
     * @param clazz dto类
     * @return 对象
     */
    private static <T> Object item2Dto(Object obj, Class<T> clazz) {

        if (obj instanceof Map || obj instanceof String) {
            return obj;
        }

        if (obj instanceof Number) {
            Map<String, Object> result = MapUtil.map();
            result.put("id", obj);
            return result;
        }

        T dto = null;
        try {
            dto = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw ExceptionUtil.pEx("DTO对象创建失败", e);
        }

        BeanUtil.copyProperties(obj, dto);
        return dto;
    }
}

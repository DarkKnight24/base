/**
 * EnumUtil.java
 * com.uxuexi.core.common.util
 * Copyright (c) 2014, 北京聚智未来科技有限公司版权所有.
*/

package com.movie.base.utils;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.movie.base.enums.IEnum;

public final class EnumUtil {
    
    private static final String VALUE = "value";
    
    private static final String KEY = "key";
    
    /**
     * 将enum转换为map对象 TODO 修改一个方法名enum2map
     * <p>
     * key是对应的key,value是显示的值
     * 
     * @param en enum的类型
     * @return map对象
     */
    public static <T extends IEnum> Map<String, String> enum2(final Class<T> en) {
        Map<String, String> re = new LinkedHashMap<String, String>();
        if (en == null) {
            return re;
        }
        if (!en.isEnum()) {
            return re;
        }
        T[] arr = en.getEnumConstants();
        for (T one : arr) {
            if (one == null) {
                continue;
            }
            re.put(one.key(), one.value());
        }
        return re;
    }
    
    /**
     * 获取枚举
     *
     * @param en 枚举类别
     * @param key 值
     * @return 枚举
     */
    public static <T extends IEnum> T get(final Class<T> en, final String key) {
        if (en == null) {
            return null;
        }
        T[] arr = en.getEnumConstants();
        for (T one : arr) {
            if (Util.eq(key, one.key())) {
                return one;
            }
        }
        return null;
    }
    
    /**
     * 通过枚举的名称获取
     *
     * @param en 枚举类别
     * @param name 枚举名称
     * @return 枚举
     */
    public static <T extends IEnum> T getByEnumName(final Class<T> en, final String name) {
        if (en == null) {
            return null;
        }
        T[] arr = en.getEnumConstants();
        for (T one : arr) {
            if (one.toString().equalsIgnoreCase(name)) {
                return one;
            }
        }
        return null;
    }
    
    /**
     * 通过枚举的名称获取
     *
     * @param en 枚举类别
     * @param value 枚举值
     * @return 枚举
     */
    public static <T extends IEnum> T getByEnumValue(final Class<T> en, final String value) {
        if (en == null) {
            return null;
        }
        T[] arr = en.getEnumConstants();
        for (T one : arr) {
            if (Util.eq(value, one.value())) {
                return one;
            }
        }
        return null;
    }
    
    /**
     * 判断枚举是否存在
     *
     * @param en 枚举类别
     * @param key 值
     * @return 枚举
     */
    public static <T extends IEnum> boolean isContain(final Class<T> en, final String key) {
        return get(en, key) != null;
    }
    
    /**
     * 获取枚举
     *
     * @param en 枚举类别
     * @param key 值
     * @return 枚举
     */
    public static <T extends IEnum> T get(final Class<T> en, final int key) {
        return get(en, String.valueOf(key));
    }
    
    /**
     * 获取枚举
     *
     * @param en 枚举类别
     * @param key 值
     * @return 枚举
     */
    public static <T extends IEnum> String getValue(final Class<T> en, final int key) {
        T t = get(en, String.valueOf(key));
        return Util.isEmpty(t) ? "" : t.value();
    }
    
    /**
     * 获取枚举
     *
     * @param en 枚举类别
     * @param key 值
     * @return 枚举
     */
    public static <T extends IEnum> String getValue(final Class<T> en, final String key) {
        T t = get(en, key);
        return Util.isEmpty(t) ? "" : t.value();
    }
    
    /**
     * 获取字符形键值
     *
     * @param ele 枚举
     * @return 键值
     */
    public static <T extends IEnum> String getKey(final T ele) {
        return Util.isEmpty(ele) ? "-1" : ele.key();
    }
    
    /**
     * 获取整形键值
     *
     * @param ele 枚举
     * @return 键值
     */
    public static <T extends IEnum> int getKeyInt(final T ele) {
        return Util.isEmpty(ele) ? -1 : Integer.valueOf(ele.key());
    }
    
    /**
     * 枚举转List<Map<String, Object>>集合
     * <p>
     * 
     * @param en enum的类型
     * @return
     */
    public static <T extends IEnum> List<Map<String, Object>> enumToList(final Class<T> en) {
        
        return enumToList(en, KEY, VALUE);
    }
    
    /**
     * 枚举转List<Map<String, Object>>集合
     * <p>
     *
     * @param en enum的类型
     * @param filter 过滤条件
     * @return
     */
    public static <T extends IEnum> List<Map<String, Object>> enumToList(final Class<T> en,
        Predicate<? super T> filter) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        if (en == null) {
            return list;
        }
        if (!en.isEnum()) {
            return list;
        }
        
        List<T> sourceList = Arrays.asList(en.getEnumConstants());
        if (!Util.isEmpty(filter)) {
            sourceList = sourceList.stream().filter(filter).collect(Collectors.toList());
        }
        for (T t : sourceList) {
            if (t == null) {
                continue;
            }
            Map<String, Object> re = new LinkedHashMap<String, Object>();
            re.put(KEY, t.key());
            re.put(VALUE, t.value());
            list.add(re);
        }
        return list;
    }
    
    /**
     * 枚举转List<Map<String, Object>>集合
     * <p>
     * 
     * @param en enum的类型
     * @param key Map中自定义key，对应enum的key
     * @param value Map中自定义value，对应enum的value
     * @return 返回示例：[{type=1, typeName=主题讨论}, {type=2, typeName=写作}]
     */
    public static <T extends IEnum> List<Map<String, Object>> enumToList(final Class<T> en, String key, String value) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        if (en == null) {
            return list;
        }
        if (!en.isEnum()) {
            return list;
        }
        if (Util.isEmpty(key)) {
            return list;
        }
        if (Util.isEmpty(key)) {
            return list;
        }
        T[] arr = en.getEnumConstants();
        for (T one : arr) {
            if (one == null) {
                continue;
            }
            Map<String, Object> re = new LinkedHashMap<String, Object>();
            re.put(key, one.key());
            re.put(value, one.value());
            list.add(re);
        }
        return list;
    }
}

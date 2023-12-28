package com.themall.common.utils;

import com.alibaba.fastjson2.JSON;

import java.util.List;

/**
 * @author poo0054
 */
public class JsonUtils {

    public static <P, V> List<V> convert(List<P> list, Class<V> vClass) {
        return JSON.parseArray(JSON.toJSONString(list), vClass);
    }

    public static <P, V> V convert(Object o, Class<V> vClass) {
        return JSON.parseObject(JSON.toJSONString(o), vClass);
    }
}

package com.themall.common.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.objenesis.instantiator.util.ClassUtils;

/**
 * @author poo0054
 */
public class JsonUtils {

    public static <V> V convert(Object o, Class<V> vClass) {
        V v = ClassUtils.newInstance(vClass);
        BeanUtils.copyProperties(o, v);
        return v;
    }
}

package com.themall.common.fastjson;

import com.alibaba.fastjson2.filter.PropertyFilter;
import org.apache.commons.lang3.ObjectUtils;

/**
 * @author poo0054
 */
public class NullPropertyFilter implements PropertyFilter {
    @Override
    public boolean apply(Object object, String name, Object value) {
        return ObjectUtils.isNotEmpty(value);
    }
}

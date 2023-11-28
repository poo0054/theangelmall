package com.themall.common.valid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.HashSet;
import java.util.Set;

/**
 * 两个泛型分别为自定义注解和要校验的类型
 */
public class ListValueConstraintValidator implements ConstraintValidator<ListVlaue, Integer> {

    private Set<Integer> set = new HashSet<Integer>();

    /**
     * @param constraintAnnotation
     */
    @Override
    public void initialize(ListVlaue constraintAnnotation) {
        int[] value = constraintAnnotation.value();
        for (int i : value) {
            set.add(i);
        }
    }

    /**
     * 判断校验是否成功
     *
     * @param value   需要校验的值
     * @param context
     * @return
     */
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return set.contains(value);
    }
}

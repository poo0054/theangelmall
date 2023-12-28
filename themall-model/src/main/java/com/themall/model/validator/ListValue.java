package com.themall.model.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 自定义校验
 *
 * @Constraint 使用哪个校验器;  可以有多个校验
 * @Target ： 作用率
 * @Retention： 什么时候生效
 */
@Documented
@Constraint(validatedBy = {ListValueConstraintValidator.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
public @interface ListValue {
    /**
     * 未填写寻找properties中的默认值
     * 默认会到ValidationMessages.properties中寻找
     *
     * @return
     */
    String message() default "{com.theangel.common.valid.ListVlaue.message}";

    /**
     * 分组
     *
     * @return
     */
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * 自定义
     *
     * @return
     */
    int[] value() default {};
}

package com.oozol.annotation.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 校验是否为正整数
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PositiveIntValidator.class)
public @interface PositiveInt {
    String message() default "必须为正整数";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}

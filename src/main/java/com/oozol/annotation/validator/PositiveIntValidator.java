package com.oozol.annotation.validator;


import com.oozol.utils.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 校验正整数
 *
 */
public class PositiveIntValidator implements ConstraintValidator<PositiveInt, Object> {
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
//        System.out.println("input value: " + value);

        if (StringUtils.isEmpty(value)) {
            return true;
        }
        return value instanceof Integer && (int) value >= 0;
    }
}

package com.sinry.annotation.validator;


import com.sinry.utils.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 校验正整数
 *
 * @author ：Sinry
 * @date ：Created in 2019-04-21 16:37
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

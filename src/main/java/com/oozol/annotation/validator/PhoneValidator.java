package com.oozol.annotation.validator;

import com.oozol.utils.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 电话号码验证
 *
 * @author ：Sinry
 * @date ：Created in 2019-04-22 15:30
 */
public class PhoneValidator implements ConstraintValidator<Phone, Object> {
    private static Pattern PHONE_PATTERN = Pattern.compile("^(\\d{11}|0{2}\\d{11})$");
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        if (StringUtils.isEmpty(value)) {
            return true;
        }
        Matcher m = PHONE_PATTERN.matcher((CharSequence) value);
        return m.matches();
    }
    public static boolean isPhone(String str){
        Matcher m = PHONE_PATTERN.matcher(str);
        return m.matches();
    }
}

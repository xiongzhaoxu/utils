package com.oozol.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @Classname: CompareFiled
 * @Description: TODO
 * @Date: 2019-08-06 16:50
 * @Author: Allen Lei
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface CompareFiled {
    /**
     * 字段名称
     * @return
     */
    String name();
}

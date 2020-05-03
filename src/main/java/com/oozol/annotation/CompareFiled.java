package com.oozol.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * CompareFiled
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface CompareFiled {
    /**
     * 字段名称
     */
    String name();
}

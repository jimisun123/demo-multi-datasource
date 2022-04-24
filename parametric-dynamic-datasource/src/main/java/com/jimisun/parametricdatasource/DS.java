package com.jimisun.parametricdatasource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 动态数据源切换注解
 *
 * @author jimisun
 * @create 2022-03-29 4:26 PM
 **/
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DS {

    /**
     * 数据源名称
     */
    String value() default DataSourceConstants.DS_KEY_MASTER;
}

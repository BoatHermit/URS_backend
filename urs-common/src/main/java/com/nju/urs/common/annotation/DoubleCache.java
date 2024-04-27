package com.nju.urs.common.annotation;

import com.nju.urs.common.enums.CacheType;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DoubleCache {
    String cacheName();
    String key();
    long l1TimeOut() default 60;
    long l2TimeOut() default 3600;
    CacheType type() default CacheType.FULL;
}

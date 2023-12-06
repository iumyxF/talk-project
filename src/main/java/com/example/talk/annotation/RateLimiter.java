package com.example.talk.annotation;

import com.example.talk.model.enums.LimitType;

import java.lang.annotation.*;

/**
 * @author iumyxF
 * @description: 限流注解
 * @date 2023/12/6 14:04
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimiter {

    /**
     * 限流时间,单位秒
     */
    int time() default 60;

    /**
     * 限流次数
     */
    int count() default 100;

    /**
     * 限流类型
     */
    LimitType limitType() default LimitType.DEFAULT;
}

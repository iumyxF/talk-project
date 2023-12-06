package com.example.talk.aop;

import com.example.talk.annotation.RateLimiter;
import com.example.talk.common.ErrorCode;
import com.example.talk.exception.BusinessException;
import com.example.talk.model.enums.LimitType;
import com.example.talk.utils.RedisUtils;
import com.example.talk.utils.ServletUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.redisson.api.RateType;
import org.springframework.stereotype.Component;

/**
 * @author iumyxF
 * @description: 限流处理
 * @date 2023/12/6 14:09
 */
@Slf4j
@Aspect
@Component
public class RateLimiterInterceptor {

    @Before("@annotation(rateLimiter)")
    public void doBefore(JoinPoint point, RateLimiter rateLimiter) {
        int time = rateLimiter.time();
        int count = rateLimiter.count();
        String key = generateKey(rateLimiter);
        long number = RedisUtils.rateLimiter(key, RateType.OVERALL, count, time);
        if (number == -1) {
            log.error("接口路径:{},方法名:{},访问频率过快,进行限制", point.getTarget().getClass().getName(), point.getSignature().getName());
            throw new BusinessException(ErrorCode.REQUEST_FREQUENTLY_ERROR);
        }
        log.info("限制令牌 => {}, 剩余令牌 => {}, 缓存key => '{}'", count, number, key);
    }

    private String generateKey(RateLimiter rateLimiter) {
        StringBuilder key = new StringBuilder();
        key.append(ServletUtils.getRequest().getRequestURI());
        if (LimitType.IP.equals(rateLimiter.limitType())) {
            // 获取请求ip
            key.append(":").append(ServletUtils.getClientIP());
        }
        return key.toString();
    }
}
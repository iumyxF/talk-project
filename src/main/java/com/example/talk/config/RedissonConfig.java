package com.example.talk.config;

import cn.hutool.core.util.ObjectUtil;
import com.example.talk.config.properties.RedissonProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.NameMapper;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.spring.starter.RedissonAutoConfigurationCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author fzy
 * @description: redisson 配置
 * @date 2023/12/11 10:08
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(RedissonProperties.class)
public class RedissonConfig {

    @Resource
    private RedissonProperties redissonProperties;

    @Resource
    private ObjectMapper objectMapper;

    @Bean
    public RedissonAutoConfigurationCustomizer redissonCustomizer() {
        return config -> {
            config.setThreads(redissonProperties.getThreads())
                    .setNettyThreads(redissonProperties.getNettyThreads())
                    .setCodec(new JsonJacksonCodec(objectMapper));
            RedissonProperties.SingleServerConfig singleServerConfig = redissonProperties.getSingleServerConfig();
            if (ObjectUtil.isNotNull(singleServerConfig)) {
                // 使用单机模式
                config.useSingleServer()
                        //设置redis key前缀
                        .setNameMapper(new NameMapper() {
                            String keyPrefix = redissonProperties.getKeyPrefix();

                            @Override
                            public String map(String name) {
                                if (StringUtils.isBlank(name)) {
                                    return null;
                                }
                                if (StringUtils.isNotBlank(keyPrefix) && !name.startsWith(keyPrefix)) {
                                    return keyPrefix + name;
                                }
                                return name;
                            }
                            @Override
                            public String unmap(String name) {
                                if (StringUtils.isBlank(name)) {
                                    return null;
                                }
                                if (StringUtils.isNotBlank(keyPrefix) && name.startsWith(keyPrefix)) {
                                    return name.substring(keyPrefix.length());
                                }
                                return name;
                            }
                        })
                        .setTimeout(singleServerConfig.getTimeout())
                        .setClientName(singleServerConfig.getClientName())
                        .setIdleConnectionTimeout(singleServerConfig.getIdleConnectionTimeout())
                        .setSubscriptionConnectionPoolSize(singleServerConfig.getSubscriptionConnectionPoolSize())
                        .setConnectionMinimumIdleSize(singleServerConfig.getConnectionMinimumIdleSize())
                        .setConnectionPoolSize(singleServerConfig.getConnectionPoolSize());
            }
            log.info("初始化 redis 配置");
        };
    }
}

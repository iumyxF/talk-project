package com.example.talk.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author iumyxF
 * @description: 阿里 通义千问 配置
 * <a href="https://help.aliyun.com/zh/dashscope/developer-reference/api-details#341800c0f8w0r"/>
 * @date 2023/12/2 14:23
 */
@Configuration
@ConfigurationProperties(prefix = "llm.tyqw")
@Data
public class TyqwConfig {

    /**
     * apiKey
     */
    private String apiKey;
}

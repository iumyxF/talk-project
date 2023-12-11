package com.example.talk.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author iumyxF
 * @description: 千帆模型配置信息
 * @date 2023/12/7 10:26
 */
@Component
@ConfigurationProperties(prefix = "llm.qian-fan")
@Data
public class QianFanProperties {

    private String appId;

    private String apiKey;

    private String secretKey;
}

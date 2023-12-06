package com.example.talk.answerer;

import com.example.talk.common.ErrorCode;
import com.example.talk.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author iumyxF
 * @description:
 * @date 2023/12/4 14:27
 */
@Slf4j
@Component
public class AnswererFactory implements InitializingBean {

    @Resource
    private ApplicationContext applicationContext;

    private final Map<String, Answerer> answererMap = new HashMap<>();

    /**
     * 加载answerer
     */
    @Override
    public void afterPropertiesSet() {
        applicationContext.getBeansOfType(Answerer.class)
                .values()
                .forEach(answerer -> answererMap.put(answerer.getName(), answerer));
        log.info("加载的LLM列表:{}", answererMap);
    }

    /**
     * 获取对应的answerer
     *
     * @param name 模型名称
     * @return 结果
     */
    public Answerer getAnswerer(String name) {
        Answerer answerer = answererMap.get(name);
        if (null == answerer) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        return answerer;
    }
}

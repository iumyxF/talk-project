package com.example.talk.answerer;

import com.example.talk.model.enums.AnswererEnums;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author iumyxF
 * @description:
 * @date 2023/12/4 14:27
 */
@Slf4j
public class AnswererFactory implements InitializingBean {

    @Resource
    private ApplicationContext applicationContext;

    private final Map<String, Answerer> answererMap = new HashMap<>();

    /**
     * 加载answerer
     */
    @Override
    public void afterPropertiesSet() {
        applicationContext.getBeansOfType(Answerer.class).values()
                .forEach(answerer -> answererMap.put(answerer.getName(), answerer));
        log.info("加载的LLM列表:{}", answererMap.keySet().toArray());
    }

    /**
     * 获取对应的answerer
     *
     * @param answererEnums 枚举
     * @return 结果
     */
    public Answerer getAnswerer(AnswererEnums answererEnums) {
        return answererMap.get(answererEnums.getName());
    }
}

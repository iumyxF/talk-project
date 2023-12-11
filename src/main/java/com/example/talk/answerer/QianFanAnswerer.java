package com.example.talk.answerer;

import com.example.talk.api.qianfan.QianFanApi;
import com.example.talk.api.qianfan.model.QianFanQuestionResponse;
import com.example.talk.config.properties.QianFanProperties;
import com.example.talk.model.domain.User;
import com.example.talk.model.enums.AnswererEnums;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author iumyxF
 * @description: 千帆
 * @date 2023/12/7 10:23
 */
@Slf4j
@Component
public class QianFanAnswerer implements Answerer {

    @Resource
    private QianFanProperties properties;

    @Resource
    private QianFanApi api;

    @Override
    public String doAnswer(User user, String question) {
        QianFanQuestionResponse message = api.callWithMessage(user, question, properties.getApiKey(), properties.getSecretKey());
        return message.getResult();
    }

    @Override
    public boolean resetTalk(User user) {
        return false;
    }

    @Override
    public String getName() {
        return AnswererEnums.QIAN_FAN.getName();
    }
}

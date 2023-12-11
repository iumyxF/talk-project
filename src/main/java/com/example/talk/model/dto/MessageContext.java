package com.example.talk.model.dto;

import com.example.talk.api.qianfan.model.QianFanQuestionResponse;
import com.example.talk.model.enums.Role;
import com.google.common.collect.EvictingQueue;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author iumyxF
 * @description: 对话内容上下文
 * @date 2023/12/8 9:55
 */
public class MessageContext {

    private static final int DEFAULT_MAXIMUM_MESSAGES = 10;

    /**
     * 消息队列
     */
    private final EvictingQueue<Message> messages;

    public MessageContext() {
        messages = EvictingQueue.create(DEFAULT_MAXIMUM_MESSAGES);
    }

    public MessageContext(int maxMessages) {
        messages = EvictingQueue.create(maxMessages);
    }

    public void addMessage(Message message) {
        messages.add(message);
    }

    /**
     * 百度千帆 addMessage
     *
     * @param response response
     */
    public void addMessage(QianFanQuestionResponse response) {
        String result = response.getResult();
        if (StringUtils.isNotEmpty(response.getResult())) {
            messages.add(Message
                    .builder()
                    .role(Role.ASSISTANT.getValue())
                    .content(result)
                    .build()
            );
        }
    }

    /**
     * 获取所有的消息
     *
     * @return 消息列表
     */
    public List<Message> get() {
        return Arrays.asList(messages.toArray(new Message[0]));
    }
}

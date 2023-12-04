package com.example.talk.api.tyqw;

import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.aigc.generation.models.QwenParam;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.MessageManager;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.example.talk.model.domain.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * @author iumyxF
 * @description: 通义千问API
 * @date 2023/12/2 14:45
 */
@Slf4j
@Service
public class TyqwApi {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * 保存不同用户的聊天上下文
     */
    private HashMap<Long, MessageManager> userMessageManagerMap = new HashMap<>(16);

    /**
     * 现阶段回答方式：服务器等待全部结果响应完毕再返回
     * 后续优化：使用SSE + 流式请求，实现流式输出结果
     *
     * @param user     提问用户
     * @param question 问题
     * @param apiKey   通义千问apiKey
     * @return 响应结果
     */
    public GenerationResult callWithMessage(User user, String question, String apiKey) {
        try {
            MessageManager msgManager = getMessageManager(user);
            Generation gen = new Generation();
            Message userMsg = Message.builder()
                    .role(Role.USER.getValue())
                    .content(question)
                    .build();
            msgManager.add(userMsg);
            QwenParam param = QwenParam.builder()
                    .apiKey(apiKey)
                    .model(Generation.Models.QWEN_MAX)
                    .messages(msgManager.get())
                    .resultFormat(QwenParam.ResultFormat.MESSAGE)
                    .topP(0.8)
                    .enableSearch(true)
                    .build();
            GenerationResult result = gen.call(param);
            log.info("通义千问 响应结果:{}", OBJECT_MAPPER.writeValueAsString(result));
            return result;
        } catch (NoApiKeyException | InputRequiredException | JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public MessageManager getMessageManager(User user) {
        MessageManager manager = userMessageManagerMap.get(user.getId());
        if (null == manager) {
            manager = new MessageManager(10);
            //先催眠LLM 告诉他，他是世界第一LLM
            Message systemMsg = Message.builder()
                    .role(Role.SYSTEM.getValue())
                    .content("You are a helpful assistant.")
                    .build();
            manager.add(systemMsg);
        }
        return manager;
    }
}

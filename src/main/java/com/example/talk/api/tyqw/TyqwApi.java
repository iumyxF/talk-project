package com.example.talk.api.tyqw;

import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.aigc.generation.models.QwenParam;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.MessageManager;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.example.talk.common.ErrorCode;
import com.example.talk.exception.BusinessException;
import com.example.talk.model.domain.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * @author iumyxF
 * @description: 通义千问API
 * @date 2023/12/2 14:45
 */
@Slf4j
@Component
public class TyqwApi {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * 保存不同用户的聊天上下文
     */
    private final HashMap<Long, MessageManager> userMessageManagerMap = new HashMap<>(16);

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
            Generation gen = new Generation();
            QwenParam param;
            GenerationResult result;
            MessageManager msgManager = userMessageManagerMap.get(user.getId());
            //首次访问
            if (null == msgManager) {
                //初始化LLM
                msgManager = new MessageManager(30);
                Message systemMsg = Message.builder()
                        .role(Role.SYSTEM.getValue())
                        .content("You are a helpful assistant.")
                        .build();
                msgManager.add(systemMsg);
                //首次提问
                Message userMsg = Message
                        .builder()
                        .role(Role.USER.getValue())
                        .content(question)
                        .build();
                msgManager.add(userMsg);
                param = QwenParam.builder()
                        .model(Generation.Models.QWEN_MAX)
                        .apiKey(apiKey)
                        .messages(msgManager.get())
                        .resultFormat(QwenParam.ResultFormat.MESSAGE)
                        .topP(0.8)
                        .enableSearch(true)
                        .build();
                result = gen.call(param);
                log.info("通义千问 响应结果:{}", OBJECT_MAPPER.writeValueAsString(result));
                msgManager.add(result);
                userMessageManagerMap.put(user.getId(), msgManager);
            } else {
                //非首次提问
                param = QwenParam.builder()
                        .model(Generation.Models.QWEN_MAX)
                        .apiKey(apiKey)
                        .messages(msgManager.get())
                        .resultFormat(QwenParam.ResultFormat.MESSAGE)
                        .topP(0.8)
                        .enableSearch(true)
                        .build();
                param.setPrompt(question);
                result = gen.call(param);
                msgManager.add(result);
                log.info("通义千问 响应结果:{}", OBJECT_MAPPER.writeValueAsString(result));
            }
            return result;
        } catch (NoApiKeyException | InputRequiredException | JsonProcessingException e) {
            log.error("通义千问 访问异常,{}", e.getMessage());
            throw new BusinessException(ErrorCode.REMOTE_CALL_ERROR);
        }
    }

    /**
     * 删除用户的聊天记录
     *
     * @param userId 用户id
     * @return 结果
     */
    public boolean clearMessage(Long userId) {
        return userMessageManagerMap.remove(userId) != null;
    }
}

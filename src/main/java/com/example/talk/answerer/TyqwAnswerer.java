package com.example.talk.answerer;


import com.alibaba.dashscope.aigc.generation.GenerationOutput;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.example.talk.api.tyqw.TyqwApi;
import com.example.talk.common.ErrorCode;
import com.example.talk.config.TyqwConfig;
import com.example.talk.exception.BusinessException;
import com.example.talk.model.domain.User;
import com.example.talk.model.enums.AnswererEnums;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author iumyxF
 * @description: 通义千问
 * @date 2023/12/2 14:40
 */
@Slf4j
@Component
public class TyqwAnswerer implements Answerer {

    @Resource
    private TyqwConfig config;

    @Resource
    private TyqwApi api;

    @Override
    public String doAnswer(User questioner, String question) {
        CompletableFuture<GenerationResult> future = CompletableFuture
                .supplyAsync(() -> api.callWithMessage(questioner, question, config.getApiKey()));
        StringBuilder sb = new StringBuilder();
        try {
            GenerationResult generationResult = future.get(60, TimeUnit.SECONDS);
            List<GenerationOutput.Choice> choices = generationResult.getOutput().getChoices();
            choices.forEach(c -> sb.append(c.getMessage().getContent()));
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            log.error("通义千问 回答超时...");
            throw new BusinessException(ErrorCode.REMOTE_CALL_ERROR);
        }
        return sb.toString();
    }

    @Override
    public String getName() {
        return AnswererEnums.TYQW.getName();
    }
}

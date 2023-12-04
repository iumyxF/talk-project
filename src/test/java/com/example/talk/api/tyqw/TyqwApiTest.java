package com.example.talk.api.tyqw;

import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.aigc.generation.models.QwenParam;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.MessageManager;
import com.alibaba.dashscope.common.ResultCallback;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.dashscope.utils.JsonUtils;
import io.reactivex.Flowable;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Collections;
import java.util.concurrent.Semaphore;

/**
 * @author iumyxF
 * @description: 通义千问API测试
 * @date 2023/12/2 14:54
 */
public class TyqwApiTest {

    private static final String apiKey = "your api key";

    /**
     * 普通单论问答
     */
    @Ignore
    @Test
    public void testCallWithMessage() {
        try {
            callWithMessage();
        } catch (ApiException | NoApiKeyException | InputRequiredException e) {
            System.out.println(e.getMessage());
        }
        System.exit(0);
    }

    private void callWithMessage() throws NoApiKeyException, InputRequiredException {
        Generation gen = new Generation();
        MessageManager msgManager = new MessageManager(10);
        Message systemMsg = Message.builder().role(Role.SYSTEM.getValue()).content("你是智能助手机器人").build();
        Message userMsg = Message.builder().role(Role.USER.getValue()).content("你好，周末去哪里玩？").build();
        msgManager.add(systemMsg);
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
        System.out.println(JsonUtils.toJson(result));
        //第二轮问答
        //msgManager.add(result);
        //param.setPrompt("找个近点的");
        //param.setMessages(msgManager.get());
        //result = gen.call(param);
        //System.out.println(result);
        //System.out.println(JsonUtils.toJson(result));
    }

    /**
     * 普通多轮问答
     */
    @Ignore
    @Test
    public void testMultiwheelCallWithMessage() {
        try {
            multiwheelCallWithMessage();
        } catch (ApiException | NoApiKeyException | InputRequiredException e) {
            System.out.println(e.getMessage());
        }
        System.exit(0);
    }

    private void multiwheelCallWithMessage() throws NoApiKeyException, InputRequiredException {
        Generation gen = new Generation();
        MessageManager msgManager = new MessageManager(10);
        Message systemMsg =
                Message.builder().role(Role.SYSTEM.getValue()).content("你是智能助手机器人").build();
        Message userMsg = Message.builder().role(Role.USER.getValue()).content("今天星期几？").build();
        msgManager.add(systemMsg);
        msgManager.add(userMsg);
        QwenParam param =
                QwenParam.builder()
                        .model(Generation.Models.QWEN_MAX)
                        .apiKey(apiKey)
                        .messages(msgManager.get())
                        .resultFormat(QwenParam.ResultFormat.MESSAGE)
                        .topP(0.8)
                        .enableSearch(true)
                        .build();
        GenerationResult result = gen.call(param);
        System.out.println(result);
        //保存第一轮结果
        msgManager.add(result);
        System.out.println(JsonUtils.toJson(result));
        // 第二轮
        param.setPrompt("不放糖可以吗？");
        param.setMessages(msgManager.get());
        result = gen.call(param);
        System.out.println(result);
        System.out.println(JsonUtils.toJson(result));
    }

    /**
     * 多轮问答
     * 流式输出
     */
    @Ignore
    @Test
    public void testMultiwheelStreamCallWithMessage() {
        try {
            streamCallWithMessage();
        } catch (ApiException | NoApiKeyException | InputRequiredException e) {
            System.out.println(e.getMessage());
        }
        try {
            streamCallWithCallback();
        } catch (ApiException | NoApiKeyException | InputRequiredException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
        System.exit(0);
    }

    public static void streamCallWithMessage() throws NoApiKeyException, ApiException, InputRequiredException {
        Generation gen = new Generation();
        Message userMsg = Message
                .builder()
                .role(Role.USER.getValue())
                .content("如何做西红柿炖牛腩？")
                .build();
        QwenParam param =
                QwenParam.builder()
                        .model(Generation.Models.QWEN_MAX)
                        .apiKey(apiKey)
                        .messages(Collections.singletonList(userMsg))
                        .resultFormat(QwenParam.ResultFormat.MESSAGE)
                        .topP(0.8)
                        .enableSearch(true)
                        .incrementalOutput(true) // get streaming output incrementally
                        .build();
        Flowable<GenerationResult> result = gen.streamCall(param);
        StringBuilder fullContent = new StringBuilder();
        result.blockingForEach(message -> {
            fullContent.append(message.getOutput().getChoices().get(0).getMessage().getContent());
            System.out.println(JsonUtils.toJson(message));
        });
        System.out.println("Full content: \n" + fullContent);
    }

    public static void streamCallWithCallback() throws NoApiKeyException, ApiException, InputRequiredException, InterruptedException {
        Generation gen = new Generation();
        Message userMsg = Message
                .builder()
                .role(Role.USER.getValue())
                .content("如何做西红柿炖牛腩？")
                .build();
        QwenParam param = QwenParam
                .builder()
                .apiKey(apiKey)
                .model(Generation.Models.QWEN_MAX)
                .resultFormat(QwenParam.ResultFormat.MESSAGE)
                .messages(Collections.singletonList(userMsg))
                .topP(0.8)
                .incrementalOutput(true) // get streaming output incrementally
                .build();
        Semaphore semaphore = new Semaphore(0);
        StringBuilder fullContent = new StringBuilder();
        gen.streamCall(param, new ResultCallback<GenerationResult>() {

            @Override
            public void onEvent(GenerationResult message) {
                fullContent.append(message.getOutput().getChoices().get(0).getMessage().getContent());
                System.out.println(message);
            }

            @Override
            public void onError(Exception err) {
                System.out.printf("Exception: %s%n", err.getMessage());
                semaphore.release();
            }

            @Override
            public void onComplete() {
                System.out.println("Completed");
                semaphore.release();
            }

        });
        semaphore.acquire();
        System.out.println("Full content: \n" + fullContent);
    }
}
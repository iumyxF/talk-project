package com.example.talk.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.concurrent.*;

/**
 * @author iumyxF
 * @description: 测试SSE 推送
 * @date 2023/12/4 9:57
 */
@RestController
@RequestMapping
public class SseControllerTest {

    private static final String[] WORDS = "The right word cuts more deeply than a knife".split(" ");

    private final ThreadPoolExecutor executor = new ThreadPoolExecutor(1,
            2,
            10,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(10),
            new ThreadPoolExecutor.AbortPolicy());

    @GetMapping(path = "/test/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    SseEmitter getWords() {
        SseEmitter emitter = new SseEmitter();
        executor.execute(() -> {
            try {
                for (String word : WORDS) {
                    emitter.send(word);
                    TimeUnit.SECONDS.sleep(1);
                }
                emitter.complete();
            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        });
        return emitter;
    }
}

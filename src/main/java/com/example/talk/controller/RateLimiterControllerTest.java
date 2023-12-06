package com.example.talk.controller;

import com.example.talk.annotation.RateLimiter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author iumyxF
 * @description:
 * @date 2023/12/6 14:55
 */
@RestController
@RequestMapping
public class RateLimiterControllerTest {

    @RateLimiter(count = 2, time = 10)
    @GetMapping("/rate/test1")
    public String test1() {
        return "ok";
    }

    @RateLimiter(count = 5, time = 10)
    @GetMapping("/rate/test2")
    public String test2() {
        return "ok";
    }
}

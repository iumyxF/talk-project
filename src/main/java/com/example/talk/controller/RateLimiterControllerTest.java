package com.example.talk.controller;

import com.example.talk.annotation.RateLimiter;
import com.example.talk.common.BaseResponse;
import com.example.talk.common.ResultUtils;
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
    public BaseResponse<String> test1() {
        return ResultUtils.success("ok");
    }

    @RateLimiter(count = 5, time = 10)
    @GetMapping("/rate/test2")
    public BaseResponse<String> test2() {
        return ResultUtils.success("ok");
    }
}

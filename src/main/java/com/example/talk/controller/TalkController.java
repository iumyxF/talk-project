package com.example.talk.controller;

import com.example.talk.annotation.AuthCheck;
import com.example.talk.common.BaseResponse;
import com.example.talk.common.ResultUtils;
import com.example.talk.constant.UserConstant;
import com.example.talk.model.domain.User;
import com.example.talk.model.dto.talk.TalkQuestionRequest;
import com.example.talk.model.dto.talk.TalkRestRequest;
import com.example.talk.model.enums.AnswererEnums;
import com.example.talk.service.TalkService;
import com.example.talk.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author iumyxF
 * @description:
 * @date 2023/12/4 11:11
 */
@Tag(name = "talk model")
@RestController
@RequestMapping("/talk")
public class TalkController {

    @Resource
    private TalkService talkService;
    @Resource
    private UserService userService;

    @Operation(summary = "LLM 模型列表")
    @GetMapping("/models")
    public BaseResponse<List<String>> models() {
        return ResultUtils.success(AnswererEnums.getAllNames());
    }

    @Operation(summary = "问答")
    @AuthCheck(anyRole = {UserConstant.DEFAULT_ROLE, UserConstant.ADMIN_ROLE})
    @PostMapping("/answerer")
    public BaseResponse<String> answerer(@RequestBody TalkQuestionRequest talkQuestionRequest,
                                         HttpServletRequest httpServletRequest) {
        User loginUser = userService.getLoginUser(httpServletRequest);
        String reply = talkService.talk(talkQuestionRequest, loginUser);
        return ResultUtils.success(reply);
    }

    @Operation(summary = "重置对话")
    @AuthCheck(anyRole = {UserConstant.DEFAULT_ROLE, UserConstant.ADMIN_ROLE})
    @PostMapping("/reset")
    public BaseResponse<Boolean> reset(@RequestBody TalkRestRequest talkRestRequest,
                                       HttpServletRequest httpServletRequest) {
        User loginUser = userService.getLoginUser(httpServletRequest);
        return ResultUtils.success(talkService.reset(talkRestRequest, loginUser));
    }
}

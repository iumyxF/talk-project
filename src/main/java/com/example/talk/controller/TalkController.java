package com.example.talk.controller;

import com.example.talk.annotation.AuthCheck;
import com.example.talk.common.BaseResponse;
import com.example.talk.common.ResultUtils;
import com.example.talk.constant.UserConstant;
import com.example.talk.model.dto.talk.TalkRequest;
import com.example.talk.model.enums.AnswererEnums;
import com.example.talk.service.TalkService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author iumyxF
 * @description:
 * @date 2023/12/4 11:11
 */
@RestController
public class TalkController {

    @Resource
    private TalkService talkService;

    @ApiOperation(value = "LLM 模型列表")
    @GetMapping("/talk/answerer/list")
    public BaseResponse<List<String>> getAllAnswerer() {
        return ResultUtils.success(AnswererEnums.getAllNames());
    }

    @ApiOperation(value = "问答")
    @AuthCheck(anyRole = {UserConstant.DEFAULT_ROLE, UserConstant.ADMIN_ROLE})
    @PostMapping("/talk")
    public BaseResponse<String> talk(@RequestBody TalkRequest talkRequest,
                                     HttpServletRequest httpServletRequest) {
        String reply = talkService.talk(talkRequest, httpServletRequest);
        return ResultUtils.success(reply);
    }
}

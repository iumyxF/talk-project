package com.example.talk.service.impl;

import com.example.talk.answerer.Answerer;
import com.example.talk.answerer.AnswererFactory;
import com.example.talk.model.domain.User;
import com.example.talk.model.dto.talk.TalkRequest;
import com.example.talk.service.TalkService;
import com.example.talk.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author fzy
 * @description:
 * @date 2023/12/5 14:29
 */
@Slf4j
@Service
public class TalkServiceImpl implements TalkService {

    @Resource
    private AnswererFactory answererFactory;

    @Resource
    private UserService userService;

    @Override
    public String talk(TalkRequest talkRequest, HttpServletRequest httpServletRequest) {
        talkRequest.check();
        User loginUser = userService.getLoginUser(httpServletRequest);
        Answerer answerer = answererFactory.getAnswerer(talkRequest.getModel());
        return answerer.doAnswer(loginUser, talkRequest.getQuestion());
    }
}

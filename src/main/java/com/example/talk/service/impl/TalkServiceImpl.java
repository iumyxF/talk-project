package com.example.talk.service.impl;

import com.example.talk.answerer.Answerer;
import com.example.talk.answerer.AnswererFactory;
import com.example.talk.model.domain.User;
import com.example.talk.model.dto.talk.TalkQuestionRequest;
import com.example.talk.model.dto.talk.TalkRestRequest;
import com.example.talk.service.TalkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author iumyxF
 * @description:
 * @date 2023/12/5 14:29
 */
@Slf4j
@Service
public class TalkServiceImpl implements TalkService {

    @Resource
    private AnswererFactory answererFactory;

    @Override
    public String talk(TalkQuestionRequest talkQuestionRequest, User user) {
        talkQuestionRequest.check();
        Answerer answerer = answererFactory.getAnswerer(talkQuestionRequest.getModel());
        return answerer.doAnswer(user, talkQuestionRequest.getQuestion());
    }

    @Override
    public boolean reset(TalkRestRequest talkRestRequest, User user) {
        talkRestRequest.check();
        Answerer answerer = answererFactory.getAnswerer(talkRestRequest.getModel());
        return answerer.resetTalk(user);
    }
}

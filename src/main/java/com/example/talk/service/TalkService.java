package com.example.talk.service;

import com.example.talk.model.domain.User;
import com.example.talk.model.dto.talk.TalkQuestionRequest;
import com.example.talk.model.dto.talk.TalkRestRequest;

/**
 * @author iumyxF
 * @description: talk
 * @date 2023/12/5 14:28
 */
public interface TalkService {

    /**
     * 问答
     *
     * @param talkQuestionRequest 提问请求对象
     * @param user                提问用户
     * @return 响应结果
     */
    String talk(TalkQuestionRequest talkQuestionRequest, User user);

    /**
     * 重置对话内容
     *
     * @param talkRestRequest 重置请求
     * @param user            提问用户
     * @return 结果
     */
    boolean reset(TalkRestRequest talkRestRequest, User user);
}

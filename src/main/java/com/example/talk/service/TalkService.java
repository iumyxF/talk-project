package com.example.talk.service;

import com.example.talk.model.dto.talk.TalkRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author iumyxF
 * @description: talk
 * @date 2023/12/5 14:28
 */
public interface TalkService {

    /**
     * 问答
     *
     * @param talkRequest        提问请求对象
     * @param httpServletRequest http请求
     * @return 响应结果
     */
    String talk(TalkRequest talkRequest, HttpServletRequest httpServletRequest);
}

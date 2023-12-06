package com.example.talk.answerer;

import com.example.talk.model.domain.User;

/**
 * @author iumyxF
 * @description: 回答者
 * @date 2023/12/2 14:39
 */
public interface Answerer {

    /**
     * 回答
     *
     * @param user     用户
     * @param question 问题
     * @return 结果
     */
    String doAnswer(User user, String question);

    /**
     * 获取名字
     *
     * @return 结果
     */
    String getName();

    /**
     * 重置某个用户的聊天内容
     *
     * @param user 用户
     * @return 结果
     */
    boolean resetTalk(User user);
}

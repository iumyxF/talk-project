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
     * @param questioner 提问者
     * @param question   问题
     * @return 结果
     */
    String doAnswer(User questioner, String question);

    /**
     * 获取名字
     *
     * @return 结果
     */
    String getName();
}

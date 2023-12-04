package com.example.talk.answerer;


import com.example.talk.api.tyqw.TyqwApi;
import com.example.talk.config.TyqwConfig;
import com.example.talk.model.domain.User;
import com.example.talk.model.enums.AnswererEnums;
import com.example.talk.utils.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @author iumyxF
 * @description: 通义千问
 * @date 2023/12/2 14:40
 */
@Slf4j
public class TyqwAnswerer implements Answerer {

    private final TyqwConfig config = SpringContextUtils.getBean(TyqwConfig.class);

    private final TyqwApi api = SpringContextUtils.getBean(TyqwApi.class);

    @Override
    public String doAnswer(User questioner, String question) {
        api.callWithMessage(questioner, question, config.getApiKey());
        return null;
    }

    @Override
    public String getName() {
        return AnswererEnums.TYQW.getName();
    }
}

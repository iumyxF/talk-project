package com.example.talk.api.qianfan;

import cn.hutool.http.ContentType;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import com.example.talk.api.qianfan.model.QianFanQuestionRequest;
import com.example.talk.api.qianfan.model.QianFanQuestionResponse;
import com.example.talk.common.ErrorCode;
import com.example.talk.constant.ThirdPartyConstant;
import com.example.talk.exception.BusinessException;
import com.example.talk.model.domain.User;
import com.example.talk.model.dto.Message;
import com.example.talk.model.dto.MessageContext;
import com.example.talk.model.dto.Token;
import com.example.talk.model.enums.Role;
import com.example.talk.utils.RedisUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.HashMap;

/**
 * @author iumyxF
 * @description:
 * @date 2023/12/7 10:47
 */
@Slf4j
@Component
public class QianFanApi {

    private final static String ACCESS_TOKEN_URI = "https://aip.baidubce.com/oauth/2.0/token";

    private final static String API_URI = "https://aip.baidubce.com/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/qianfan_chinese_llama_2_13b";

    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private final static HashMap<Long, MessageContext> msgContextMap = new HashMap<>();

    /**
     * 提问
     *
     * @param user      提问者
     * @param question  问题
     * @param apiKey    apiKey
     * @param secretKey secretKey
     * @return 结果
     */
    public QianFanQuestionResponse callWithMessage(User user, String question, String apiKey, String secretKey) {
        Token token = getAccessToken(apiKey, secretKey);
        MessageContext context = msgContextMap.get(user.getId());
        if (null == context) {
            context = new MessageContext();
        }
        //构建消息和请求
        Message questionMessage = Message.builder().role(Role.USER.getValue()).content(question).build();
        QianFanQuestionRequest request = new QianFanQuestionRequest();
        context.addMessage(questionMessage);
        request.setMessages(context.get());
        try {
            String respJson = HttpRequest.post(API_URI + "?access_token=" + token.getAccessToken())
                    .header(Header.CONTENT_TYPE, ContentType.JSON.getValue())
                    .header(Header.ACCEPT, ContentType.JSON.getValue())
                    .body(OBJECT_MAPPER.writeValueAsString(request))
                    .execute()
                    .body();
            QianFanQuestionResponse response = OBJECT_MAPPER.readValue(respJson, QianFanQuestionResponse.class);
            handleError(response);
            context.addMessage(response);
            msgContextMap.put(user.getId(), context);
            return response;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取accessToken
     *
     * @param apiKey    key
     * @param secretKey secretKey
     * @return token
     */
    private Token getAccessToken(String apiKey, String secretKey) {
        Token token = RedisUtils.getCacheObject(ThirdPartyConstant.QIAN_FAN_ACCESS_TOKEN);
        if (null != token) {
            return token;
        }
        HashMap<String, Object> paramMap = new HashMap<>(3);
        paramMap.put("grant_type", "client_credentials");
        paramMap.put("client_id", apiKey);
        paramMap.put("client_secret", secretKey);
        try {
            String response = HttpRequest.post(ACCESS_TOKEN_URI)
                    .header(Header.CONTENT_TYPE, ContentType.JSON.getValue())
                    .header(Header.ACCEPT, ContentType.JSON.getValue())
                    .form(paramMap)
                    .execute()
                    .body();
            token = OBJECT_MAPPER.readValue(response, Token.class);
            if (StringUtils.isNotBlank(token.getErrorDescription()) || StringUtils.isNotBlank(token.getError())) {
                log.error("QianFan 获取accessToken异常,Description:{},error:{}", token.getErrorDescription(), token.getError());
                throw new BusinessException(ErrorCode.REMOTE_CALL_ERROR);
            }
            RedisUtils.setCacheObject(ThirdPartyConstant.QIAN_FAN_ACCESS_TOKEN, token, Duration.ofSeconds(token.getExpires()));
            return token;
        } catch (JsonProcessingException e) {
            log.error("QianFan 获取accessToken异常:{}", e.getMessage());
            throw new BusinessException(ErrorCode.REMOTE_CALL_ERROR);
        }
    }

    /**
     * 响应失败的处理
     *
     * @param response 响应结果
     */
    private void handleError(QianFanQuestionResponse response) {
        if (null == response) {
            throw new BusinessException(ErrorCode.REMOTE_CALL_ERROR);
        }
        if (null != response.getErrorCode()) {
            log.error("远程调用失败，错误码：{}，错误信息：{}", response.getErrorCode(), response.getErrorMsg());
            throw new BusinessException(ErrorCode.REMOTE_CALL_ERROR, response.getErrorMsg());
        }
    }
}
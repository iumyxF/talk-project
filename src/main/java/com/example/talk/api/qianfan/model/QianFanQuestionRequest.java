package com.example.talk.api.qianfan.model;

import com.example.talk.model.dto.Message;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author iumyxF
 * @description: 问答请求
 * <a href="https://cloud.baidu.com/doc/WENXINWORKSHOP/s/8lo479b4b#%E8%AF%B7%E6%B1%82%E8%AF%B4%E6%98%8E"/>
 * @date 2023/12/7 10:50
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QianFanQuestionRequest implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    /**
     * 对话记录 必填
     */
    @JsonProperty("messages")
    private List<Message> messages;

    @JsonProperty("stream")
    private Boolean bool;

    @JsonProperty("temperature")
    private Float temperature;

    @JsonProperty("top_k")
    private Integer topK;

    @JsonProperty("top_p")
    private Float topP;

    @JsonProperty("penalty_score")
    private Float penaltyScore;

    @JsonProperty("stop")
    private List<String> stop;

    @JsonProperty("user_id")
    private String userId;

    public QianFanQuestionRequest(List<Message> messages) {
        this.messages = messages;
    }
}
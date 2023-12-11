package com.example.talk.api.qianfan.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author iumyxF
 * @description: 千帆回答响应
 * <a href="https://cloud.baidu.com/doc/WENXINWORKSHOP/s/8lo479b4b#%E5%93%8D%E5%BA%94%E8%AF%B4%E6%98%8E"/>
 * @date 2023/12/7 10:53
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QianFanQuestionResponse implements Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 1L;

    @JsonProperty("id")
    private String id;

    @JsonProperty("object")
    private String object;

    @JsonProperty("created")
    private Long created;

    @JsonProperty("sentence_id")
    private Integer sentenceId;

    @JsonProperty("is_end")
    private Boolean isEnd;

    @JsonProperty("is_truncated")
    private Boolean isTruncated;

    @JsonProperty("result")
    private String result;

    @JsonProperty("need_clear_history")
    private Boolean needClearHistory;

    @JsonProperty("ban_round")
    private Integer banRound;

    @JsonProperty("usage")
    private Usage usage;

    @JsonProperty("error_code")
    private Integer errorCode;

    @JsonProperty("error_msg")
    private String errorMsg;
}
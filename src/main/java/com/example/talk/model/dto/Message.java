package com.example.talk.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author iumyxF
 * @description: 通用message
 * @date 2023/12/8 9:58
 */
@Data
@SuperBuilder
@NoArgsConstructor
public class Message {

    /**
     * 角色
     * 百度千帆：user,assistant
     */
    @JsonProperty("role")
    String role;

    /**
     * 内容文本
     */
    @JsonProperty("content")
    String content;

}

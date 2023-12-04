package com.example.talk.model.dto.talk;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author iumyxF
 * @description:
 * @date 2023/12/4 14:37
 */
public class TalkRequest implements Serializable {

    private final static long serialVersionUID = 1L;

    @ApiModelProperty("提问者id")
    private Long userId;

    @ApiModelProperty("问题文本")
    private String question;

    /**
     * {@link com.example.talk.model.enums.AnswererEnums}
     */
    @ApiModelProperty("模型")
    private String model;
}
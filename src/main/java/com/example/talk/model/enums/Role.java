package com.example.talk.model.enums;

/**
 * @author iumyxF
 * @description: 角色枚举
 * @date 2023/12/8 10:06
 */
public enum Role {

    /**
     * 用户
     */
    USER("user"),

    /**
     * GPT助理
     */
    ASSISTANT("assistant");

    private final String value;

    private Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

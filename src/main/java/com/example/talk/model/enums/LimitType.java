package com.example.talk.model.enums;

/**
 * @author iumyxF
 * @description: 限流类型
 * @date 2023/12/6 14:07
 */
public enum LimitType {

    /**
     * 全局限制
     */
    DEFAULT,

    /**
     * 根据请求者IP进行限流
     */
    IP
}

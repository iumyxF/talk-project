package com.example.talk.model.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author iumyxF
 * @description: 回答者列表
 * @date 2023/12/4 11:18
 */
public enum AnswererEnums {

    /**
     * 通义千问
     */
    TYQW("qwen"),

    /**
     * 微软new bing
     */
    NEW_BING("newbing");

    private final String name;

    AnswererEnums(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    /**
     * 根据名字获取枚举类
     *
     * @param name 回答者名字
     * @return 枚举结果
     */
    public static AnswererEnums getByName(String name) {
        for (AnswererEnums answererEnums : AnswererEnums.values()) {
            if (answererEnums.getName().equals(name)) {
                return answererEnums;
            }
        }
        return null;
    }

    /**
     * 获取所有的回答者名称
     *
     * @return 结果
     */
    public static List<String> getAllNames() {
        return Arrays.stream(AnswererEnums.values()).map(AnswererEnums::getName).collect(Collectors.toList());
    }
}

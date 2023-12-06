package com.example.talk.model.dto.talk;

import com.example.talk.common.ErrorCode;
import com.example.talk.exception.BusinessException;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * @author iumyxF
 * @description: 聊天内容重置请求
 * @date 2023/12/6 10:33
 */
@ApiModel("聊天内容重置请求")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TalkRestRequest implements Serializable {

    private final static long serialVersionUID = 1L;

    /**
     * {@link com.example.talk.model.enums.AnswererEnums}
     */
    @ApiModelProperty("模型")
    private String model;

    public void check() {
        if (StringUtils.isBlank(model)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
    }
}

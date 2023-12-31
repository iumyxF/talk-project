package com.example.talk.model.dto.talk;

import com.example.talk.common.ErrorCode;
import com.example.talk.exception.BusinessException;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * @author iumyxF
 * @description: 提问请求
 * @date 2023/12/4 14:37
 */
@Schema(description = "提问请求")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TalkQuestionRequest implements Serializable {

    private final static long serialVersionUID = 1L;

    @Schema(description = "问题")
    private String question;

    /**
     * {@link com.example.talk.model.enums.AnswererEnums}
     */
    @Schema(description = "模型,请参考模型接口所列举的模型")
    private String model;

    /**
     * 校验参数
     */
    public void check() {
        if (StringUtils.isBlank(question)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (StringUtils.isBlank(model)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
    }
}
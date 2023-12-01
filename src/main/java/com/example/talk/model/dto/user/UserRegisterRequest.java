package com.example.talk.model.dto.user;

import com.example.talk.common.ErrorCode;
import com.example.talk.exception.BusinessException;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * 用户注册请求体
 *
 * @author iumyxF
 */
@Data
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = 3191241716373120793L;

    private String account;

    private String password;

    private String checkPassword;

    /**
     * 检查参数是否合法
     */
    public void check() {
        if (StringUtils.isBlank(this.account)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (StringUtils.isBlank(this.password)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (StringUtils.isBlank(this.checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
    }
}

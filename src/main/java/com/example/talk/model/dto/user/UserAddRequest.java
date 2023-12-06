package com.example.talk.model.dto.user;

import com.example.talk.common.ErrorCode;
import com.example.talk.exception.BusinessException;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * 用户创建请求
 *
 * @author iumyxF
 */
@Data
public class UserAddRequest implements Serializable {

    /**
     * 用户昵称
     */
    private String username;

    /**
     * 账号
     */
    private String account;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 用户角色: user, admin
     */
    private String userRole;

    /**
     * 密码
     */
    private String password;

    private static final long serialVersionUID = 1L;

    public void check() {
        if (StringUtils.isBlank(username)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (StringUtils.isBlank(account)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (StringUtils.isBlank(password)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (StringUtils.isBlank(userRole)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (gender == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
    }
}
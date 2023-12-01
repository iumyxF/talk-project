package com.example.talk.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.talk.model.domain.User;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户服务
 *
 * @author iumyxF
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     *
     * @param account       用户账户
     * @param password      用户密码
     * @param checkPassword 校验密码
     * @return 新用户 id
     */
    long userRegister(String account, String password, String checkPassword);

    /**
     * 用户登录
     *
     * @param account  用户账户
     * @param password 用户密码
     * @param request  请求
     * @return 脱敏后的用户信息
     */
    User userLogin(String account, String password, HttpServletRequest request);

    /**
     * 获取当前登录用户
     *
     * @param request 请求
     * @return 用户实体
     */
    User getLoginUser(HttpServletRequest request);

    /**
     * 是否为管理员
     *
     * @param request 请求
     * @return 结果
     */
    boolean isAdmin(HttpServletRequest request);

    /**
     * 用户注销
     *
     * @param request 请求
     * @return 结果
     */
    boolean userLogout(HttpServletRequest request);
}

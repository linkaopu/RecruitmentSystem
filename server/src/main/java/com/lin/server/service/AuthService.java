package com.lin.server.service;

import com.lin.pojo.dto.LoginDTO;
import com.lin.pojo.dto.RegisterDTO;
import com.lin.pojo.vo.LoginVO;

/**
 * 认证服务接口
 */
public interface AuthService {
    
    /**
     * 用户登录
     * @param loginDTO 登录参数
     * @return 登录响应（包含token和用户信息）
     */
    LoginVO login(LoginDTO loginDTO);
    
    /**
     * 用户注册
     * @param registerDTO 注册参数
     * @return 用户信息
     */
    LoginVO register(RegisterDTO registerDTO);
    
    /**
     * 用户登出
     * @param token JWT Token
     */
    void logout(String token);
    
    /**
     * 发送邮箱验证码
     * @param email 邮箱地址
     */
    void sendCode(String email);
    
    /**
     * 重置密码
     * @param email 邮箱地址
     * @param code 验证码
     * @param newPassword 新密码
     */
    void resetPassword(String email, String code, String newPassword);
    
    /**
     * 修改密码
     * @param userId 用户ID
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     */
    void changePassword(Integer userId, String oldPassword, String newPassword);
}

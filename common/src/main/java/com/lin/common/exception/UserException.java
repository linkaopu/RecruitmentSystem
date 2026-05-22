package com.lin.common.exception;

/**
 * 用户相关异常
 */
public class UserException extends BusinessException {
    
    public UserException(String message) {
        super(400, message);
    }
    
    public UserException(Integer code, String message) {
        super(code, message);
    }
    
    /**
     * 用户不存在
     */
    public static UserException notFound() {
        return new UserException("用户不存在");
    }
    
    /**
     * 用户名已存在
     */
    public static UserException usernameExists() {
        return new UserException("用户名已存在");
    }
    
    /**
     * 邮箱已存在
     */
    public static UserException emailExists() {
        return new UserException("邮箱已被注册");
    }
    
    /**
     * 手机号已存在
     */
    public static UserException phoneExists() {
        return new UserException("手机号已被注册");
    }
    
    /**
     * 密码错误
     */
    public static UserException passwordError() {
        return new UserException("密码错误");
    }
    
    /**
     * 账号被禁用
     */
    public static UserException accountDisabled() {
        return new UserException("账号已被禁用");
    }
}

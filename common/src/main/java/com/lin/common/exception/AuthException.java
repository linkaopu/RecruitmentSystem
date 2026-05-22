package com.lin.common.exception;

/**
 * 认证授权异常
 */
public class AuthException extends BusinessException {
    
    public AuthException(String message) {
        super(401, message);
    }
    
    public AuthException(Integer code, String message) {
        super(code, message);
    }
    
    /**
     * Token无效
     */
    public static AuthException invalidToken() {
        return new AuthException("Token无效或已过期");
    }
    
    /**
     * Token缺失
     */
    public static AuthException missingToken() {
        return new AuthException("缺少认证Token");
    }
    
    /**
     * 未登录
     */
    public static AuthException notLoggedIn() {
        return new AuthException("请先登录");
    }
    
    /**
     * 权限不足
     */
    public static AuthException insufficientPermission() {
        return new AuthException(403, "权限不足，无法访问");
    }
    
    /**
     * 角色不匹配
     */
    public static AuthException roleMismatch() {
        return new AuthException("角色不匹配，无权执行此操作");
    }
}

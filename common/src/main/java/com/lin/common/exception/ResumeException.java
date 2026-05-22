package com.lin.common.exception;

/**
 * 简历相关异常
 */
public class ResumeException extends BusinessException {
    
    public ResumeException(String message) {
        super(400, message);
    }
    
    public ResumeException(Integer code, String message) {
        super(code, message);
    }
    
    /**
     * 简历不存在
     */
    public static ResumeException notFound() {
        return new ResumeException("简历不存在");
    }
    
    /**
     * 无权限操作
     */
    public static ResumeException noPermission() {
        return new ResumeException("无权限操作该简历");
    }
    
    /**
     * 简历信息不完整
     */
    public static ResumeException incomplete() {
        return new ResumeException("简历信息不完整，请补充完整后再申请");
    }
}

package com.lin.common.exception;

/**
 * 职位相关异常
 */
public class JobException extends BusinessException {
    
    public JobException(String message) {
        super(400, message);
    }
    
    public JobException(Integer code, String message) {
        super(code, message);
    }
    
    /**
     * 职位不存在
     */
    public static JobException notFound() {
        return new JobException("职位不存在");
    }
    
    /**
     * 职位已关闭
     */
    public static JobException jobClosed() {
        return new JobException("该职位已关闭招聘");
    }
    
    /**
     * 无权限操作
     */
    public static JobException noPermission() {
        return new JobException("无权限操作该职位");
    }
}

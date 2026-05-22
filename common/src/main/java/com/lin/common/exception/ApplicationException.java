package com.lin.common.exception;

/**
 * 申请相关异常
 */
public class ApplicationException extends BusinessException {
    
    public ApplicationException(String message) {
        super(400, message);
    }
    
    public ApplicationException(Integer code, String message) {
        super(code, message);
    }
    
    /**
     * 申请不存在
     */
    public static ApplicationException notFound() {
        return new ApplicationException("申请记录不存在");
    }
    
    /**
     * 重复申请
     */
    public static ApplicationException duplicateApplication() {
        return new ApplicationException("您已申请过该职位，请勿重复申请");
    }
    
    /**
     * 无权限操作
     */
    public static ApplicationException noPermission() {
        return new ApplicationException("无权限操作该申请");
    }
    
    /**
     * 申请状态不允许此操作
     */
    public static ApplicationException invalidStatus() {
        return new ApplicationException("当前申请状态不允许此操作");
    }
}

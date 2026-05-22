package com.lin.common.exception;

/**
 * 面试相关异常
 */
public class InterviewException extends BusinessException {
    
    public InterviewException(String message) {
        super(400, message);
    }
    
    public InterviewException(Integer code, String message) {
        super(code, message);
    }
    
    /**
     * 面试安排不存在
     */
    public static InterviewException notFound() {
        return new InterviewException("面试安排不存在");
    }
    
    /**
     * 面试时间冲突
     */
    public static InterviewException timeConflict() {
        return new InterviewException("面试时间冲突，请选择其他时间");
    }
    
    /**
     * 无权限操作
     */
    public static InterviewException noPermission() {
        return new InterviewException("无权限操作该面试安排");
    }
    
    /**
     * 面试结果已记录
     */
    public static InterviewException resultAlreadyRecorded() {
        return new InterviewException("面试结果已记录，无法重复提交");
    }
}

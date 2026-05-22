package com.lin.server.service;

/**
 * 邮件服务接口
 */
public interface EmailService {
    
    /**
     * 发送验证码邮件
     *
     * @param toEmail 收件人邮箱
     * @param code    验证码
     */
    void sendVerificationCode(String toEmail, String code);
    
    /**
     * 发送简单文本邮件
     *
     * @param toEmail 收件人邮箱
     * @param subject 邮件主题
     * @param content 邮件内容
     */
    void sendSimpleEmail(String toEmail, String subject, String content);
}

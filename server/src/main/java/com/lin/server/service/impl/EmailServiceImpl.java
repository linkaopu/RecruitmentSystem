package com.lin.server.service.impl;

import com.lin.common.exception.BusinessException;
import com.lin.server.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import com.lin.common.constant.EmailTextHTMLConstant;

/**
 * 邮件服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    
    private final JavaMailSender mailSender;
    
    @Value("${spring.mail.username}")
    private String fromEmail;
    
    @Override
    public void sendVerificationCode(String toEmail, String code) {
        log.info("发送验证码邮件，收件人: {}", toEmail);
        
        String subject = "招聘系统 - 邮箱验证码";
        String content = buildVerificationCodeHtml(code);
        
        sendHtmlEmail(toEmail, subject, content);
        
        log.info("验证码邮件发送成功，收件人: {}", toEmail);
    }
    
    @Override
    public void sendSimpleEmail(String toEmail, String subject, String content) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            
            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(content);
            
            mailSender.send(message);
            log.info("简单邮件发送成功，收件人: {}, 主题: {}", toEmail, subject);
        } catch (MessagingException e) {
            log.error("发送邮件失败，收件人: {}, 错误: {}", toEmail, e.getMessage());
            throw new BusinessException("邮件发送失败: " + e.getMessage());
        }
    }
    
    /**
     * 发送HTML格式邮件
     */
    private void sendHtmlEmail(String toEmail, String subject, String htmlContent) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            
            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(htmlContent, true); // true表示HTML内容
            
            mailSender.send(message);
            log.info("HTML邮件发送成功，收件人: {}", toEmail);
        } catch (MessagingException e) {
            log.error("发送HTML邮件失败，收件人: {}, 错误: {}", toEmail, e.getMessage());
            throw new BusinessException("邮件发送失败: " + e.getMessage());
        }
    }
    
    /**
     * 构建验证码HTML邮件内容
     */
    private String buildVerificationCodeHtml(String code) {
        return EmailTextHTMLConstant.VERIFICATION_CODE_HTML.formatted(code);
    }
}

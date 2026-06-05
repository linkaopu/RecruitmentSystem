package com.lin.common.util;

import lombok.extern.slf4j.Slf4j;

import java.security.SecureRandom;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 邮箱验证码工具类
 */
@Slf4j
public class EmailCodeUtil {
    
    /**
     * 验证码长度
     */
    private static final int CODE_LENGTH = 6;
    
    /**
     * 验证码有效期（毫秒）- 默认5分钟
     */
    private static final long EXPIRE_TIME = 5 * 60 * 1000L;
    
    /**
     * 发送频率限制（毫秒）- 默认60秒
     */
    private static final long SEND_INTERVAL = 60 * 1000L;
    
    /**
     * 存储验证码的Map（生产环境建议使用Redis）
     * Key: email, Value: CodeInfo
     */
    private static final Map<String, CodeInfo> codeStore = new ConcurrentHashMap<>();
    
    /**
     * 生成6位随机验证码
     *
     * @return 验证码字符串
     */
    public static String generateCode() {
        SecureRandom random = new SecureRandom();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < CODE_LENGTH; i++) {
            code.append(random.nextInt(10));
        }
        return code.toString();
    }
    
    /**
     * 存储验证码
     *
     * @param email 邮箱地址
     * @param code  验证码
     */
    public static void storeCode(String email, String code) {
        long now = System.currentTimeMillis();
        CodeInfo codeInfo = new CodeInfo(code, now + EXPIRE_TIME, now);
        codeStore.put(email, codeInfo);
        log.debug("验证码已存储，邮箱: {}, 验证码: {}", email, code);
    }
    
    /**
     * 验证验证码是否正确
     *
     * @param email 邮箱地址
     * @param code  用户输入的验证码
     * @return true-正确，false-错误
     */
    public static boolean verifyCode(String email, String code) {
        CodeInfo codeInfo = codeStore.get(email);
        
        if (codeInfo == null) {
            log.warn("验证码不存在，邮箱: {}", email);
            return false;
        }
        
        // 检查是否过期
        if (System.currentTimeMillis() > codeInfo.getExpireTime()) {
            log.warn("验证码已过期，邮箱: {}", email);
            codeStore.remove(email);
            return false;
        }
        
        // 比对验证码
        boolean isValid = codeInfo.getCode().equals(code);
        if (isValid) {
            // 验证成功后删除验证码（一次性使用）
            codeStore.remove(email);
            log.info("验证码验证成功，邮箱: {}", email);
        } else {
            log.warn("验证码错误，邮箱: {}", email);
        }
        
        return isValid;
    }
    
    /**
     * 检查是否可以发送验证码（频率限制）
     *
     * @param email 邮箱地址
     * @return true-可以发送，false-发送过于频繁
     */
    public static boolean canSendCode(String email) {
        CodeInfo codeInfo = codeStore.get(email);
        
        if (codeInfo == null) {
            return true;
        }
        
        long now = System.currentTimeMillis();
        long timeSinceLastSend = now - codeInfo.getLastSendTime();
        
        if (timeSinceLastSend < SEND_INTERVAL) {
            log.warn("发送验证码过于频繁，邮箱: {}, 距离上次发送: {}秒", 
                    email, timeSinceLastSend / 1000);
            return false;
        }
        
        return true;
    }
    
    /**
     * 更新最后发送时间
     *
     * @param email 邮箱地址
     */
    public static void updateLastSendTime(String email) {
        CodeInfo codeInfo = codeStore.get(email);
        if (codeInfo != null) {
            codeInfo.setLastSendTime(System.currentTimeMillis());
        }
    }
    
    /**
     * 清除验证码
     *
     * @param email 邮箱地址
     */
    public static void clearCode(String email) {
        codeStore.remove(email);
        log.debug("验证码已清除，邮箱: {}", email);
    }
    
    /**
     * 验证码信息内部类
     */
    private static class CodeInfo {
        private String code;
        private long expireTime;
        private long lastSendTime;
        
        public CodeInfo(String code, long expireTime, long lastSendTime) {
            this.code = code;
            this.expireTime = expireTime;
            this.lastSendTime = lastSendTime;
        }
        
        public String getCode() {
            return code;
        }
        
        public long getExpireTime() {
            return expireTime;
        }
        
        public long getLastSendTime() {
            return lastSendTime;
        }
        
        public void setLastSendTime(long lastSendTime) {
            this.lastSendTime = lastSendTime;
        }
    }
}

package com.lin.common.constant;

/**
 * Redis Key 常量
 */
public class RedisConstant {
    
    /**
     * Token缓存前缀
     */
    public static final String TOKEN_PREFIX = "token:";
    
    /**
     * 验证码缓存前缀
     */
    public static final String CAPTCHA_PREFIX = "captcha:";
    
    /**
     * 用户信息缓存前缀
     */
    public static final String USER_INFO_PREFIX = "user:info:";
    
    /**
     * 职位缓存前缀
     */
    public static final String JOB_PREFIX = "job:";
    
    /**
     * 热门职位列表
     */
    public static final String HOT_JOBS = "jobs:hot";
    
    /**
     * 最新职位列表
     */
    public static final String NEW_JOBS = "jobs:new";
    
    /**
     * 简历缓存前缀
     */
    public static final String RESUME_PREFIX = "resume:";
    
    /**
     * 申请记录缓存前缀
     */
    public static final String APPLICATION_PREFIX = "application:";
    
    /**
     * 面试通知缓存前缀
     */
    public static final String INTERVIEW_NOTIFICATION_PREFIX = "interview:notification:";
    
    /**
     * Token过期时间（秒）- 7天
     */
    public static final long TOKEN_EXPIRE_TIME = 7 * 24 * 60 * 60;
    
    /**
     * 验证码过期时间（秒）- 5分钟
     */
    public static final long CAPTCHA_EXPIRE_TIME = 5 * 60;
    
    /**
     * 职位缓存过期时间（秒）- 1小时
     */
    public static final long JOB_CACHE_EXPIRE_TIME = 60 * 60;
}

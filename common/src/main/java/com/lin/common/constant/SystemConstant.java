package com.lin.common.constant;

/**
 * 系统通用常量
 */
public class SystemConstant {
    
    /**
     * UTF-8 编码
     */
    public static final String UTF8 = "UTF-8";
    
    /**
     * 成功标记
     */
    public static final Integer SUCCESS = 200;
    
    /**
     * 失败标记
     */
    public static final Integer FAIL = 500;
    
    /**
     * 默认分页大小
     */
    public static final Integer DEFAULT_PAGE_SIZE = 10;
    
    /**
     * 最大分页大小
     */
    public static final Integer MAX_PAGE_SIZE = 100;
    
    /**
     * 默认页码
     */
    public static final Integer DEFAULT_PAGE_NUM = 1;
    
    /**
     * JWT Token 前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";
    
    /**
     * Token Header名称
     */
    public static final String TOKEN_HEADER = "Authorization";
    
    /**
     * 用户ID Header名称
     */
    public static final String USER_ID_HEADER = "X-User-Id";
    
    /**
     * 匿名用户 ID
     */
    public static final Long ANONYMOUS_USER_ID = -1L;
}

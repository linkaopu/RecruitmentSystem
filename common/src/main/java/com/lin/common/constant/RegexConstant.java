package com.lin.common.constant;

/**
 * 正则表达式常量
 */
public class RegexConstant {
    
    /**
     * 手机号正则表达式
     */
    public static final String PHONE_REGEX = "^1[3-9]\\d{9}$";
    
    /**
     * 邮箱正则表达式
     */
    public static final String EMAIL_REGEX = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
    
    /**
     * 密码正则表达式（至少8位，包含字母和数字）
     */
    public static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$";
    
    /**
     * 用户名正则表达式（字母、数字、下划线，4-20位）
     */
    public static final String USERNAME_REGEX = "^[a-zA-Z0-9_]{4,20}$";
    
    /**
     * 身份证号码正则表达式
     */
    public static final String ID_CARD_REGEX = "^(^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$)|(^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])((\\d{4})|\\d{3}[Xx])$)$";
    
    /**
     * URL正则表达式
     */
    public static final String URL_REGEX = "^(https?|ftp)://[^\\s/$.?#].[^\\s]*$";
}

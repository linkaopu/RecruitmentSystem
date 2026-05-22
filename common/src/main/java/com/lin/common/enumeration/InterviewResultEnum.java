package com.lin.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 面试结果枚举
 */
@Getter
@AllArgsConstructor
public enum InterviewResultEnum {
    
    /**
     * 待定
     */
    PENDING("pending", "待定"),
    
    /**
     * 通过
     */
    PASS("pass", "通过"),
    
    /**
     * 未通过
     */
    FAIL("fail", "未通过");
    
    private final String code;
    private final String description;
    
    /**
     * 根据code获取枚举
     */
    public static InterviewResultEnum getByCode(String code) {
        for (InterviewResultEnum result : values()) {
            if (result.getCode().equals(code)) {
                return result;
            }
        }
        return null;
    }
}

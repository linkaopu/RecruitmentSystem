package com.lin.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 面试方式枚举
 */
@Getter
@AllArgsConstructor
public enum InterviewMethodEnum {
    
    /**
     * 线上面试
     */
    ONLINE("online", "线上面试"),
    
    /**
     * 线下面试
     */
    OFFLINE("offline", "线下面试");
    
    private final String code;
    private final String description;
    
    /**
     * 根据code获取枚举
     */
    public static InterviewMethodEnum getByCode(String code) {
        for (InterviewMethodEnum method : values()) {
            if (method.getCode().equals(code)) {
                return method;
            }
        }
        return null;
    }
}

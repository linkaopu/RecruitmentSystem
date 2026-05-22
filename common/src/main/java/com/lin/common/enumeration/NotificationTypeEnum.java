package com.lin.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 通知类型枚举
 */
@Getter
@AllArgsConstructor
public enum NotificationTypeEnum {
    
    /**
     * 面试通知
     */
    INTERVIEW("interview", "面试通知"),
    
    /**
     * 申请通知
     */
    APPLICATION("application", "申请通知"),
    
    /**
     * 系统通知
     */
    SYSTEM("system", "系统通知");
    
    private final String code;
    private final String description;
    
    /**
     * 根据code获取枚举
     */
    public static NotificationTypeEnum getByCode(String code) {
        for (NotificationTypeEnum type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }
}

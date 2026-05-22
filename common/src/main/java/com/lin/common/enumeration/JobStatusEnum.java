package com.lin.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 职位状态枚举
 */
@Getter
@AllArgsConstructor
public enum JobStatusEnum {
    
    /**
     * 招聘中
     */
    ACTIVE("active", "招聘中"),
    
    /**
     * 已关闭
     */
    INACTIVE("inactive", "已关闭");
    
    private final String code;
    private final String description;
    
    /**
     * 根据code获取枚举
     */
    public static JobStatusEnum getByCode(String code) {
        for (JobStatusEnum status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }
}

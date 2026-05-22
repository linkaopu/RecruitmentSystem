package com.lin.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 申请状态枚举
 */
@Getter
@AllArgsConstructor
public enum ApplicationStatusEnum {
    
    /**
     * 待处理
     */
    PENDING("pending", "待处理"),
    
    /**
     * 已筛选
     */
    SCREENED("screened", "已筛选"),
    
    /**
     * 面试中
     */
    INTERVIEW("interview", "面试中"),
    
    /**
     * 已录用
     */
    HIRED("hired", "已录用"),
    
    /**
     * 已拒绝
     */
    REJECTED("rejected", "已拒绝");
    
    private final String code;
    private final String description;
    
    /**
     * 根据code获取枚举
     */
    public static ApplicationStatusEnum getByCode(String code) {
        for (ApplicationStatusEnum status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }
}

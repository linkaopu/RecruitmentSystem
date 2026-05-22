package com.lin.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户角色枚举
 */
@Getter
@AllArgsConstructor
public enum UserRoleEnum {
    
    /**
     * 求职者
     */
    CANDIDATE("candidate", "求职者"),
    
    /**
     * HR招聘专员
     */
    HR("hr", "HR招聘专员"),
    
    /**
     * 系统管理员
     */
    ADMIN("admin", "系统管理员");
    
    private final String code;
    private final String description;
    
    /**
     * 根据code获取枚举
     */
    public static UserRoleEnum getByCode(String code) {
        for (UserRoleEnum role : values()) {
            if (role.getCode().equals(code)) {
                return role;
            }
        }
        return null;
    }
}

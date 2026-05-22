package com.lin.common.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 性别枚举
 */
@Getter
@AllArgsConstructor
public enum GenderEnum {
    
    /**
     * 男
     */
    MALE("male", "男"),
    
    /**
     * 女
     */
    FEMALE("female", "女");
    
    private final String code;
    private final String description;
    
    /**
     * 根据code获取枚举
     */
    public static GenderEnum getByCode(String code) {
        for (GenderEnum gender : values()) {
            if (gender.getCode().equals(code)) {
                return gender;
            }
        }
        return null;
    }
}

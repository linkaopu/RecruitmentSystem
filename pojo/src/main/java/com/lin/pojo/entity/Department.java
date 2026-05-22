package com.lin.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 部门实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Department {
    /**
     * 部门ID
     */
    private Integer id;
    
    /**
     * 部门名称
     */
    private String name;
    
    /**
     * 部门代码
     */
    private String code;
    
    /**
     * 部门描述
     */
    private String description;
    
    /**
     * 是否删除：0-未删除，1-已删除
     */
    private Integer isDelete;
    
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
    
    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
}

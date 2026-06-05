package com.lin.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * 项目经历实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectExperience {
    /**
     * 项目ID
     */
    private Integer id;
    
    /**
     * 简历ID
     */
    private Integer resumeId;
    
    /**
     * 项目名称
     */
    private String name;
    
    /**
     * 担任角色
     */
    private String role;
    
    /**
     * 开始日期
     */
    private LocalDate startDate;
    
    /**
     * 结束日期
     */
    private LocalDate endDate;
    
    /**
     * 项目描述
     */
    private String description;
    
    /**
     * 使用技术(JSON格式)
     */
    private List<String> technologies;
    
    /**
     * 是否删除：0-未删除，1-已删除
     */
    private Integer isDelete;
}

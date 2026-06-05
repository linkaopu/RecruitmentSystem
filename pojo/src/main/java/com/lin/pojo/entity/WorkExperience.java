package com.lin.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 工作经历实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkExperience {
    /**
     * 经历ID
     */
    private Integer id;
    
    /**
     * 简历ID
     */
    private Integer resumeId;
    
    /**
     * 公司名称
     */
    private String company;
    
    /**
     * 职位
     */
    private String position;
    
    /**
     * 开始日期
     */
    private LocalDate startDate;
    
    /**
     * 结束日期
     */
    private LocalDate endDate;
    
    /**
     * 是否当前工作：0-否，1-是
     */
    private Integer isCurrent;
    
    /**
     * 工作描述
     */
    private String description;
    
    /**
     * 是否删除：0-未删除，1-已删除
     */
    private Integer isDelete;
}

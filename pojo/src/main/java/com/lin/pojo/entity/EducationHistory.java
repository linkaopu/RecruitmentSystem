package com.lin.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * 教育经历实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EducationHistory {
    /**
     * 教育经历ID
     */
    private Integer id;
    
    /**
     * 简历ID
     */
    private Integer resumeId;
    
    /**
     * 学校名称
     */
    private String school;
    
    /**
     * 专业
     */
    private String major;
    
    /**
     * 学位
     */
    private String degree;
    
    /**
     * 开始日期
     */
    private LocalDate startDate;
    
    /**
     * 结束日期
     */
    private LocalDate endDate;
    
    /**
     * 是否删除：0-未删除，1-已删除
     */
    private Integer isDelete;
}

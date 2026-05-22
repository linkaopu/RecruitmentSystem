package com.lin.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 职位实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Job {
    /**
     * 职位ID
     */
    private Integer id;
    
    /**
     * 职位标题
     */
    private String title;
    
    /**
     * 所属部门
     */
    private String department;
    
    /**
     * 工作地点
     */
    private String location;
    
    /**
     * 最低薪资
     */
    private Integer salaryMin;
    
    /**
     * 最高薪资
     */
    private Integer salaryMax;
    
    /**
     * 薪资显示文本
     */
    private String salaryDisplay;
    
    /**
     * 学历要求
     */
    private String education;
    
    /**
     * 经验要求
     */
    private String experience;
    
    /**
     * 招聘人数
     */
    private Integer headcount;
    
    /**
     * 职位描述
     */
    private String description;
    
    /**
     * 职位要求
     */
    private String requirements;
    
    /**
     * 福利待遇
     */
    private String benefits;
    
    /**
     * 状态: active(招聘中), inactive(已关闭)
     */
    private String status;
    
    /**
     * 浏览量
     */
    private Integer viewCount;
    
    /**
     * 申请数
     */
    private Integer applyCount;
    
    /**
     * 是否热门
     */
    private Boolean isHot;
    
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

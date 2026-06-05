package com.lin.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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
     * 负责该职位的HR用户ID
     */
    private Integer hrId;

    /**
     * 所属部门ID
     */
    private Integer departmentId;

    /**
     * 职位标题
     */
    private String title;

    /**
     * 工作地点
     */
    private String location;

    /**
     * 最低薪资（NULL表示面议）
     */
    private Integer salaryMin;

    /**
     * 最高薪资
     */
    private Integer salaryMax;

    /**
     * 薪资展示文案（如"15-25K·14薪"）
     */
    private String salaryDisplay;

    /**
     * 是否面议：0-否，1-是
     */
    private Integer isNegotiable;

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
     * 职位描述（岗位职责）
     */
    private String description;

    /**
     * 任职要求
     */
    private String requirements;

    /**
     * 福利待遇
     */
    private String benefits;

    /**
     * 职位状态: draft(草稿), pending(待审核), active(已发布), closed(关闭), rejected(驳回)
     */
    private String status;

    /**
     * 招聘截止日期
     */
    private LocalDate deadline;

    /**
     * 浏览量
     */
    private Integer viewCount;

    /**
     * 申请数
     */
    private Integer applyCount;

    /**
     * 是否热门：0-否，1-是
     */
    private Integer isHot;

    /**
     * 审核人ID
     */
    private Integer approvedBy;

    /**
     * 审核通过时间
     */
    private LocalDateTime approvedAt;

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

    // ========== 关联数据（JOIN查询时填充） ==========

    /**
     * 所属部门信息
     */
    private Department department;

    /**
     * HR姓名
     */
    private String hrName;
}

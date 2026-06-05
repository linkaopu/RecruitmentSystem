package com.lin.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

/**
 * 创建/更新职位参数DTO
 */
@Data
@Schema(description = "创建/更新职位参数")
public class CreateJobDTO {
    /**
     * 负责该职位的HR用户ID
     */
    @Schema(description = "HR用户ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "11")
    private Integer hrId;

    /**
     * 所属部门ID
     */
    @Schema(description = "部门ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer departmentId;

    /**
     * 职位标题
     */
    @Schema(description = "职位标题", requiredMode = Schema.RequiredMode.REQUIRED, example = "Java开发工程师")
    private String title;

    /**
     * 工作地点
     */
    @Schema(description = "工作地点", requiredMode = Schema.RequiredMode.REQUIRED, example = "上海")
    private String location;

    /**
     * 最低薪资（NULL表示面议）
     */
    @Schema(description = "最低薪资", example = "15000")
    private Integer salaryMin;

    /**
     * 最高薪资
     */
    @Schema(description = "最高薪资", example = "25000")
    private Integer salaryMax;

    /**
     * 薪资展示文案（如"15-25K·14薪"）
     */
    @Schema(description = "薪资展示文案", requiredMode = Schema.RequiredMode.REQUIRED, example = "15-25K·14薪")
    private String salaryDisplay;

    /**
     * 是否面议：0-否，1-是
     */
    @Schema(description = "是否面议", example = "0")
    private Integer isNegotiable;

    /**
     * 学历要求
     */
    @Schema(description = "学历要求", requiredMode = Schema.RequiredMode.REQUIRED, example = "本科")
    private String education;

    /**
     * 经验要求
     */
    @Schema(description = "工作经验要求", requiredMode = Schema.RequiredMode.REQUIRED, example = "3-5年")
    private String experience;

    /**
     * 招聘人数
     */
    @Schema(description = "招聘人数", requiredMode = Schema.RequiredMode.REQUIRED, example = "3")
    private Integer headcount;

    /**
     * 岗位职责
     */
    @Schema(description = "岗位职责", requiredMode = Schema.RequiredMode.REQUIRED, example = "负责公司核心业务系统后端开发")
    private String description;

    /**
     * 任职要求
     */
    @Schema(description = "任职要求", requiredMode = Schema.RequiredMode.REQUIRED, example = "精通Java, Spring Boot, MySQL")
    private String requirements;

    /**
     * 福利待遇
     */
    @Schema(description = "福利待遇", example = "五险一金, 弹性工作")
    private String benefits;

    /**
     * 职位状态
     */
    @Schema(description = "职位状态: draft/pending/active/closed/rejected", example = "draft")
    private String status;

    /**
     * 招聘截止日期
     */
    @Schema(description = "招聘截止日期", example = "2025-12-31")
    private LocalDate deadline;

    /**
     * 是否热门：0-否，1-是
     */
    @Schema(description = "是否热门", example = "0")
    private Integer isHot;
}

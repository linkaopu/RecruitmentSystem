package com.lin.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 创建简历参数DTO
 */
@Data
@Schema(description = "创建简历参数")
public class CreateResumeDTO {
    /**
     * 姓名
     */
    @Schema(description = "姓名", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    private String name;

    /**
     * 手机号
     */
    @Schema(description = "手机号", requiredMode = Schema.RequiredMode.REQUIRED, example = "13800138000")
    private String phone;

    /**
     * 邮箱
     */
    @Schema(description = "邮箱", requiredMode = Schema.RequiredMode.REQUIRED, example = "zhangsan@example.com")
    private String email;

    /**
     * 年龄
     */
    @Schema(description = "年龄", example = "28")
    private Integer age;

    /**
     * 性别: male/female
     */
    @Schema(description = "性别", example = "male", allowableValues = {"male", "female"})
    private String gender;

    /**
     * 学历
     */
    @Schema(description = "学历", requiredMode = Schema.RequiredMode.REQUIRED, example = "本科")
    private String education;

    /**
     * 头像URL
     */
    @Schema(description = "头像URL", example = "https://example.com/avatar.jpg")
    private String avatar;

    /**
     * 技能标签（JSON数组）
     */
    @Schema(description = "技能标签列表", example = "[\"Java\",\"Spring\",\"MySQL\"]")
    private List<String> skills;

    /**
     * 附件简历URL
     */
    @Schema(description = "附件简历URL", example = "https://example.com/resume.pdf")
    private String attachmentUrl;

    /**
     * 附件简历名称
     */
    @Schema(description = "附件简历名称", example = "张三_Java开发_简历.pdf")
    private String attachmentName;

    /**
     * 自我介绍
     */
    @Schema(description = "自我介绍", example = "3年Java开发经验，热爱技术")
    private String selfIntroduction;

    /**
     * 工作经历
     */
    @Schema(description = "工作经历列表")
    private List<WorkExperienceItem> workExperience;

    /**
     * 教育经历
     */
    @Schema(description = "教育经历列表")
    private List<EducationItem> educationHistory;

    /**
     * 项目经历
     */
    @Schema(description = "项目经历列表")
    private List<ProjectItem> projects;

    // ========== 内部类 ==========

    @Data
    @Schema(description = "工作经历")
    public static class WorkExperienceItem {
        @Schema(description = "公司名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "ABC科技")
        private String company;
        @Schema(description = "职位名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "Java开发")
        private String position;
        @Schema(description = "开始日期", requiredMode = Schema.RequiredMode.REQUIRED, example = "2021-07-01")
        private String startDate;
        @Schema(description = "结束日期", example = "2023-04-15")
        private String endDate;
        @Schema(description = "是否当前工作", example = "0")
        private Integer isCurrent;
        @Schema(description = "工作内容描述", example = "参与电商系统开发")
        private String description;
    }

    @Data
    @Schema(description = "教育经历")
    public static class EducationItem {
        @Schema(description = "学校名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "上海交通大学")
        private String school;
        @Schema(description = "专业", requiredMode = Schema.RequiredMode.REQUIRED, example = "计算机科学与技术")
        private String major;
        @Schema(description = "学位", requiredMode = Schema.RequiredMode.REQUIRED, example = "本科")
        private String degree;
        @Schema(description = "开始日期", requiredMode = Schema.RequiredMode.REQUIRED, example = "2015-09-01")
        private String startDate;
        @Schema(description = "结束日期", requiredMode = Schema.RequiredMode.REQUIRED, example = "2019-06-30")
        private String endDate;
    }

    @Data
    @Schema(description = "项目经历")
    public static class ProjectItem {
        @Schema(description = "项目名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "电商秒杀系统")
        private String name;
        @Schema(description = "角色", requiredMode = Schema.RequiredMode.REQUIRED, example = "核心开发")
        private String role;
        @Schema(description = "开始日期", requiredMode = Schema.RequiredMode.REQUIRED, example = "2022-03-01")
        private String startDate;
        @Schema(description = "结束日期", example = "2022-08-31")
        private String endDate;
        @Schema(description = "项目描述", example = "设计高并发秒杀方案")
        private String description;
        @Schema(description = "技术栈", example = "[\"Java\",\"Redis\",\"RabbitMQ\"]")
        private List<String> technologies;
    }
}

package com.lin.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 简历实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Resume {
    /**
     * 简历ID
     */
    private Integer id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 性别: male(男), female(女)
     */
    private String gender;

    /**
     * 学历
     */
    private String education;

    /**
     * 头像URL
     */
    private String avatar;

    /**
     * 技能(JSON格式)
     */
    private List<String> skills;

    /**
     * 附件URL
     */
    private String attachmentUrl;

    /**
     * 附件名称
     */
    private String attachmentName;

    /**
     * 自我介绍
     */
    private String selfIntroduction;

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
     * 工作经历列表
     */
    private List<WorkExperience> workExperience;

    /**
     * 教育经历列表
     */
    private List<EducationHistory> educationHistory;

    /**
     * 项目经历列表
     */
    private List<ProjectExperience> projects;
}

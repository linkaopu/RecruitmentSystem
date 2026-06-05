package com.lin.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 职位查询参数DTO
 */
@Data
@Schema(description = "职位查询参数")
public class JobQueryDTO {
    /**
     * 搜索关键词
     */
    @Schema(description = "搜索关键词（匹配标题和描述）", example = "Java")
    private String keyword;

    /**
     * 部门ID
     */
    @Schema(description = "部门ID", example = "1")
    private Integer departmentId;

    /**
     * 工作地点
     */
    @Schema(description = "工作地点", example = "上海")
    private String location;

    /**
     * 最低薪资
     */
    @Schema(description = "最低薪资", example = "10000")
    private Integer salaryMin;

    /**
     * 最高薪资
     */
    @Schema(description = "最高薪资", example = "30000")
    private Integer salaryMax;

    /**
     * 学历要求
     */
    @Schema(description = "学历要求", example = "本科")
    private String education;

    /**
     * 工作经验要求
     */
    @Schema(description = "工作经验要求", example = "3-5年")
    private String experience;

    /**
     * 职位状态
     */
    @Schema(description = "职位状态: draft/pending/active/closed/rejected", example = "active")
    private String status;

    /**
     * 排序方式: latest/salary/hot
     */
    @Schema(description = "排序方式: latest(最新), salary(薪资), hot(热门)", example = "latest")
    private String sortBy;

    /**
     * 页码
     */
    @Schema(description = "页码", example = "1")
    private Integer page = 1;

    /**
     * 每页数量
     */
    @Schema(description = "每页数量", example = "10")
    private Integer pageSize = 10;
}

package com.lin.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 创建面试参数DTO
 */
@Data
@Schema(description = "创建面试参数")
public class CreateInterviewDTO {
    /**
     * 申请记录ID
     */
    @Schema(description = "申请记录ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer applicationId;

    /**
     * 职位ID
     */
    @Schema(description = "职位ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer jobId;

    /**
     * 用户ID（求职者）
     */
    @Schema(description = "求职者用户ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer userId;

    /**
     * 简历ID
     */
    @Schema(description = "简历ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Integer resumeId;

    /**
     * 职位标题（冗余快照）
     */
    @Schema(description = "职位标题", requiredMode = Schema.RequiredMode.REQUIRED, example = "Java开发工程师")
    private String jobTitle;

    /**
     * 用户姓名（冗余快照）
     */
    @Schema(description = "求职者姓名", requiredMode = Schema.RequiredMode.REQUIRED, example = "张明")
    private String userName;

    /**
     * 面试官用户ID
     */
    @Schema(description = "面试官用户ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "11")
    private Integer interviewerId;

    /**
     * 面试官姓名
     */
    @Schema(description = "面试官姓名", requiredMode = Schema.RequiredMode.REQUIRED, example = "李华")
    private String interviewerName;

    /**
     * 面试开始时间
     */
    @Schema(description = "面试开始时间", requiredMode = Schema.RequiredMode.REQUIRED, example = "2025-01-20T10:00:00")
    private String startTime;

    /**
     * 面试结束时间
     */
    @Schema(description = "面试结束时间", requiredMode = Schema.RequiredMode.REQUIRED, example = "2025-01-20T11:00:00")
    private String endTime;

    /**
     * 面试地点或线上会议链接
     */
    @Schema(description = "线下地址或线上会议链接", example = "上海市浦东新区xx大厦3楼")
    private String location;

    /**
     * 面试方式: online(线上)/offline(线下)
     */
    @Schema(description = "面试方式", requiredMode = Schema.RequiredMode.REQUIRED, example = "offline",
            allowableValues = {"online", "offline"})
    private String method;

    /**
     * 面试备注
     */
    @Schema(description = "面试备注", example = "请携带纸质简历")
    private String notes;
}

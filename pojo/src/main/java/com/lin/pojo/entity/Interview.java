package com.lin.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 面试实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Interview {
    /**
     * 面试ID
     */
    private Integer id;
    
    /**
     * 申请ID
     */
    private Integer applicationId;
    
    /**
     * 职位ID
     */
    private Integer jobId;
    
    /**
     * 用户ID
     */
    private Integer userId;
    
    /**
     * 简历ID
     */
    private Integer resumeId;
    
    /**
     * 职位标题
     */
    private String jobTitle;
    
    /**
     * 用户姓名
     */
    private String userName;
    
    /**
     * 面试官
     */
    private String interviewer;
    
    /**
     * 面试时间
     */
    private LocalDateTime interviewTime;
    
    /**
     * 面试地点
     */
    private String location;
    
    /**
     * 面试方式: online(线上), offline(线下)
     */
    private String method;
    
    /**
     * 面试结果: pending(待定), pass(通过), fail(未通过)
     */
    private String result;
    
    /**
     * 备注
     */
    private String notes;
    
    /**
     * 是否已通知
     */
    private Boolean notified;
    
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

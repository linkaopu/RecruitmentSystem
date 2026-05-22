package com.lin.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 职位申请实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Application {
    /**
     * 申请ID
     */
    private Integer id;
    
    /**
     * 职位ID
     */
    private Integer jobId;
    
    /**
     * 简历ID
     */
    private Integer resumeId;
    
    /**
     * 用户ID
     */
    private Integer userId;
    
    /**
     * 用户姓名
     */
    private String userName;
    
    /**
     * 职位标题
     */
    private String jobTitle;
    
    /**
     * 申请状态: pending(待处理), screened(已筛选), interview(面试中), hired(已录用), rejected(已拒绝)
     */
    private String status;
    
    /**
     * 拒绝原因
     */
    private String rejectReason;
    
    /**
     * 申请时间
     */
    private LocalDateTime appliedAt;
    
    /**
     * 是否删除：0-未删除，1-已删除
     */
    private Integer isDelete;
    
    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
}

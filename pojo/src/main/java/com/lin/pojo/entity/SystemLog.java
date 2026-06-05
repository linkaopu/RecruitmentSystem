package com.lin.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 系统日志实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemLog {
    /**
     * 日志ID
     */
    private Integer id;
    
    /**
     * 用户ID
     */
    private Integer userId;
    
    /**
     * 用户名
     */
    private String username;

    /**
     * 操作类型（如 LOGIN, APPLY, UPDATE_JOB）
     */
    private String actionType;

    /**
     * 操作行为描述
     */
    private String action;

    /**
     * 操作对象ID
     */
    private Integer targetId;

    /**
     * IP地址
     */
    private String ip;
    
    /**
     * 用户代理
     */
    private String userAgent;
    
    /**
     * 是否删除：0-未删除，1-已删除
     */
    private Integer isDelete;
    
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
}

package com.lin.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 通知实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    /**
     * 通知ID
     */
    private Integer id;
    
    /**
     * 用户ID（接收者）
     */
    private Integer userId;

    /**
     * 发送者用户ID（NULL表示系统发送）
     */
    private Integer fromUserId;

    /**
     * 通知标题
     */
    private String title;
    
    /**
     * 通知内容
     */
    private String content;
    
    /**
     * 通知类型: interview(面试), application(申请), system(系统)
     */
    private String type;
    
    /**
     * 是否已读
     */
    private Integer isRead;
    
    /**
     * 是否删除：0-未删除，1-已删除
     */
    private Integer isDelete;
    
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
}

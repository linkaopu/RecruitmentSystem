package com.lin.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 收藏实体类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Favorite {
    /**
     * 收藏ID
     */
    private Integer id;
    
    /**
     * 用户ID
     */
    private Integer userId;
    
    /**
     * 职位ID
     */
    private Integer jobId;
    
    /**
     * 是否删除：0-未删除，1-已删除
     */
    private Integer isDelete;
    
    /**
     * 创建时间
     */
    private LocalDateTime createdAt;
}

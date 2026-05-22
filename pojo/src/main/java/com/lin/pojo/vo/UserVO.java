package com.lin.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 用户信息VO（不包含密码）
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "用户信息视图对象")
public class UserVO {
    /**
     * 用户ID
     */
    @Schema(description = "用户ID", example = "1")
    private Integer id;
    
    /**
     * 用户名
     */
    @Schema(description = "用户名", example = "张三")
    private String username;
    
    /**
     * 邮箱
     */
    @Schema(description = "邮箱", example = "zhangsan@example.com")
    private String email;
    
    /**
     * 手机号
     */
    @Schema(description = "手机号", example = "13800138000")
    private String phone;
    
    /**
     * 角色: candidate(求职者), hr(人力资源), admin(管理员)
     */
    @Schema(description = "角色", example = "candidate", allowableValues = {"candidate", "hr", "admin"})
    private String role;
    
    /**
     * 头像URL
     */
    @Schema(description = "头像URL", example = "http://example.com/avatar.jpg")
    private String avatar;
    
    /**
     * 创建时间
     */
    @Schema(description = "创建时间", example = "2026-05-22T10:00:00")
    private LocalDateTime createdAt;
    
    /**
     * 更新时间
     */
    @Schema(description = "更新时间", example = "2026-05-22T10:00:00")
    private LocalDateTime updatedAt;
}

package com.lin.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 更新用户参数DTO
 */
@Data
@Schema(description = "更新用户参数")
public class UpdateUserDTO {
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
     * 角色
     */
    @Schema(description = "角色", example = "hr", allowableValues = {"candidate", "hr", "admin"})
    private String role;
    
    /**
     * 头像URL
     */
    @Schema(description = "头像URL", example = "http://example.com/avatar.jpg")
    private String avatar;
}

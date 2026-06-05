package com.lin.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 创建用户参数DTO
 */
@Data
@Schema(description = "创建用户参数")
public class CreateUserDTO {
    /**
     * 用户名
     */
    @Schema(description = "用户名", requiredMode = Schema.RequiredMode.REQUIRED, example = "zhangsan")
    private String username;
    
    /**
     * 密码
     */
    @Schema(description = "密码", requiredMode = Schema.RequiredMode.REQUIRED, example = "123456")
    private String password;
    
    /**
     * 邮箱
     */
    @Schema(description = "邮箱", requiredMode = Schema.RequiredMode.REQUIRED, example = "zhangsan@example.com")
    private String email;
    
    /**
     * 手机号
     */
    @Schema(description = "手机号", example = "13800138000")
    private String phone;
    
    /**
     * 角色: candidate(求职者), hr(人力资源), admin(管理员)
     */
    @Schema(description = "角色", requiredMode = Schema.RequiredMode.REQUIRED, example = "candidate", 
            allowableValues = {"candidate", "hr", "admin"})
    private String role;
    
    /**
     * 头像URL
     */
    @Schema(description = "头像URL", example = "http://example.com/avatar.jpg" )
    private String avatar;
}

package com.lin.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 登录响应VO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "登录响应数据")
public class LoginVO {
    
    @Schema(description = "JWT Token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String token;
    
    @Schema(description = "用户信息")
    private UserVO user;
    
    /**
     * 用户信息VO
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "用户信息")
    public static class UserVO {
        
        @Schema(description = "用户ID", example = "1")
        private Integer id;
        
        @Schema(description = "用户名", example = "admin")
        private String username;
        
        @Schema(description = "邮箱", example = "admin@example.com")
        private String email;
        
        @Schema(description = "手机号", example = "13800138000")
        private String phone;
        
        @Schema(description = "用户角色: candidate-求职者, hr-HR, admin-管理员", example = "admin")
        private String role;
        
        @Schema(description = "头像URL", example = "https://example.com/avatar.jpg")
        private String avatar;
        
        @Schema(description = "创建时间", example = "2024-01-01T12:00:00")
        private LocalDateTime createdAt;
        
        @Schema(description = "更新时间", example = "2024-01-01T12:00:00")
        private LocalDateTime updatedAt;
    }
}

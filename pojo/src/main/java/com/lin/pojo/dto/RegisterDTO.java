package com.lin.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * 注册请求DTO
 */
@Data
@Schema(description = "注册请求参数")
public class RegisterDTO {
    
    @NotBlank(message = "用户名不能为空")
    @Schema(description = "用户名", example = "zhangsan")
    private String username;
    
    @NotBlank(message = "密码不能为空")
    @Schema(description = "密码", example = "123456")
    private String password;
    
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    @Schema(description = "邮箱", example = "zhangsan@example.com")
    private String email;
    
    @Schema(description = "手机号（可选）", example = "13800138000")
    private String phone;
    
    @NotBlank(message = "角色不能为空")
    @Schema(description = "用户角色: candidate-求职者, hr-HR, admin-管理员", example = "candidate")
    private String role;
    
    @Schema(description = "邮箱验证码（可选）", example = "123456")
    private String code;
}

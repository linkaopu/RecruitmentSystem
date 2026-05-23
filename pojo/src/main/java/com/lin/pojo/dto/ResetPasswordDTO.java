package com.lin.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 重置密码请求DTO
 */
@Data
@Schema(description = "重置密码请求参数")
public class ResetPasswordDTO {
    
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    @Schema(description = "邮箱地址", example = "user@example.com")
    private String email;
    
    @NotBlank(message = "验证码不能为空")
    @Size(min = 6, max = 6, message = "验证码长度为6位")
    @Schema(description = "验证码", example = "123456")
    private String code;
    
    @NotBlank(message = "新密码不能为空")
    @Size(min = 6, message = "密码长度至少为6位")
    @Schema(description = "新密码", example = "newpassword123")
    private String password;
}

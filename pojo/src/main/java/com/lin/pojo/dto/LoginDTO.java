package com.lin.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * 登录请求DTO
 */
@Data
@Schema(description = "登录请求参数")
public class LoginDTO {
    
    @NotBlank(message = "用户名不能为空")
    @Schema(description = "用户名", example = "admin")
    private String username;
    
    @NotBlank(message = "密码不能为空")
    @Schema(description = "密码", example = "123456")
    private String password;
    
    @Schema(description = "登录类型: account-账号密码, phone-手机验证码", example = "account")
    private String type;
    
    @Schema(description = "手机验证码（type为phone时必填）", example = "123456")
    private String code;
}

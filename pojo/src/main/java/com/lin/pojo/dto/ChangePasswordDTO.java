package com.lin.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 修改密码请求DTO
 */
@Data
@Schema(description = "修改密码请求参数")
public class ChangePasswordDTO {
    
    @NotBlank(message = "原密码不能为空")
    @Schema(description = "原密码", example = "oldpassword123")
    private String oldPassword;
    
    @NotBlank(message = "新密码不能为空")
    @Size(min = 6, message = "密码长度至少为6位")
    @Schema(description = "新密码", example = "newpassword123")
    private String newPassword;
}

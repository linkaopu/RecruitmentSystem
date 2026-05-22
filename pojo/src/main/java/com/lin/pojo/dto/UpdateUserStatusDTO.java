package com.lin.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 更新用户状态参数DTO
 */
@Data
@Schema(description = "更新用户状态参数")
public class UpdateUserStatusDTO {
    /**
     * 用户状态: active(激活), inactive(禁用)
     */
    @Schema(description = "用户状态", requiredMode = Schema.RequiredMode.REQUIRED, 
            example = "active", allowableValues = {"active", "inactive"})
    private String status;
}

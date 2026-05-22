package com.lin.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 创建部门参数DTO
 */
@Data
@Schema(description = "创建部门参数")
public class CreateDepartmentDTO {
    /**
     * 部门名称
     */
    @Schema(description = "部门名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "技术部")
    private String name;
    
    /**
     * 部门代码
     */
    @Schema(description = "部门代码", requiredMode = Schema.RequiredMode.REQUIRED, example = "TECH")
    private String code;
    
    /**
     * 部门描述
     */
    @Schema(description = "部门描述", example = "负责技术研发")
    private String description;
}

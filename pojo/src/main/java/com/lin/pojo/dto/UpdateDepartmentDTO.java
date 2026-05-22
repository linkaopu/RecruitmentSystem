package com.lin.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 更新部门参数DTO
 */
@Data
@Schema(description = "更新部门参数")
public class UpdateDepartmentDTO {
    /**
     * 部门名称
     */
    @Schema(description = "部门名称", example = "技术研发部")
    private String name;
    
    /**
     * 部门代码
     */
    @Schema(description = "部门代码", example = "TECH")
    private String code;
    
    /**
     * 部门描述
     */
    @Schema(description = "部门描述", example = "负责产品研发和技术创新")
    private String description;
}

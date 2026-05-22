package com.lin.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 用户查询参数DTO
 */
@Data
@Schema(description = "用户查询参数")
public class UserQueryDTO {
    /**
     * 页码
     */
    @Schema(description = "页码", example = "1")
    private Integer pageNum = 1;
    
    /**
     * 每页数量
     */
    @Schema(description = "每页数量", example = "10")
    private Integer pageSize = 10;
    
    /**
     * 用户名（模糊搜索）
     */
    @Schema(description = "用户名（模糊搜索）", example = "张三")
    private String username;
    
    /**
     * 邮箱（模糊搜索）
     */
    @Schema(description = "邮箱（模糊搜索）", example = "zhangsan@example.com")
    private String email;
    
    /**
     * 手机号（模糊搜索）
     */
    @Schema(description = "手机号（模糊搜索）", example = "13800138000")
    private String phone;
    
    /**
     * 角色
     */
    @Schema(description = "角色", example = "candidate", allowableValues = {"candidate", "hr", "admin"})
    private String role;
}

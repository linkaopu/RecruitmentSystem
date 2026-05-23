package com.lin.server.controller;

import com.lin.common.result.Result;
import com.lin.common.result.PageResult;
import com.lin.pojo.dto.*;
import com.lin.pojo.entity.Department;
import com.lin.pojo.entity.SystemLog;
import com.lin.pojo.vo.DashboardStatsVO;
import com.lin.pojo.vo.UserVO;
import com.lin.server.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 管理员控制器
 */
@Slf4j
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Tag(name = "管理员模块", description = "管理员相关接口，包括用户管理、部门管理、系统日志等")
public class AdminController {
    
    private final UserService userService;
    private final DepartmentService departmentService;
    private final SystemLogService systemLogService;
    private final DashboardService dashboardService;
    
    // ==================== 用户管理 ====================
    
    /**
     * 分页查询用户列表
     */
    @GetMapping("/users")
    @Operation(summary = "分页查询用户列表", description = "支持按用户名、邮箱、手机号、角色等条件筛选")
    public Result<PageResult<UserVO>> getUsers(UserQueryDTO query) {
        log.info("查询用户列表，参数: {}", query);
        PageResult<UserVO> result = userService.getUsers(query);
        return Result.success(result);
    }
    
    /**
     * 创建用户
     */
    @PostMapping("/users")
    @Operation(summary = "创建用户", description = "创建新用户，需要传入用户名、密码、邮箱等信息")
    public Result<UserVO> createUser(@RequestBody CreateUserDTO createUserDTO) {
        log.info("创建用户，参数: {}", createUserDTO);
        UserVO user = userService.createUser(createUserDTO);
        return Result.success("用户创建成功", user);
    }
    
    /**
     * 更新用户信息
     */
    @PutMapping("/users/{id}")
    @Operation(summary = "更新用户信息", description = "根据ID更新用户的基本信息")
    public Result<UserVO> updateUser(@Parameter(description = "用户ID") @PathVariable Integer id, 
                                     @RequestBody UpdateUserDTO updateUserDTO) {
        log.info("更新用户信息，ID: {}, 参数: {}", id, updateUserDTO);
        UserVO user = userService.updateUser(id, updateUserDTO);
        return Result.success("用户更新成功", user);
    }
    
    /**
     * 删除用户
     */
    @DeleteMapping("/users/{id}")
    @Operation(summary = "删除用户", description = "根据ID删除用户")
    public Result<Void> deleteUser(@Parameter(description = "用户ID") @PathVariable Integer id) {
        log.info("删除用户，ID: {}", id);
        userService.deleteUser(id);
        return Result.success("用户删除成功", null);
    }
    
    // ==================== 部门管理 ====================
    
    /**
     * 查询所有部门
     */
    @GetMapping("/departments")
    @Operation(summary = "查询所有部门", description = "获取系统中所有部门的列表")
    public Result<List<Department>> getDepartments() {
        log.info("查询所有部门");
        List<Department> departments = departmentService.getDepartments();
        return Result.success(departments);
    }
    
    /**
     * 创建部门
     */
    @PostMapping("/departments")
    @Operation(summary = "创建部门", description = "创建新的部门")
    public Result<Department> createDepartment(@RequestBody CreateDepartmentDTO createDepartmentDTO) {
        log.info("创建部门，参数: {}", createDepartmentDTO);
        Department department = departmentService.createDepartment(createDepartmentDTO);
        return Result.success("部门创建成功", department);
    }
    
    /**
     * 更新部门信息
     */
    @PutMapping("/departments/{id}")
    @Operation(summary = "更新部门信息", description = "根据ID更新部门的基本信息")
    public Result<Department> updateDepartment(@Parameter(description = "部门ID") @PathVariable Integer id,
                                               @RequestBody UpdateDepartmentDTO updateDepartmentDTO) {
        log.info("更新部门信息，ID: {}, 参数: {}", id, updateDepartmentDTO);
        Department department = departmentService.updateDepartment(id, updateDepartmentDTO);
        return Result.success("部门更新成功", department);
    }
    
    /**
     * 删除部门
     */
    @DeleteMapping("/departments/{id}")
    @Operation(summary = "删除部门", description = "根据ID删除部门")
    public Result<Void> deleteDepartment(@Parameter(description = "部门ID") @PathVariable Integer id) {
        log.info("删除部门，ID: {}", id);
        departmentService.deleteDepartment(id);
        return Result.success("部门删除成功", null);
    }
    
    // ==================== 系统日志 ====================
    
    /**
     * 分页查询系统日志
     */
    @GetMapping("/logs")
    @Operation(summary = "分页查询系统日志", description = "获取系统操作日志列表")
    public Result<PageResult<SystemLog>> getSystemLogs(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "每页数量") @RequestParam(defaultValue = "10") Integer pageSize) {
        log.info("查询系统日志，页码: {}, 每页数量: {}", pageNum, pageSize);
        PageResult<SystemLog> result = systemLogService.getSystemLogs(pageNum, pageSize);
        return Result.success(result);
    }
    
    // ==================== 仪表盘数据 ====================
    
    /**
     * 获取仪表盘统计数据
     */
    @GetMapping("/dashboard/stats")
    @Operation(summary = "获取仪表盘统计数据", description = "获取今日申请数、总职位数、待处理简历数等统计信息")
    public Result<DashboardStatsVO> getDashboardStats() {
        log.info("获取仪表盘统计数据");
        DashboardStatsVO stats = dashboardService.getDashboardStats();
        return Result.success(stats);
    }
    
    // ==================== 系统设置 ====================
    
    /**
     * 获取系统设置
     */
    @GetMapping("/settings")
    @Operation(summary = "获取系统设置", description = "获取系统配置信息")
    public Result<Object> getSystemSettings() {
        log.info("获取系统设置");
        // TODO: 实现系统设置查询逻辑
        return Result.success(null);
    }
    
    /**
     * 更新系统设置
     */
    @PutMapping("/settings")
    @Operation(summary = "更新系统设置", description = "更新系统配置信息")
    public Result<Void> updateSystemSettings(@RequestBody Object settings) {
        log.info("更新系统设置，参数: {}", settings);
        // TODO: 实现系统设置更新逻辑
        return Result.success("系统设置更新成功", null);
    }
}

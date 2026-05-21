package com.lin.server.controller;

import com.lin.common.result.Result;
import com.lin.common.result.PageResult;
import com.lin.pojo.dto.*;
import com.lin.pojo.entity.Department;
import com.lin.pojo.entity.SystemLog;
import com.lin.pojo.vo.DashboardStatsVO;
import com.lin.pojo.vo.UserVO;
import com.lin.server.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 管理员控制器
 */
@Slf4j
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
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
    public Result<PageResult<UserVO>> getUsers(UserQueryDTO query) {
        log.info("查询用户列表，参数: {}", query);
        PageResult<UserVO> result = userService.getUsers(query);
        return Result.success(result);
    }
    
    /**
     * 创建用户
     */
    @PostMapping("/users")
    public Result<UserVO> createUser(@RequestBody CreateUserDTO createUserDTO) {
        log.info("创建用户，参数: {}", createUserDTO);
        UserVO user = userService.createUser(createUserDTO);
        return Result.success("用户创建成功", user);
    }
    
    /**
     * 更新用户信息
     */
    @PutMapping("/users/{id}")
    public Result<UserVO> updateUser(@PathVariable Integer id, 
                                     @RequestBody UpdateUserDTO updateUserDTO) {
        log.info("更新用户信息，ID: {}, 参数: {}", id, updateUserDTO);
        UserVO user = userService.updateUser(id, updateUserDTO);
        return Result.success("用户更新成功", user);
    }
    
    /**
     * 删除用户
     */
    @DeleteMapping("/users/{id}")
    public Result<Void> deleteUser(@PathVariable Integer id) {
        log.info("删除用户，ID: {}", id);
        userService.deleteUser(id);
        return Result.success("用户删除成功", null);
    }
    
    /**
     * 更新用户状态
     */
    @PatchMapping("/users/{id}/status")
    public Result<Void> toggleUserStatus(@PathVariable Integer id,
                                         @RequestBody UpdateUserStatusDTO statusDTO) {
        log.info("更新用户状态，ID: {}, 状态: {}", id, statusDTO.getStatus());
        userService.toggleUserStatus(id, statusDTO.getStatus());
        return Result.success("用户状态更新成功", null);
    }
    
    // ==================== 部门管理 ====================
    
    /**
     * 查询所有部门
     */
    @GetMapping("/departments")
    public Result<List<Department>> getDepartments() {
        log.info("查询所有部门");
        List<Department> departments = departmentService.getDepartments();
        return Result.success(departments);
    }
    
    /**
     * 创建部门
     */
    @PostMapping("/departments")
    public Result<Department> createDepartment(@RequestBody CreateDepartmentDTO createDepartmentDTO) {
        log.info("创建部门，参数: {}", createDepartmentDTO);
        Department department = departmentService.createDepartment(createDepartmentDTO);
        return Result.success("部门创建成功", department);
    }
    
    /**
     * 更新部门信息
     */
    @PutMapping("/departments/{id}")
    public Result<Department> updateDepartment(@PathVariable Integer id,
                                               @RequestBody UpdateDepartmentDTO updateDepartmentDTO) {
        log.info("更新部门信息，ID: {}, 参数: {}", id, updateDepartmentDTO);
        Department department = departmentService.updateDepartment(id, updateDepartmentDTO);
        return Result.success("部门更新成功", department);
    }
    
    /**
     * 删除部门
     */
    @DeleteMapping("/departments/{id}")
    public Result<Void> deleteDepartment(@PathVariable Integer id) {
        log.info("删除部门，ID: {}", id);
        departmentService.deleteDepartment(id);
        return Result.success("部门删除成功", null);
    }
    
    // ==================== 系统日志 ====================
    
    /**
     * 分页查询系统日志
     */
    @GetMapping("/logs")
    public Result<PageResult<SystemLog>> getSystemLogs(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        log.info("查询系统日志，页码: {}, 每页数量: {}", pageNum, pageSize);
        PageResult<SystemLog> result = systemLogService.getSystemLogs(pageNum, pageSize);
        return Result.success(result);
    }
    
    // ==================== 仪表盘数据 ====================
    
    /**
     * 获取仪表盘统计数据
     */
    @GetMapping("/dashboard/stats")
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
    public Result<Object> getSystemSettings() {
        log.info("获取系统设置");
        // TODO: 实现系统设置查询逻辑
        return Result.success(null);
    }
    
    /**
     * 更新系统设置
     */
    @PutMapping("/settings")
    public Result<Void> updateSystemSettings(@RequestBody Object settings) {
        log.info("更新系统设置，参数: {}", settings);
        // TODO: 实现系统设置更新逻辑
        return Result.success("系统设置更新成功", null);
    }
}

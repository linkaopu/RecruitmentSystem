package com.lin.server.controller;

import com.lin.common.result.PageResult;
import com.lin.common.result.Result;
import com.lin.pojo.entity.Application;
import com.lin.server.service.ApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 申请控制器
 */
@Slf4j
@RestController
@RequestMapping("/applications")
@RequiredArgsConstructor
@Tag(name = "申请模块", description = "职位申请管理相关接口")
public class ApplicationController {

    private final ApplicationService applicationService;

    /**
     * 申请职位
     */
    @PostMapping("/apply")
    @Operation(summary = "申请职位", description = "使用简历申请职位")
    public Result<Void> applyJob(@RequestBody Map<String, Integer> params) {
        Integer jobId = params.get("jobId");
        Integer resumeId = params.get("resumeId");
        log.info("申请职位，jobId: {}, resumeId: {}", jobId, resumeId);
        applicationService.applyJob(jobId, resumeId);
        return Result.success("申请成功", null);
    }

    /**
     * 获取我的申请列表
     */
    @GetMapping("/me")
    @Operation(summary = "获取我的申请列表", description = "获取当前用户的申请列表")
    public Result<PageResult<Application>> getMyApplications(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer page,
            @Parameter(description = "每页数量") @RequestParam(defaultValue = "10") Integer pageSize) {
        log.info("获取我的申请列表，page: {}, pageSize: {}", page, pageSize);
        PageResult<Application> pageResult = applicationService.getMyApplications(page, pageSize);
        return Result.success(pageResult);
    }

    /**
     * 获取申请列表（管理员）
     */
    @GetMapping
    @Operation(summary = "获取申请列表", description = "分页获取申请列表（管理员）")
    public Result<PageResult<Application>> getApplications(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer page,
            @Parameter(description = "每页数量") @RequestParam(defaultValue = "10") Integer pageSize,
            @Parameter(description = "申请状态") @RequestParam(required = false) String status,
            @Parameter(description = "负责HR用户ID") @RequestParam(required = false) Integer hrId) {
        log.info("获取申请列表，page: {}, pageSize: {}, status: {}, hrId: {}", page, pageSize, status, hrId);
        PageResult<Application> pageResult = applicationService.getApplications(page, pageSize, status, hrId);
        return Result.success(pageResult);
    }

    /**
     * 更新申请状态
     */
    @PatchMapping("/{id}/status")
    @Operation(summary = "更新申请状态", description = "更新申请状态（管理员）")
    public Result<Void> updateApplicationStatus(
            @Parameter(description = "申请ID") @PathVariable Integer id,
            @RequestBody Map<String, String> params) {
        String status = params.get("status");
        String rejectReason = params.get("rejectReason");
        log.info("更新申请状态，id: {}, status: {}", id, status);
        applicationService.updateApplicationStatus(id, status, rejectReason);
        return Result.success("申请状态更新成功", null);
    }
}

package com.lin.server.controller;

import com.lin.common.result.PageResult;
import com.lin.common.result.Result;
import com.lin.pojo.dto.CreateInterviewDTO;
import com.lin.pojo.entity.Interview;
import com.lin.server.service.InterviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * 面试控制器
 */
@Slf4j
@RestController
@RequestMapping("/interviews")
@RequiredArgsConstructor
@Tag(name = "面试模块", description = "面试管理相关接口")
public class InterviewController {

    private final InterviewService interviewService;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm[:ss]");

    /**
     * 获取面试列表
     */
    @GetMapping
    @Operation(summary = "获取面试列表", description = "分页获取面试列表")
    public Result<PageResult<Interview>> getInterviews(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer page,
            @Parameter(description = "每页数量") @RequestParam(defaultValue = "10") Integer pageSize,
            @Parameter(description = "负责HR用户ID") @RequestParam(required = false) Integer hrId) {
        log.info("获取面试列表，page: {}, pageSize: {}, hrId: {}", page, pageSize, hrId);
        PageResult<Interview> pageResult = interviewService.getInterviews(page, pageSize, hrId);
        return Result.success(pageResult);
    }

    /**
     * 获取面试详情
     */
    @GetMapping("/{id}")
    @Operation(summary = "获取面试详情", description = "根据ID获取面试详情")
    public Result<Interview> getInterviewDetail(
            @Parameter(description = "面试ID") @PathVariable Integer id) {
        log.info("获取面试详情，ID: {}", id);
        Interview interview = interviewService.getInterviewDetail(id);
        return Result.success(interview);
    }

    /**
     * 创建面试
     */
    @PostMapping
    @Operation(summary = "创建面试", description = "创建新的面试安排")
    public Result<Interview> createInterview(@RequestBody CreateInterviewDTO dto) {
        log.info("创建面试，applicationId: {}, 面试官: {}", dto.getApplicationId(), dto.getInterviewerName());
        Interview interview = interviewService.createInterview(dto);
        return Result.success("面试创建成功", interview);
    }

    /**
     * 更新面试
     */
    @PutMapping("/{id}")
    @Operation(summary = "更新面试", description = "更新面试信息")
    public Result<Interview> updateInterview(
            @Parameter(description = "面试ID") @PathVariable Integer id,
            @RequestBody Interview interview) {
        log.info("更新面试，ID: {}", id);
        Interview updated = interviewService.updateInterview(id, interview);
        return Result.success("面试更新成功", updated);
    }

    /**
     * 更新面试结果
     */
    @PutMapping("/{id}/result")
    @Operation(summary = "更新面试结果", description = "更新面试结果和备注")
    public Result<Void> updateInterviewResult(
            @Parameter(description = "面试ID") @PathVariable Integer id,
            @RequestBody Map<String, String> params) {
        String result = params.get("result");
        String notes = params.get("notes");
        log.info("更新面试结果，ID: {}, 结果: {}", id, result);
        interviewService.updateInterviewResult(id, result, notes);
        return Result.success("面试结果更新成功", null);
    }

    /**
     * 删除面试
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除面试", description = "删除面试记录")
    public Result<Void> deleteInterview(
            @Parameter(description = "面试ID") @PathVariable Integer id) {
        log.info("删除面试，ID: {}", id);
        interviewService.deleteInterview(id);
        return Result.success("面试删除成功", null);
    }

    /**
     * 发送面试通知
     */
    @PostMapping("/{id}/notify")
    @Operation(summary = "发送面试通知", description = "向候选人发送面试通知邮件")
    public Result<Void> sendInterviewNotification(
            @Parameter(description = "面试ID") @PathVariable Integer id) {
        log.info("发送面试通知，ID: {}", id);
        interviewService.sendInterviewNotification(id);
        return Result.success("面试通知已发送", null);
    }

    /**
     * 获取面试日程
     */
    @GetMapping("/schedule")
    @Operation(summary = "获取面试日程", description = "根据日期范围获取面试日程")
    public Result<List<Interview>> getInterviewSchedule(
            @Parameter(description = "开始日期") @RequestParam String startDate,
            @Parameter(description = "结束日期") @RequestParam String endDate) {
        LocalDateTime start = LocalDateTime.parse(startDate, FORMATTER);
        LocalDateTime end = LocalDateTime.parse(endDate, FORMATTER);
        log.info("获取面试日程，开始日期: {}, 结束日期: {}", startDate, endDate);
        List<Interview> schedule = interviewService.getInterviewSchedule(start, end);
        return Result.success(schedule);
    }
}

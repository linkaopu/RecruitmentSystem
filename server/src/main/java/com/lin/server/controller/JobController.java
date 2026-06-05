package com.lin.server.controller;

import com.lin.common.result.PageResult;
import com.lin.common.result.Result;
import com.lin.pojo.dto.CreateJobDTO;
import com.lin.pojo.entity.Job;
import com.lin.server.service.JobService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 职位控制器
 */
@Slf4j
@RestController
@RequestMapping("/jobs")
@RequiredArgsConstructor
@Tag(name = "职位模块", description = "职位管理相关接口")
public class JobController {

    private final JobService jobService;

    /**
     * 获取职位列表
     */
    @GetMapping
    @Operation(summary = "获取职位列表", description = "分页获取职位列表，支持多条件筛选")
    public Result<PageResult<Job>> getJobList(
            @Parameter(description = "搜索关键词") @RequestParam(required = false) String keyword,
            @Parameter(description = "部门ID") @RequestParam(required = false) Integer departmentId,
            @Parameter(description = "地点") @RequestParam(required = false) String location,
            @Parameter(description = "最低薪资") @RequestParam(required = false) Integer salaryMin,
            @Parameter(description = "最高薪资") @RequestParam(required = false) Integer salaryMax,
            @Parameter(description = "学历要求") @RequestParam(required = false) String education,
            @Parameter(description = "工作经验要求") @RequestParam(required = false) String experience,
            @Parameter(description = "职位状态") @RequestParam(required = false) String status,
            @Parameter(description = "负责HR用户ID") @RequestParam(required = false) Integer hrId,
            @Parameter(description = "排序方式: latest/salary/hot") @RequestParam(defaultValue = "latest") String sortBy,
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer page,
            @Parameter(description = "每页数量") @RequestParam(defaultValue = "10") Integer pageSize) {
        log.info("获取职位列表，keyword: {}, departmentId: {}, location: {}, status: {}, hrId: {}", keyword, departmentId, location, status, hrId);
        PageResult<Job> pageResult = jobService.getJobList(keyword, departmentId, location,
                salaryMin, salaryMax, education, experience, status, hrId, sortBy, page, pageSize);
        return Result.success(pageResult);
    }

    /**
     * 获取职位详情
     */
    @GetMapping("/{id}")
    @Operation(summary = "获取职位详情", description = "根据ID获取职位详情，浏览量自动增加")
    public Result<Job> getJobDetail(@Parameter(description = "职位ID") @PathVariable Integer id) {
        log.info("获取职位详情，ID: {}", id);
        Job job = jobService.getJobDetail(id);
        return Result.success(job);
    }

    /**
     * 获取热门职位
     */
    @GetMapping("/hot")
    @Operation(summary = "获取热门职位", description = "获取浏览量最高的热门职位")
    public Result<List<Job>> getHotJobs() {
        log.info("获取热门职位");
        List<Job> jobs = jobService.getHotJobs();
        return Result.success(jobs);
    }

    /**
     * 获取最新职位
     */
    @GetMapping("/latest")
    @Operation(summary = "获取最新职位", description = "获取最新发布的职位")
    public Result<List<Job>> getLatestJobs() {
        log.info("获取最新职位");
        List<Job> jobs = jobService.getLatestJobs();
        return Result.success(jobs);
    }

    /**
     * 创建职位
     */
    @PostMapping
    @Operation(summary = "创建职位", description = "创建新的职位")
    public Result<Job> createJob(@RequestBody CreateJobDTO dto) {
        log.info("创建职位，标题: {}", dto.getTitle());
        Job created = jobService.createJob(dto);
        return Result.success("职位创建成功", created);
    }

    /**
     * 更新职位
     */
    @PutMapping("/{id}")
    @Operation(summary = "更新职位", description = "更新职位信息")
    public Result<Job> updateJob(
            @Parameter(description = "职位ID") @PathVariable Integer id,
            @RequestBody CreateJobDTO dto) {
        log.info("更新职位，ID: {}", id);
        Job updated = jobService.updateJob(id, dto);
        return Result.success("职位更新成功", updated);
    }

    /**
     * 删除职位
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除职位", description = "删除职位记录")
    public Result<Void> deleteJob(@Parameter(description = "职位ID") @PathVariable Integer id) {
        log.info("删除职位，ID: {}", id);
        jobService.deleteJob(id);
        return Result.success("职位删除成功", null);
    }

    /**
     * 切换职位状态
     */
    @PatchMapping("/{id}/status")
    @Operation(summary = "切换职位状态", description = "上架或下架职位")
    public Result<Void> toggleJobStatus(
            @Parameter(description = "职位ID") @PathVariable Integer id,
            @RequestBody Map<String, String> params) {
        String status = params.get("status");
        log.info("切换职位状态，ID: {}, 状态: {}", id, status);
        jobService.toggleJobStatus(id, status);
        return Result.success("职位状态更新成功", null);
    }

    /**
     * 获取职位统计数据
     */
    @GetMapping("/{id}/stats")
    @Operation(summary = "获取职位统计数据", description = "获取职位的浏览量和申请数")
    public Result<Map<String, Integer>> getJobStats(@Parameter(description = "职位ID") @PathVariable Integer id) {
        log.info("获取职位统计数据，ID: {}", id);
        Map<String, Integer> stats = jobService.getJobStats(id);
        return Result.success(stats);
    }
}

package com.lin.server.service.impl;

import com.lin.common.result.PageResult;
import com.lin.pojo.dto.CreateJobDTO;
import com.lin.pojo.entity.Job;
import com.lin.server.mapper.JobMapper;
import com.lin.server.service.JobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 职位服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobMapper jobMapper;

    @Override
    public PageResult<Job> getJobList(String keyword, Integer departmentId, String location,
                                      Integer salaryMin, Integer salaryMax, String education,
                                      String experience, String status, Integer hrId,
                                      String sortBy, Integer page, Integer pageSize) {
        // 计算偏移量
        int offset = (page - 1) * pageSize;

        // 查询职位列表
        List<Job> list = jobMapper.selectJobs(keyword, departmentId, location, salaryMin, salaryMax,
                education, experience, status, hrId, sortBy, offset, pageSize);

        // 查询总数
        Long total = jobMapper.countJobs(keyword, departmentId, location, salaryMin, salaryMax,
                education, experience, status, hrId);

        log.info("查询职位列表，page: {}, pageSize: {}, total: {}", page, pageSize, total);

        return PageResult.of(page, pageSize, total, list);
    }

    @Override
    public Job getJobDetail(Integer id) {
        // 增加浏览量
        jobMapper.incrementViewCount(id);

        // 查询职位详情
        Job job = jobMapper.selectById(id);
        if (job == null) {
            throw new IllegalArgumentException("职位不存在");
        }

        log.info("获取职位详情，ID: {}", id);

        return job;
    }

    @Override
    public List<Job> getHotJobs() {
        List<Job> jobs = jobMapper.selectHotJobs();
        log.info("获取热门职位，数量: {}", jobs.size());
        return jobs;
    }

    @Override
    public List<Job> getLatestJobs() {
        List<Job> jobs = jobMapper.selectLatestJobs();
        log.info("获取最新职位，数量: {}", jobs.size());
        return jobs;
    }

    @Override
    @Transactional
    public Job createJob(CreateJobDTO dto) {
        // 创建职位实体
        Job job = Job.builder()
                .hrId(dto.getHrId())
                .departmentId(dto.getDepartmentId())
                .title(dto.getTitle())
                .location(dto.getLocation())
                .salaryMin(dto.getSalaryMin())
                .salaryMax(dto.getSalaryMax())
                .salaryDisplay(dto.getSalaryDisplay())
                .isNegotiable(dto.getIsNegotiable() != null ? dto.getIsNegotiable() : 0)
                .education(dto.getEducation())
                .experience(dto.getExperience())
                .headcount(dto.getHeadcount())
                .description(dto.getDescription())
                .requirements(dto.getRequirements())
                .benefits(dto.getBenefits())
                .status(dto.getStatus() != null ? dto.getStatus() : "draft")
                .deadline(dto.getDeadline())
                .viewCount(0)
                .applyCount(0)
                .isHot(dto.getIsHot() != null ? dto.getIsHot() : 0)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        jobMapper.insert(job);

        log.info("创建职位成功，ID: {}, 标题: {}", job.getId(), job.getTitle());

        return job;
    }

    @Override
    @Transactional
    public Job updateJob(Integer id, CreateJobDTO dto) {
        // 检查职位是否存在
        Job existing = jobMapper.selectById(id);
        if (existing == null) {
            throw new IllegalArgumentException("职位不存在");
        }

        // 更新字段（DTO中null的字段保留原值）
        Job job = Job.builder()
                .id(id)
                .hrId(dto.getHrId() != null ? dto.getHrId() : existing.getHrId())
                .departmentId(dto.getDepartmentId() != null ? dto.getDepartmentId() : existing.getDepartmentId())
                .title(dto.getTitle() != null ? dto.getTitle() : existing.getTitle())
                .location(dto.getLocation() != null ? dto.getLocation() : existing.getLocation())
                .salaryMin(dto.getSalaryMin() != null ? dto.getSalaryMin() : existing.getSalaryMin())
                .salaryMax(dto.getSalaryMax() != null ? dto.getSalaryMax() : existing.getSalaryMax())
                .salaryDisplay(dto.getSalaryDisplay() != null ? dto.getSalaryDisplay() : existing.getSalaryDisplay())
                .isNegotiable(dto.getIsNegotiable() != null ? dto.getIsNegotiable() : existing.getIsNegotiable())
                .education(dto.getEducation() != null ? dto.getEducation() : existing.getEducation())
                .experience(dto.getExperience() != null ? dto.getExperience() : existing.getExperience())
                .headcount(dto.getHeadcount() != null ? dto.getHeadcount() : existing.getHeadcount())
                .description(dto.getDescription() != null ? dto.getDescription() : existing.getDescription())
                .requirements(dto.getRequirements() != null ? dto.getRequirements() : existing.getRequirements())
                .benefits(dto.getBenefits() != null ? dto.getBenefits() : existing.getBenefits())
                .status(dto.getStatus() != null ? dto.getStatus() : existing.getStatus())
                .deadline(dto.getDeadline() != null ? dto.getDeadline() : existing.getDeadline())
                .viewCount(existing.getViewCount())
                .applyCount(existing.getApplyCount())
                .isHot(dto.getIsHot() != null ? dto.getIsHot() : existing.getIsHot())
                .updatedAt(LocalDateTime.now())
                .build();

        jobMapper.updateById(job);

        log.info("更新职位成功，ID: {}", id);

        return jobMapper.selectById(id);
    }

    @Override
    @Transactional
    public void deleteJob(Integer id) {
        // 检查职位是否存在
        Job job = jobMapper.selectById(id);
        if (job == null) {
            throw new IllegalArgumentException("职位不存在");
        }

        jobMapper.deleteById(id);

        log.info("删除职位成功，ID: {}", id);
    }

    @Override
    @Transactional
    public void toggleJobStatus(Integer id, String status) {
        // 检查职位是否存在
        Job job = jobMapper.selectById(id);
        if (job == null) {
            throw new IllegalArgumentException("职位不存在");
        }

        // 验证状态值
        if (!"draft".equals(status) && !"pending".equals(status) && !"active".equals(status)
                && !"closed".equals(status) && !"rejected".equals(status)) {
            throw new IllegalArgumentException("无效的职位状态，有效值: draft, pending, active, closed, rejected");
        }

        jobMapper.updateStatus(id, status);

        log.info("更新职位状态成功，ID: {}, 状态: {}", id, status);
    }

    @Override
    public Map<String, Integer> getJobStats(Integer id) {
        // 检查职位是否存在
        Job job = jobMapper.selectById(id);
        if (job == null) {
            throw new IllegalArgumentException("职位不存在");
        }

        Map<String, Integer> stats = new HashMap<>();
        stats.put("viewCount", job.getViewCount());
        stats.put("applyCount", job.getApplyCount());

        log.info("获取职位统计数据，ID: {}, 浏览量: {}, 申请数: {}", id, job.getViewCount(), job.getApplyCount());

        return stats;
    }
}

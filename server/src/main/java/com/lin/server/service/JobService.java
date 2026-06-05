package com.lin.server.service;

import com.lin.common.result.PageResult;
import com.lin.pojo.dto.CreateJobDTO;
import com.lin.pojo.entity.Job;

import java.util.List;
import java.util.Map;

/**
 * 职位服务接口
 */
public interface JobService {

    /**
     * 分页获取职位列表
     */
    PageResult<Job> getJobList(String keyword, Integer departmentId, String location,
                               Integer salaryMin, Integer salaryMax, String education,
                               String experience, String status, Integer hrId,
                               String sortBy, Integer page, Integer pageSize);

    /**
     * 获取职位详情
     */
    Job getJobDetail(Integer id);

    /**
     * 获取热门职位
     */
    List<Job> getHotJobs();

    /**
     * 获取最新职位
     */
    List<Job> getLatestJobs();

    /**
     * 创建职位
     */
    Job createJob(CreateJobDTO dto);

    /**
     * 更新职位
     */
    Job updateJob(Integer id, CreateJobDTO dto);

    /**
     * 删除职位
     */
    void deleteJob(Integer id);

    /**
     * 切换职位状态
     */
    void toggleJobStatus(Integer id, String status);

    /**
     * 获取职位统计数据
     */
    Map<String, Integer> getJobStats(Integer id);
}

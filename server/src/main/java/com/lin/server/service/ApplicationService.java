package com.lin.server.service;

import com.lin.common.result.PageResult;
import com.lin.pojo.entity.Application;

/**
 * 申请服务接口
 */
public interface ApplicationService {

    /**
     * 申请职位
     */
    void applyJob(Integer jobId, Integer resumeId);

    /**
     * 获取我的申请列表
     */
    PageResult<Application> getMyApplications(Integer page, Integer pageSize);

    /**
     * 分页获取申请列表（管理员）
     */
    PageResult<Application> getApplications(Integer page, Integer pageSize, String status, Integer hrId);

    /**
     * 更新申请状态
     */
    void updateApplicationStatus(Integer id, String status, String rejectReason);
}

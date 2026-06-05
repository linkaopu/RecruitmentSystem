package com.lin.server.service;

import com.lin.common.result.PageResult;
import com.lin.pojo.dto.CreateInterviewDTO;
import com.lin.pojo.entity.Interview;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 面试服务接口
 */
public interface InterviewService {

    /**
     * 分页获取面试列表
     */
    PageResult<Interview> getInterviews(Integer page, Integer pageSize, Integer hrId);

    /**
     * 获取面试详情
     */
    Interview getInterviewDetail(Integer id);

    /**
     * 创建面试
     */
    Interview createInterview(CreateInterviewDTO dto);

    /**
     * 更新面试
     */
    Interview updateInterview(Integer id, Interview interview);

    /**
     * 更新面试结果
     */
    void updateInterviewResult(Integer id, String result, String notes);

    /**
     * 删除面试
     */
    void deleteInterview(Integer id);

    /**
     * 发送面试通知
     */
    void sendInterviewNotification(Integer id);

    /**
     * 获取面试日程
     */
    List<Interview> getInterviewSchedule(LocalDateTime startDate, LocalDateTime endDate);
}

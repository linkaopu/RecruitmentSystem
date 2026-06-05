package com.lin.server.service.impl;

import com.lin.common.result.PageResult;
import com.lin.pojo.dto.CreateInterviewDTO;
import com.lin.pojo.entity.Interview;
import com.lin.pojo.entity.User;
import com.lin.server.mapper.InterviewMapper;
import com.lin.server.mapper.UserMapper;
import com.lin.server.service.EmailService;
import com.lin.server.service.InterviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 面试服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class InterviewServiceImpl implements InterviewService {

    private final InterviewMapper interviewMapper;
    private final EmailService emailService;
    private final UserMapper userMapper;

    @Override
    public PageResult<Interview> getInterviews(Integer page, Integer pageSize, Integer hrId) {
        // 计算偏移量
        int offset = (page - 1) * pageSize;

        // 查询面试列表
        List<Interview> list = interviewMapper.selectInterviews(offset, pageSize, hrId);

        // 查询总数
        Long total = interviewMapper.countInterviews(hrId);

        log.info("查询面试列表，page: {}, pageSize: {}, hrId: {}, total: {}", page, pageSize, hrId, total);

        return PageResult.of(page, pageSize, total, list);
    }

    @Override
    public Interview getInterviewDetail(Integer id) {
        Interview interview = interviewMapper.selectById(id);
        if (interview == null) {
            throw new IllegalArgumentException("面试记录不存在");
        }
        return interview;
    }

    @Override
    @Transactional
    public Interview createInterview(CreateInterviewDTO dto) {

        // 解析日期时间
        LocalDateTime startTime = LocalDateTime.parse(dto.getStartTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm[:ss]"));
        LocalDateTime endTime = LocalDateTime.parse(dto.getEndTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm[:ss]"));

        // 创建面试记录
        Interview interview = Interview.builder()
                .applicationId(dto.getApplicationId())
                .jobId(dto.getJobId())
                .userId(dto.getUserId())
                .resumeId(dto.getResumeId())
                .jobTitle(dto.getJobTitle())
                .userName(dto.getUserName())
                .interviewerId(dto.getInterviewerId())
                .interviewerName(dto.getInterviewerName())
                .startTime(startTime)
                .endTime(endTime)
                .location(dto.getLocation())
                .method(dto.getMethod())
                .notes(dto.getNotes())
                .result("pending")
                .notified(0)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        interviewMapper.insert(interview);

        log.info("创建面试成功，ID: {}, 求职者: {}, 面试官: {}",
                interview.getId(), dto.getUserName(), dto.getInterviewerName());

        return interview;
    }

    @Override
    @Transactional
    public Interview updateInterview(Integer id, Interview interviewData) {
        // 检查面试是否存在
        Interview existing = interviewMapper.selectById(id);
        if (existing == null) {
            throw new IllegalArgumentException("面试记录不存在");
        }

        // 更新字段
        Interview interview = Interview.builder()
                .id(id)
                .applicationId(interviewData.getApplicationId() != null ? interviewData.getApplicationId()
                        : existing.getApplicationId())
                .jobId(interviewData.getJobId() != null ? interviewData.getJobId() : existing.getJobId())
                .userId(interviewData.getUserId() != null ? interviewData.getUserId() : existing.getUserId())
                .resumeId(interviewData.getResumeId() != null ? interviewData.getResumeId() : existing.getResumeId())
                .jobTitle(interviewData.getJobTitle() != null ? interviewData.getJobTitle() : existing.getJobTitle())
                .userName(interviewData.getUserName() != null ? interviewData.getUserName() : existing.getUserName())
                .interviewerId(interviewData.getInterviewerId() != null ? interviewData.getInterviewerId()
                        : existing.getInterviewerId())
                .interviewerName(interviewData.getInterviewerName() != null ? interviewData.getInterviewerName()
                        : existing.getInterviewerName())
                .startTime(interviewData.getStartTime() != null ? interviewData.getStartTime()
                        : existing.getStartTime())
                .endTime(interviewData.getEndTime() != null ? interviewData.getEndTime()
                        : existing.getEndTime())
                .location(interviewData.getLocation() != null ? interviewData.getLocation() : existing.getLocation())
                .method(interviewData.getMethod() != null ? interviewData.getMethod() : existing.getMethod())
                .result(interviewData.getResult() != null ? interviewData.getResult() : existing.getResult())
                .notes(interviewData.getNotes() != null ? interviewData.getNotes() : existing.getNotes())
                .notified(interviewData.getNotified() != null ? interviewData.getNotified() : existing.getNotified())
                .updatedAt(LocalDateTime.now())
                .build();

        interviewMapper.updateById(interview);

        log.info("更新面试成功，ID: {}", id);

        return interviewMapper.selectById(id);
    }

    @Override
    @Transactional
    public void updateInterviewResult(Integer id, String result, String notes) {
        // 检查面试是否存在
        Interview interview = interviewMapper.selectById(id);
        if (interview == null) {
            throw new IllegalArgumentException("面试记录不存在");
        }

        interviewMapper.updateResult(id, result, notes);

        log.info("更新面试结果成功，ID: {}, 结果: {}", id, result);
    }

    @Override
    @Transactional
    public void deleteInterview(Integer id) {
        // 检查面试是否存在
        Interview interview = interviewMapper.selectById(id);
        if (interview == null) {
            throw new IllegalArgumentException("面试记录不存在");
        }

        interviewMapper.deleteById(id);

        log.info("删除面试成功，ID: {}", id);
    }

    @Override
    @Transactional
    public void sendInterviewNotification(Integer id) {
        // 检查面试是否存在
        Interview interview = interviewMapper.selectById(id);
        if (interview == null) {
            throw new IllegalArgumentException("面试记录不存在");
        }

        // 获取求职者邮箱
        User user = userMapper.selectById(interview.getUserId());
        if (user == null) {
            throw new IllegalArgumentException("求职者不存在");
        }
        String email = user.getEmail();

        // 构建邮件内容
        String subject = "面试通知 - " + interview.getJobTitle();
        String methodText = "online".equals(interview.getMethod()) ? "线上" : "线下";
        String content = String.format(
                "尊敬的%s：\n\n" +
                "您已获得%s职位的面试机会。\n\n" +
                "面试时间：%s 至 %s\n" +
                "面试地点：%s\n" +
                "面试方式：%s\n" +
                "面试官：%s\n\n" +
                "%s\n\n" +
                "请准时参加面试。",
                interview.getUserName(),
                interview.getJobTitle(),
                interview.getStartTime(),
                interview.getEndTime(),
                interview.getLocation() != null ? interview.getLocation() : "待定",
                methodText,
                interview.getInterviewerName(),
                interview.getNotes() != null ? "备注：" + interview.getNotes() : "");

        // 发送邮件
        emailService.sendSimpleEmail(email, subject, content);

        // 更新通知状态
        interviewMapper.updateNotified(id, 1);

        log.info("发送面试通知成功，面试ID: {}, 邮箱: {}", id, email);
    }

    @Override
    public List<Interview> getInterviewSchedule(LocalDateTime startDate, LocalDateTime endDate) {
        List<Interview> schedule = interviewMapper.selectByDateRange(startDate, endDate);

        log.info("查询面试日程，开始日期: {}, 结束日期: {}, 数量: {}", startDate, endDate, schedule.size());

        return schedule;
    }
}

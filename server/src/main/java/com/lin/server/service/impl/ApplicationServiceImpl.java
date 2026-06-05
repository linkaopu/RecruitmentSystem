package com.lin.server.service.impl;

import com.lin.common.result.PageResult;
import com.lin.common.util.BaseContextUtil;
import com.lin.pojo.entity.Application;
import com.lin.pojo.entity.Job;
import com.lin.pojo.entity.Resume;
import com.lin.pojo.entity.User;
import com.lin.server.mapper.ApplicationMapper;
import com.lin.server.mapper.JobMapper;
import com.lin.server.mapper.ResumeMapper;
import com.lin.server.mapper.UserMapper;
import com.lin.server.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 申请服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationMapper applicationMapper;
    private final JobMapper jobMapper;
    private final ResumeMapper resumeMapper;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public void applyJob(Integer jobId, Integer resumeId) {
        Integer userId = BaseContextUtil.getUserId();
        
        // 检查是否已申请过该职位
        Application existing = applicationMapper.selectByUserIdAndJobId(userId, jobId);
        if (existing != null) {
            throw new IllegalArgumentException("您已申请过该职位");
        }

        // 检查职位是否存在
        Job job = jobMapper.selectById(jobId);
        if (job == null) {
            throw new IllegalArgumentException("职位不存在");
        }

        // 检查简历是否存在
        Resume resume = resumeMapper.selectById(resumeId);
        if (resume == null) {
            throw new IllegalArgumentException("简历不存在");
        }

        // 验证是否是当前用户的简历
        if (!resume.getUserId().equals(userId)) {
            throw new IllegalArgumentException("无权使用此简历");
        }

        // 获取用户信息
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new IllegalArgumentException("用户不存在");
        }

        Application application = Application.builder()
                .jobId(jobId)
                .resumeId(resumeId)
                .userId(userId)
                .userName(user.getUsername())
                .jobTitle(job.getTitle())
                .status("pending")
                .appliedAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        applicationMapper.insert(application);

        // 增加职位申请数
        jobMapper.incrementApplyCount(jobId);

        log.info("申请职位成功，userId: {}, jobId: {}, resumeId: {}", userId, jobId, resumeId);
    }

    @Override
    public PageResult<Application> getMyApplications(Integer page, Integer pageSize) {
        Integer userId = BaseContextUtil.getUserId();
        int offset = (page - 1) * pageSize;
        List<Application> list = applicationMapper.selectByUserId(userId, offset, pageSize);
        Long total = applicationMapper.countByUserId(userId);
        log.info("查询我的申请列表，userId: {}, page: {}, pageSize: {}, total: {}", userId, page, pageSize, total);
        return PageResult.of(page, pageSize, total, list);
    }

    @Override
    public PageResult<Application> getApplications(Integer page, Integer pageSize, String status, Integer hrId) {
        int offset = (page - 1) * pageSize;
        List<Application> list = applicationMapper.selectAll(offset, pageSize, status, hrId);
        Long total = applicationMapper.countAll(status, hrId);
        log.info("查询申请列表，page: {}, pageSize: {}, status: {}, hrId: {}, total: {}", page, pageSize, status, hrId, total);
        return PageResult.of(page, pageSize, total, list);
    }

    @Override
    @Transactional
    public void updateApplicationStatus(Integer id, String status, String rejectReason) {
        Application existing = applicationMapper.selectById(id);
        if (existing == null) {
            throw new IllegalArgumentException("申请记录不存在");
        }

        // 验证状态值
        if (!"pending".equals(status) && !"screened".equals(status) &&
            !"interview".equals(status) && !"hired".equals(status) &&
            !"rejected".equals(status)) {
            throw new IllegalArgumentException("无效的申请状态");
        }

        applicationMapper.updateStatus(id, status, rejectReason);

        log.info("更新申请状态成功，id: {}, status: {}", id, status);
    }
}

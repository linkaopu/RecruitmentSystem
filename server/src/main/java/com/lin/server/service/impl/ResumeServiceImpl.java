package com.lin.server.service.impl;

import com.lin.common.result.PageResult;
import com.lin.common.util.BaseContextUtil;
import com.lin.pojo.dto.CreateResumeDTO;
import com.lin.pojo.entity.EducationHistory;
import com.lin.pojo.entity.ProjectExperience;
import com.lin.pojo.entity.Resume;
import com.lin.pojo.entity.WorkExperience;
import com.lin.server.mapper.EducationHistoryMapper;
import com.lin.server.mapper.ProjectExperienceMapper;
import com.lin.server.mapper.ResumeMapper;
import com.lin.server.mapper.WorkExperienceMapper;
import com.lin.server.service.ResumeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * 简历服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {

    private final ResumeMapper resumeMapper;
    private final WorkExperienceMapper workExperienceMapper;
    private final EducationHistoryMapper educationHistoryMapper;
    private final ProjectExperienceMapper projectExperienceMapper;

    @Override
    public Resume getMyResume() {
        Integer userId = BaseContextUtil.getUserId();
        Resume resume = resumeMapper.selectByUserId(userId);
        if (resume != null) {
            enrichResumeWithSubEntities(resume);
        }
        log.info("获取当前用户简历，userId: {}, found: {}", userId, resume != null);
        return resume;
    }

    @Override
    public PageResult<Resume> getResumes(Integer page, Integer pageSize) {
        int offset = (page - 1) * pageSize;
        List<Resume> list = resumeMapper.selectAll(offset, pageSize);
        Long total = resumeMapper.countAll();
        log.info("查询简历列表，page: {}, pageSize: {}, total: {}", page, pageSize, total);
        return PageResult.of(page, pageSize, total, list);
    }

    @Override
    @Transactional
    public Resume createResume(CreateResumeDTO dto) {
        Integer userId = BaseContextUtil.getUserId();

        // 检查用户是否已有简历
        Resume existing = resumeMapper.selectByUserId(userId);
        if (existing != null) {
            throw new IllegalArgumentException("您已创建过简历，请直接编辑");
        }

        Resume resume = Resume.builder()
                .userId(userId)
                .name(dto.getName())
                .phone(dto.getPhone())
                .email(dto.getEmail())
                .age(dto.getAge())
                .gender(dto.getGender())
                .education(dto.getEducation())
                .avatar(dto.getAvatar())
                .skills(dto.getSkills())
                .attachmentUrl(dto.getAttachmentUrl())
                .attachmentName(dto.getAttachmentName())
                .selfIntroduction(dto.getSelfIntroduction())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        resumeMapper.insert(resume);
        Integer resumeId = resume.getId();

        // 保存子表数据
        saveWorkExperiences(resumeId, dto.getWorkExperience());
        saveEducationHistories(resumeId, dto.getEducationHistory());
        saveProjectExperiences(resumeId, dto.getProjects());

        // 加载完整简历信息
        Resume fullResume = resumeMapper.selectById(resumeId);
        enrichResumeWithSubEntities(fullResume);

        log.info("创建简历成功，userId: {}, resumeId: {}", userId, resumeId);

        return fullResume;
    }

    @Override
    @Transactional
    public Resume updateResume(Integer id, CreateResumeDTO dto) {
        Integer userId = BaseContextUtil.getUserId();

        Resume existing = resumeMapper.selectById(id);
        if (existing == null) {
            throw new IllegalArgumentException("简历不存在");
        }

        // 验证是否是当前用户的简历
        if (!existing.getUserId().equals(userId)) {
            throw new IllegalArgumentException("无权编辑此简历");
        }

        Resume resume = Resume.builder()
                .id(id)
                .userId(userId)
                .name(dto.getName() != null ? dto.getName() : existing.getName())
                .phone(dto.getPhone() != null ? dto.getPhone() : existing.getPhone())
                .email(dto.getEmail() != null ? dto.getEmail() : existing.getEmail())
                .age(dto.getAge() != null ? dto.getAge() : existing.getAge())
                .gender(dto.getGender() != null ? dto.getGender() : existing.getGender())
                .education(dto.getEducation() != null ? dto.getEducation() : existing.getEducation())
                .avatar(dto.getAvatar() != null ? dto.getAvatar() : existing.getAvatar())
                .skills(dto.getSkills() != null ? dto.getSkills() : existing.getSkills())
                .attachmentUrl(dto.getAttachmentUrl() != null ? dto.getAttachmentUrl() : existing.getAttachmentUrl())
                .attachmentName(dto.getAttachmentName() != null ? dto.getAttachmentName() : existing.getAttachmentName())
                .selfIntroduction(dto.getSelfIntroduction() != null ? dto.getSelfIntroduction() : existing.getSelfIntroduction())
                .updatedAt(LocalDateTime.now())
                .build();

        resumeMapper.updateById(resume);

        // 更新子表数据：先删后插
        if (dto.getWorkExperience() != null) {
            workExperienceMapper.deleteByResumeId(id);
            saveWorkExperiences(id, dto.getWorkExperience());
        }
        if (dto.getEducationHistory() != null) {
            educationHistoryMapper.deleteByResumeId(id);
            saveEducationHistories(id, dto.getEducationHistory());
        }
        if (dto.getProjects() != null) {
            projectExperienceMapper.deleteByResumeId(id);
            saveProjectExperiences(id, dto.getProjects());
        }

        // 加载完整简历信息
        Resume fullResume = resumeMapper.selectById(id);
        enrichResumeWithSubEntities(fullResume);

        log.info("更新简历成功，id: {}", id);

        return fullResume;
    }

    @Override
    @Transactional
    public void deleteResume(Integer id) {
        Integer userId = BaseContextUtil.getUserId();

        Resume existing = resumeMapper.selectById(id);
        if (existing == null) {
            throw new IllegalArgumentException("简历不存在");
        }

        // 验证是否是当前用户的简历
        if (!existing.getUserId().equals(userId)) {
            throw new IllegalArgumentException("无权删除此简历");
        }

        // 级联删除子表数据
        workExperienceMapper.deleteByResumeId(id);
        educationHistoryMapper.deleteByResumeId(id);
        projectExperienceMapper.deleteByResumeId(id);

        resumeMapper.deleteById(id);

        log.info("删除简历成功，id: {}", id);
    }

    @Override
    public Resume getResumeDetail(Integer id) {
        Resume resume = resumeMapper.selectById(id);
        if (resume == null) {
            throw new IllegalArgumentException("简历不存在");
        }
        enrichResumeWithSubEntities(resume);
        log.info("获取简历详情，id: {}", id);
        return resume;
    }

    // ==================== 私有辅助方法 ====================

    /**
     * 加载简历的子表数据（工作经历、教育经历、项目经历）
     */
    private void enrichResumeWithSubEntities(Resume resume) {
        if (resume == null || resume.getId() == null) return;
        resume.setWorkExperience(workExperienceMapper.selectByResumeId(resume.getId()));
        resume.setEducationHistory(educationHistoryMapper.selectByResumeId(resume.getId()));
        resume.setProjects(projectExperienceMapper.selectByResumeId(resume.getId()));
    }

    /**
     * 保存工作经历
     */
    private void saveWorkExperiences(Integer resumeId, List<CreateResumeDTO.WorkExperienceItem> items) {
        if (items == null || items.isEmpty()) return;
        List<WorkExperience> list = new ArrayList<>();
        for (CreateResumeDTO.WorkExperienceItem item : items) {
            WorkExperience we = WorkExperience.builder()
                    .resumeId(resumeId)
                    .company(item.getCompany())
                    .position(item.getPosition())
                    .startDate(parseDate(item.getStartDate()))
                    .endDate(parseDate(item.getEndDate()))
                    .isCurrent(item.getIsCurrent() != null ? item.getIsCurrent() : 0)
                    .description(item.getDescription())
                    .build();
            list.add(we);
        }
        workExperienceMapper.insertBatch(list);
    }

    /**
     * 保存教育经历
     */
    private void saveEducationHistories(Integer resumeId, List<CreateResumeDTO.EducationItem> items) {
        if (items == null || items.isEmpty()) return;
        List<EducationHistory> list = new ArrayList<>();
        for (CreateResumeDTO.EducationItem item : items) {
            EducationHistory eh = EducationHistory.builder()
                    .resumeId(resumeId)
                    .school(item.getSchool())
                    .major(item.getMajor())
                    .degree(item.getDegree())
                    .startDate(parseDate(item.getStartDate()))
                    .endDate(parseDate(item.getEndDate()))
                    .build();
            list.add(eh);
        }
        educationHistoryMapper.insertBatch(list);
    }

    /**
     * 保存项目经历
     */
    private void saveProjectExperiences(Integer resumeId, List<CreateResumeDTO.ProjectItem> items) {
        if (items == null || items.isEmpty()) return;
        List<ProjectExperience> list = new ArrayList<>();
        for (CreateResumeDTO.ProjectItem item : items) {
            ProjectExperience pe = ProjectExperience.builder()
                    .resumeId(resumeId)
                    .name(item.getName())
                    .role(item.getRole())
                    .startDate(parseDate(item.getStartDate()))
                    .endDate(parseDate(item.getEndDate()))
                    .description(item.getDescription())
                    .technologies(item.getTechnologies())
                    .build();
            list.add(pe);
        }
        projectExperienceMapper.insertBatch(list);
    }

    private static final DateTimeFormatter FORMATTER_FULL = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter FORMATTER_MONTH = DateTimeFormatter.ofPattern("yyyy-MM");

    /**
     * 安全解析日期字符串，支持 yyyy-MM-dd 和 yyyy-MM 两种格式
     */
    private LocalDate parseDate(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) return null;
        try {
            // 先尝试完整日期格式 yyyy-MM-dd
            return LocalDate.parse(dateStr, FORMATTER_FULL);
        } catch (Exception e1) {
            try {
                // 再尝试年月格式 yyyy-MM，缺省为当月1号
                return LocalDate.parse(dateStr + "-01", FORMATTER_FULL);
            } catch (Exception e2) {
                log.warn("日期解析失败: {}", dateStr);
                return null;
            }
        }
    }
}

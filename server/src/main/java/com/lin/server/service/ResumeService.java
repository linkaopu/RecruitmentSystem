package com.lin.server.service;

import com.lin.common.result.PageResult;
import com.lin.pojo.dto.CreateResumeDTO;
import com.lin.pojo.entity.Resume;

/**
 * 简历服务接口
 */
public interface ResumeService {

    /**
     * 获取当前用户的简历
     */
    Resume getMyResume();

    /**
     * 分页获取简历列表（管理员）
     */
    PageResult<Resume> getResumes(Integer page, Integer pageSize);

    /**
     * 创建简历
     */
    Resume createResume(CreateResumeDTO dto);

    /**
     * 更新简历
     */
    Resume updateResume(Integer id, CreateResumeDTO dto);

    /**
     * 删除简历
     */
    void deleteResume(Integer id);

    /**
     * 获取简历详情
     */
    Resume getResumeDetail(Integer id);
}

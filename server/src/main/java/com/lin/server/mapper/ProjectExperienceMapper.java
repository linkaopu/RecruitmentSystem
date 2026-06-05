package com.lin.server.mapper;

import com.lin.pojo.entity.ProjectExperience;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 项目经历Mapper
 */
@Mapper
public interface ProjectExperienceMapper {

    /**
     * 根据简历ID查询项目经历列表
     */
    List<ProjectExperience> selectByResumeId(@Param("resumeId") Integer resumeId);

    /**
     * 批量插入项目经历
     */
    int insertBatch(@Param("list") List<ProjectExperience> list);

    /**
     * 根据简历ID逻辑删除所有项目经历
     */
    int deleteByResumeId(@Param("resumeId") Integer resumeId);
}

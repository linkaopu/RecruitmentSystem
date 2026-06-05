package com.lin.server.mapper;

import com.lin.pojo.entity.WorkExperience;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 工作经历Mapper
 */
@Mapper
public interface WorkExperienceMapper {

    /**
     * 根据简历ID查询工作经历列表
     */
    List<WorkExperience> selectByResumeId(@Param("resumeId") Integer resumeId);

    /**
     * 批量插入工作经历
     */
    int insertBatch(@Param("list") List<WorkExperience> list);

    /**
     * 根据简历ID逻辑删除所有工作经历
     */
    int deleteByResumeId(@Param("resumeId") Integer resumeId);
}

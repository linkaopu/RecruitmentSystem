package com.lin.server.mapper;

import com.lin.pojo.entity.EducationHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 教育经历Mapper
 */
@Mapper
public interface EducationHistoryMapper {

    /**
     * 根据简历ID查询教育经历列表
     */
    List<EducationHistory> selectByResumeId(@Param("resumeId") Integer resumeId);

    /**
     * 批量插入教育经历
     */
    int insertBatch(@Param("list") List<EducationHistory> list);

    /**
     * 根据简历ID逻辑删除所有教育经历
     */
    int deleteByResumeId(@Param("resumeId") Integer resumeId);
}

package com.lin.server.mapper;

import com.lin.pojo.entity.Job;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 职位Mapper
 */
@Mapper
public interface JobMapper {

    /**
     * 分页查询职位列表
     */
    List<Job> selectJobs(@Param("keyword") String keyword,
                         @Param("departmentId") Integer departmentId,
                         @Param("location") String location,
                         @Param("salaryMin") Integer salaryMin,
                         @Param("salaryMax") Integer salaryMax,
                         @Param("education") String education,
                         @Param("experience") String experience,
                         @Param("status") String status,
                         @Param("hrId") Integer hrId,
                         @Param("sortBy") String sortBy,
                         @Param("offset") Integer offset,
                         @Param("pageSize") Integer pageSize);

    /**
     * 查询职位总数
     */
    Long countJobs(@Param("keyword") String keyword,
                   @Param("departmentId") Integer departmentId,
                   @Param("location") String location,
                   @Param("salaryMin") Integer salaryMin,
                   @Param("salaryMax") Integer salaryMax,
                   @Param("education") String education,
                   @Param("experience") String experience,
                   @Param("status") String status,
                   @Param("hrId") Integer hrId);

    /**
     * 根据ID查询职位详情
     */
    Job selectById(@Param("id") Integer id);

    /**
     * 获取热门职位
     */
    List<Job> selectHotJobs();

    /**
     * 获取最新职位
     */
    List<Job> selectLatestJobs();

    /**
     * 插入职位记录
     */
    int insert(Job job);

    /**
     * 更新职位记录
     */
    int updateById(Job job);

    /**
     * 更新职位状态
     */
    int updateStatus(@Param("id") Integer id, @Param("status") String status);

    /**
     * 逻辑删除职位记录
     */
    int deleteById(@Param("id") Integer id);

    /**
     * 增加浏览量
     */
    int incrementViewCount(@Param("id") Integer id);

    /**
     * 增加申请数
     */
    int incrementApplyCount(@Param("id") Integer id);

    /**
     * 获取职位统计数据
     */
    Job selectStatsById(@Param("id") Integer id);
}

package com.lin.server.mapper;

import com.lin.pojo.entity.Interview;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 面试Mapper
 */
@Mapper
public interface InterviewMapper {

    /**
     * 分页查询面试列表
     */
    List<Interview> selectInterviews(@Param("offset") Integer offset, @Param("pageSize") Integer pageSize,
                                    @Param("hrId") Integer hrId);

    /**
     * 查询面试总数
     */
    Long countInterviews(@Param("hrId") Integer hrId);

    /**
     * 根据ID查询面试详情
     */
    Interview selectById(@Param("id") Integer id);

    /**
     * 插入面试记录
     */
    int insert(Interview interview);

    /**
     * 更新面试记录
     */
    int updateById(Interview interview);

    /**
     * 更新面试结果
     */
    int updateResult(@Param("id") Integer id, @Param("result") String result, @Param("notes") String notes);

    /**
     * 逻辑删除面试记录
     */
    int deleteById(@Param("id") Integer id);

    /**
     * 更新通知状态
     */
    int updateNotified(@Param("id") Integer id, @Param("notified") Integer notified);

    /**
     * 根据日期范围查询面试日程
     */
    List<Interview> selectByDateRange(@Param("startDate") LocalDateTime startDate,
                                       @Param("endDate") LocalDateTime endDate);

    /**
     * 统计待定面试数（result = 'pending'）
     */
    Long countPending(@Param("hrId") Integer hrId);
}

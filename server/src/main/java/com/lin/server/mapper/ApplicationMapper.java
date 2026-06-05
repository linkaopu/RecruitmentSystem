package com.lin.server.mapper;

import com.lin.pojo.entity.Application;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 申请Mapper
 */
@Mapper
public interface ApplicationMapper {

    /**
     * 分页查询用户申请列表
     */
    List<Application> selectByUserId(@Param("userId") Integer userId,
                                      @Param("offset") Integer offset,
                                      @Param("pageSize") Integer pageSize);

    /**
     * 查询用户申请总数
     */
    Long countByUserId(@Param("userId") Integer userId);

    /**
     * 分页查询所有申请列表（管理员）
     */
    List<Application> selectAll(@Param("offset") Integer offset,
                                 @Param("pageSize") Integer pageSize,
                                 @Param("status") String status,
                                 @Param("hrId") Integer hrId);

    /**
     * 查询所有申请总数
     */
    Long countAll(@Param("status") String status,
                  @Param("hrId") Integer hrId);

    /**
     * 根据ID查询申请详情
     */
    Application selectById(@Param("id") Integer id);

    /**
     * 检查用户是否已申请过该职位
     */
    Application selectByUserIdAndJobId(@Param("userId") Integer userId,
                                        @Param("jobId") Integer jobId);

    /**
     * 插入申请记录
     */
    int insert(Application application);

    /**
     * 更新申请状态
     */
    int updateStatus(@Param("id") Integer id,
                     @Param("status") String status,
                     @Param("rejectReason") String rejectReason);

    /**
     * 逻辑删除申请
     */
    int deleteById(@Param("id") Integer id);

    /**
     * 统计今日申请数
     */
    Long countToday(@Param("hrId") Integer hrId);

    /**
     * 统计指定状态的申请数
     */
    Long countByStatus(@Param("status") String status, @Param("hrId") Integer hrId);

    /**
     * 按日期统计近N天申请趋势
     */
    List<java.util.Map<String, Object>> countByDateRange(@Param("days") Integer days,
                                                         @Param("hrId") Integer hrId);
}

package com.lin.server.mapper;

import com.lin.pojo.entity.SystemLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统日志Mapper接口
 */
@Mapper
public interface SystemLogMapper {
    
    /**
     * 查询系统日志（分页）
     */
    List<SystemLog> selectLogs(@Param("pageNum") Integer pageNum, 
                                @Param("pageSize") Integer pageSize);
    
    /**
     * 统计日志总数
     */
    Long countLogs();
    
    /**
     * 插入日志
     */
    int insert(SystemLog log);
}

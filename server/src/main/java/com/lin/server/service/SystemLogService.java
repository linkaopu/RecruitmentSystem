package com.lin.server.service;

import com.lin.common.result.PageResult;
import com.lin.pojo.entity.SystemLog;

/**
 * 系统日志服务接口
 */
public interface SystemLogService {
    
    /**
     * 分页查询系统日志
     */
    PageResult<SystemLog> getSystemLogs(Integer page, Integer pageSize);
}

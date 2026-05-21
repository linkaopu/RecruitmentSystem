package com.lin.server.service.impl;

import com.lin.common.result.PageResult;
import com.lin.pojo.entity.SystemLog;
import com.lin.server.mapper.SystemLogMapper;
import com.lin.server.service.SystemLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统日志服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SystemLogServiceImpl implements SystemLogService {
    
    private final SystemLogMapper systemLogMapper;
    
    @Override
    public PageResult<SystemLog> getSystemLogs(Integer pageNum, Integer pageSize) {
        // 查询日志列表
        List<SystemLog> logs = systemLogMapper.selectLogs(pageNum, pageSize);
        
        // 统计总数
        Long total = systemLogMapper.countLogs();
        
        return PageResult.of(pageNum, pageSize, total, logs);
    }
}

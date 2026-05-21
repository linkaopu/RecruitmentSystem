package com.lin.server.service;

import com.lin.pojo.vo.DashboardStatsVO;

/**
 * 仪表盘服务接口
 */
public interface DashboardService {
    
    /**
     * 获取仪表盘统计数据
     */
    DashboardStatsVO getDashboardStats();
}

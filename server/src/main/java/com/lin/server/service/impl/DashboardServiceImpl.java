package com.lin.server.service.impl;

import com.lin.pojo.vo.DashboardStatsVO;
import com.lin.server.mapper.ApplicationMapper;
import com.lin.server.mapper.InterviewMapper;
import com.lin.server.mapper.JobMapper;
import com.lin.server.service.DashboardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 仪表盘服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final ApplicationMapper applicationMapper;
    private final JobMapper jobMapper;
    private final InterviewMapper interviewMapper;

    @Override
    public DashboardStatsVO getDashboardStats(Integer hrId) {
        // 今日申请数
        Long todayApplications = applicationMapper.countToday(hrId);
        if (todayApplications == null) todayApplications = 0L;

        // 总职位数（已发布状态的职位）
        Long totalJobs = jobMapper.countJobs(null, null, null, null, null,
                null, null, "active", hrId);
        if (totalJobs == null) totalJobs = 0L;

        // 待处理简历数（待查看状态的申请数）
        Long pendingResumes = applicationMapper.countByStatus("pending", hrId);
        if (pendingResumes == null) pendingResumes = 0L;

        // 待安排面试数（结果待定的面试数）
        Long pendingInterviews = interviewMapper.countPending(hrId);
        if (pendingInterviews == null) pendingInterviews = 0L;

        // 近7天申请趋势
        List<Map<String, Object>> rawTrend = applicationMapper.countByDateRange(6, hrId);
        List<DashboardStatsVO.ApplicationTrendVO> applicationTrend = buildTrend(rawTrend, 6);

        log.info("仪表盘数据: 今日申请={}, 总职位={}, 待处理={}, 待面试={}, hrId={}",
                todayApplications, totalJobs, pendingResumes, pendingInterviews, hrId);

        return DashboardStatsVO.builder()
                .todayApplications(todayApplications.intValue())
                .totalJobs(totalJobs.intValue())
                .pendingResumes(pendingResumes.intValue())
                .pendingInterviews(pendingInterviews.intValue())
                .applicationTrend(applicationTrend)
                .build();
    }

    /**
     * 构建近N天申请趋势，缺失的日期补0
     */
    private List<DashboardStatsVO.ApplicationTrendVO> buildTrend(List<Map<String, Object>> rawData, int days) {
        List<DashboardStatsVO.ApplicationTrendVO> trendList = new ArrayList<>();
        for (int i = days; i >= 0; i--) {
            String date = LocalDate.now().minusDays(i).toString();
            int count = 0;
            // 查找该日期是否有数据
            for (Map<String, Object> row : rawData) {
                if (date.equals(row.get("date").toString())) {
                    count = ((Number) row.get("count")).intValue();
                    break;
                }
            }
            trendList.add(DashboardStatsVO.ApplicationTrendVO.builder()
                    .date(date)
                    .count(count)
                    .build());
        }
        return trendList;
    }
}

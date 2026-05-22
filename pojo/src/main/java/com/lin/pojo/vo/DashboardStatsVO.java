package com.lin.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 仪表盘统计数据VO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "仪表盘统计数据")
public class DashboardStatsVO {
    /**
     * 今日申请数
     */
    @Schema(description = "今日申请数", example = "15")
    private Integer todayApplications;
    
    /**
     * 总职位数
     */
    @Schema(description = "总职位数", example = "50")
    private Integer totalJobs;
    
    /**
     * 待处理简历数
     */
    @Schema(description = "待处理简历数", example = "30")
    private Integer pendingResumes;
    
    /**
     * 待安排面试数
     */
    @Schema(description = "待安排面试数", example = "20")
    private Integer pendingInterviews;
    
    /**
     * 近7天申请趋势
     */
    @Schema(description = "近7天申请趋势")
    private java.util.List<ApplicationTrendVO> applicationTrend;
    
    /**
     * 申请趋势数据项
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "申请趋势数据项")
    public static class ApplicationTrendVO {
        @Schema(description = "日期", example = "2026-05-16")
        private String date;
        
        @Schema(description = "申请数量", example = "25")
        private Integer count;
    }
}

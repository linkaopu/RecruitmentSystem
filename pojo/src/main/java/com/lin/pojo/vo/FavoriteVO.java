package com.lin.pojo.vo;

import com.lin.pojo.entity.Job;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 收藏信息VO（包含职位详情）
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "收藏信息视图对象")
public class FavoriteVO {
    
    /**
     * 收藏ID
     */
    @Schema(description = "收藏ID")
    private Integer id;
    
    /**
     * 用户ID
     */
    @Schema(description = "用户ID")
    private Integer userId;
    
    /**
     * 职位ID
     */
    @Schema(description = "职位ID")
    private Integer jobId;
    
    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createdAt;
    
    /**
     * 职位详情
     */
    @Schema(description = "职位详情")
    private Job job;
}

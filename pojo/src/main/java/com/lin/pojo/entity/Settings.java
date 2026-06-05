package com.lin.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 网站基础配置实体类（单行记录，id固定为1）
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Settings {
    /**
     * 主键（固定为1，仅一条配置）
     */
    private Integer id;

    /**
     * 网站名称
     */
    private String siteName;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 联系电话
     */
    private String contactPhone;

    /**
     * 联系邮箱
     */
    private String contactEmail;

    /**
     * 公司地址
     */
    private String address;

    /**
     * 企业简介
     */
    private String description;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    private LocalDateTime updatedAt;
}

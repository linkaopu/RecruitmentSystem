package com.lin.server.service;

import com.lin.pojo.entity.Settings;

/**
 * 网站基础配置服务接口
 */
public interface SettingsService {

    /**
     * 获取网站基础配置（若不存在则自动初始化一条空记录）
     */
    Settings getSettings();

    /**
     * 更新网站基础配置
     */
    void updateSettings(Settings settings);
}

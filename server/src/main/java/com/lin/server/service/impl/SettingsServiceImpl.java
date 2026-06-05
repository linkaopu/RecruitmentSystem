package com.lin.server.service.impl;

import com.lin.pojo.entity.Settings;
import com.lin.server.mapper.SettingsMapper;
import com.lin.server.service.SettingsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 网站基础配置服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SettingsServiceImpl implements SettingsService {

    private final SettingsMapper settingsMapper;

    @Override
    public Settings getSettings() {
        Settings settings = settingsMapper.selectById();
        if (settings == null) {
            // 首次访问时初始化一条默认记录
            log.info("系统配置不存在，初始化默认记录");
            settings = Settings.builder()
                    .siteName("")
                    .companyName("")
                    .contactPhone("")
                    .contactEmail("")
                    .address("")
                    .description("")
                    .build();
            settingsMapper.insert(settings);
            settings = settingsMapper.selectById();
        }
        return settings;
    }

    @Override
    @Transactional
    public void updateSettings(Settings settings) {
        // 强制id为1，防止误改
        settings.setId(1);
        int rows = settingsMapper.update(settings);
        if (rows == 0) {
            // 如果更新影响0行，说明记录不存在，先插入再更新
            log.info("系统配置记录不存在，先插入默认记录");
            Settings defaults = Settings.builder()
                    .siteName("")
                    .companyName("")
                    .contactPhone("")
                    .contactEmail("")
                    .address("")
                    .description("")
                    .build();
            settingsMapper.insert(defaults);
            settingsMapper.update(settings);
        }
        log.info("系统配置已更新");
    }
}

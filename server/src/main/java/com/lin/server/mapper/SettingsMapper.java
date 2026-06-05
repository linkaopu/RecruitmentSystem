package com.lin.server.mapper;

import com.lin.pojo.entity.Settings;
import org.apache.ibatis.annotations.Mapper;

/**
 * 网站基础配置Mapper接口
 */
@Mapper
public interface SettingsMapper {

    /**
     * 查询唯一配置（id=1）
     */
    Settings selectById();

    /**
     * 插入默认配置（id=1）
     */
    int insert(Settings settings);

    /**
     * 更新配置
     */
    int update(Settings settings);
}

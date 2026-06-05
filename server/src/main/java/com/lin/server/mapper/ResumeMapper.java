package com.lin.server.mapper;

import com.lin.pojo.entity.Resume;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 简历Mapper
 */
@Mapper
public interface ResumeMapper {

    /**
     * 根据用户ID查询简历
     */
    Resume selectByUserId(@Param("userId") Integer userId);

    /**
     * 分页查询简历列表（管理员）
     */
    List<Resume> selectAll(@Param("offset") Integer offset, @Param("pageSize") Integer pageSize);

    /**
     * 查询简历总数
     */
    Long countAll();

    /**
     * 根据ID查询简历详情
     */
    Resume selectById(@Param("id") Integer id);

    /**
     * 插入简历
     */
    int insert(Resume resume);

    /**
     * 更新简历
     */
    int updateById(Resume resume);

    /**
     * 逻辑删除简历
     */
    int deleteById(@Param("id") Integer id);
}

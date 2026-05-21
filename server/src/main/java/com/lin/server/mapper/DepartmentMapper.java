package com.lin.server.mapper;

import com.lin.pojo.entity.Department;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 部门Mapper接口
 */
@Mapper
public interface DepartmentMapper {
    
    /**
     * 查询所有部门
     */
    List<Department> selectAll();
    
    /**
     * 根据ID查询部门
     */
    Department selectById(@Param("id") Integer id);
    
    /**
     * 插入部门
     */
    int insert(Department department);
    
    /**
     * 更新部门信息
     */
    int updateById(Department department);
    
    /**
     * 删除部门
     */
    int deleteById(@Param("id") Integer id);
}

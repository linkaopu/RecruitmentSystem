package com.lin.server.service;

import com.lin.pojo.dto.CreateDepartmentDTO;
import com.lin.pojo.dto.UpdateDepartmentDTO;
import com.lin.pojo.entity.Department;

import java.util.List;

/**
 * 部门服务接口
 */
public interface DepartmentService {
    
    /**
     * 查询所有部门
     */
    List<Department> getDepartments();
    
    /**
     * 根据ID查询部门
     */
    Department getDepartmentById(Integer id);
    
    /**
     * 创建部门
     */
    Department createDepartment(CreateDepartmentDTO createDepartmentDTO);
    
    /**
     * 更新部门信息
     */
    Department updateDepartment(Integer id, UpdateDepartmentDTO updateDepartmentDTO);
    
    /**
     * 删除部门
     */
    void deleteDepartment(Integer id);
}

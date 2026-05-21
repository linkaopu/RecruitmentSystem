package com.lin.server.service.impl;

import com.lin.common.exception.BusinessException;
import com.lin.pojo.dto.CreateDepartmentDTO;
import com.lin.pojo.dto.UpdateDepartmentDTO;
import com.lin.pojo.entity.Department;
import com.lin.server.mapper.DepartmentMapper;
import com.lin.server.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 部门服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    
    private final DepartmentMapper departmentMapper;
    
    @Override
    public List<Department> getDepartments() {
        return departmentMapper.selectAll();
    }
    
    @Override
    public Department getDepartmentById(Integer id) {
        Department department = departmentMapper.selectById(id);
        if (department == null) {
            throw new BusinessException("部门不存在");
        }
        return department;
    }
    
    @Override
    @Transactional
    public Department createDepartment(CreateDepartmentDTO createDepartmentDTO) {
        // 检查部门代码是否已存在
        // TODO: 添加部门代码唯一性检查
        
        // 创建部门实体
        Department department = new Department();
        BeanUtils.copyProperties(createDepartmentDTO, department);
        department.setCreatedAt(LocalDateTime.now());
        department.setUpdatedAt(LocalDateTime.now());
        
        // 插入数据库
        departmentMapper.insert(department);
        
        log.info("创建部门成功，部门名称: {}", department.getName());
        
        return department;
    }
    
    @Override
    @Transactional
    public Department updateDepartment(Integer id, UpdateDepartmentDTO updateDepartmentDTO) {
        // 检查部门是否存在
        Department existingDepartment = departmentMapper.selectById(id);
        if (existingDepartment == null) {
            throw new BusinessException("部门不存在");
        }
        
        // 更新部门信息
        Department department = new Department();
        department.setId(id);
        BeanUtils.copyProperties(updateDepartmentDTO, department);
        
        departmentMapper.updateById(department);
        
        log.info("更新部门成功，部门ID: {}", id);
        
        // 返回更新后的部门信息
        return departmentMapper.selectById(id);
    }
    
    @Override
    @Transactional
    public void deleteDepartment(Integer id) {
        // 检查部门是否存在
        Department existingDepartment = departmentMapper.selectById(id);
        if (existingDepartment == null) {
            throw new BusinessException("部门不存在");
        }
        
        // 删除部门
        departmentMapper.deleteById(id);
        
        log.info("删除部门成功，部门ID: {}", id);
    }
}

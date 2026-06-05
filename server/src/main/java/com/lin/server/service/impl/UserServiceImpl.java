package com.lin.server.service.impl;

import com.lin.common.exception.UserException;
import com.lin.common.result.PageResult;
import com.lin.common.util.Md5Util;
import com.lin.pojo.dto.CreateUserDTO;
import com.lin.pojo.dto.UpdateUserDTO;
import com.lin.pojo.dto.UserQueryDTO;
import com.lin.pojo.entity.User;
import com.lin.pojo.vo.UserVO;
import com.lin.server.mapper.UserMapper;
import com.lin.server.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    
    private final UserMapper userMapper;
    
    @Override
    public PageResult<UserVO> getUsers(UserQueryDTO query) {
        // 查询用户列表
        List<User> users = userMapper.selectUsers(query);
        
        // 统计总数
        Long total = userMapper.countUsers(query);
        
        // 转换为VO
        List<UserVO> userVOList = users.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
        
        return PageResult.of(query.getPage(), query.getPageSize(), total, userVOList);
    }
    
    @Override
    public UserVO getUserById(Integer id) {
        User user = userMapper.selectById(id);
        if (user == null) {
            throw UserException.notFound();
        }
        return convertToVO(user);
    }
    
    @Override
    @Transactional
    public UserVO createUser(CreateUserDTO createUserDTO) {

        // 检查邮箱是否已存在
        User existingUser = userMapper.selectByEmail(createUserDTO.getEmail());
        if (existingUser != null) {
            throw UserException.emailExists();
        }
        
        // 检查手机号是否已存在
        if (createUserDTO.getPhone() != null && !createUserDTO.getPhone().isEmpty()) {
            existingUser = userMapper.selectByPhone(createUserDTO.getPhone());
            if (existingUser != null) {
                throw UserException.phoneExists();
            }
        }
        
        // 创建用户实体
        User user = new User();
        BeanUtils.copyProperties(createUserDTO, user);
        
        // 对密码进行MD5加密
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(Md5Util.md5(user.getPassword()));
        } else {
            throw new IllegalArgumentException("密码不能为空");
        }
        
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        
        // 插入数据库
        userMapper.insert(user);
        
        log.info("创建用户成功，用户名: {}", user.getUsername());
        
        return convertToVO(user);
    }
    
    @Override
    @Transactional
    public UserVO updateUser(Integer id, UpdateUserDTO updateUserDTO) {
        // 检查用户是否存在
        User existingUser = userMapper.selectById(id);
        if (existingUser == null) {
            throw UserException.notFound();
        }
        
        // 更新用户信息
        User user = new User();
        user.setId(id);
        BeanUtils.copyProperties(updateUserDTO, user);
        
        // 如果密码被更新，则进行MD5加密
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(Md5Util.md5(user.getPassword()));
            log.info("用户ID: {} 的密码已更新并加密", id);
        } else {
            // 如果DTO中没有密码字段，则不更新密码
            user.setPassword(null);
        }
        
        userMapper.updateById(user);
        
        log.info("更新用户成功，用户ID: {}", id);
        
        // 返回更新后的用户信息
        User updatedUser = userMapper.selectById(id);
        return convertToVO(updatedUser);
    }
    
    @Override
    @Transactional
    public void deleteUser(Integer id) {
        // 检查用户是否存在
        User existingUser = userMapper.selectById(id);
        if (existingUser == null) {
            throw UserException.notFound();
        }
        
        // 删除用户
        userMapper.deleteById(id);
        
        log.info("删除用户成功，用户ID: {}", id);
    }
    
    // TODO: toggleUserStatus方法暂时禁用，因为数据库users表中没有status字段
    // 如果需要使用此功能，请先在数据库中添加status字段
    /*
    @Override
    @Transactional
    public void toggleUserStatus(Integer id, String status) {
        // 检查用户是否存在
        User existingUser = userMapper.selectById(id);
        if (existingUser == null) {
            throw new BusinessException("用户不存在");
        }
        
        // 验证状态值
        if (!"active".equals(status) && !"inactive".equals(status)) {
            throw new BusinessException("无效的用户状态");
        }
        
        // 更新状态
        userMapper.updateStatus(id, status);
        
        log.info("更新用户状态成功，用户ID: {}, 状态: {}", id, status);
    }
    */
    
    /**
     * 将User实体转换为UserVO
     */
    private UserVO convertToVO(User user) {
        UserVO vo = new UserVO();
        BeanUtils.copyProperties(user, vo);
        return vo;
    }
}

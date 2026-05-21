package com.lin.server.service;

import com.lin.common.result.PageResult;
import com.lin.pojo.dto.CreateUserDTO;
import com.lin.pojo.dto.UpdateUserDTO;
import com.lin.pojo.dto.UserQueryDTO;
import com.lin.pojo.vo.UserVO;

/**
 * 用户服务接口
 */
public interface UserService {
    
    /**
     * 分页查询用户列表
     */
    PageResult<UserVO> getUsers(UserQueryDTO query);
    
    /**
     * 根据ID查询用户
     */
    UserVO getUserById(Integer id);
    
    /**
     * 创建用户
     */
    UserVO createUser(CreateUserDTO createUserDTO);
    
    /**
     * 更新用户信息
     */
    UserVO updateUser(Integer id, UpdateUserDTO updateUserDTO);
    
    /**
     * 删除用户
     */
    void deleteUser(Integer id);
    
    /**
     * 更新用户状态
     */
    void toggleUserStatus(Integer id, String status);
}

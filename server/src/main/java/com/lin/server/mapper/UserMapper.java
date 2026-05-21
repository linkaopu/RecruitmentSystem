package com.lin.server.mapper;

import com.lin.pojo.dto.UserQueryDTO;
import com.lin.pojo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户Mapper接口
 */
@Mapper
public interface UserMapper {
    
    /**
     * 查询所有用户（分页）
     */
    List<User> selectUsers(@Param("query") UserQueryDTO query);
    
    /**
     * 统计用户总数
     */
    Long countUsers(@Param("query") UserQueryDTO query);
    
    /**
     * 根据ID查询用户
     */
    User selectById(@Param("id") Integer id);
    
    /**
     * 插入用户
     */
    int insert(User user);
    
    /**
     * 更新用户信息
     */
    int updateById(User user);
    
    /**
     * 删除用户
     */
    int deleteById(@Param("id") Integer id);
    
    /**
     * 更新用户状态
     */
    int updateStatus(@Param("id") Integer id, @Param("status") String status);
}

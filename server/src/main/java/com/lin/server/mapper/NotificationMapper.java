package com.lin.server.mapper;

import com.lin.pojo.entity.Notification;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 通知Mapper
 */
@Mapper
public interface NotificationMapper {

    /**
     * 分页查询用户通知列表
     */
    List<Notification> selectByUserId(@Param("userId") Integer userId,
                                      @Param("offset") Integer offset,
                                      @Param("pageSize") Integer pageSize);

    /**
     * 查询所有用户通知列表（不分页）
     */
    List<Notification> selectAllByUserId(@Param("userId") Integer userId);

    /**
     * 查询用户通知总数
     */
    Long countByUserId(@Param("userId") Integer userId);

    /**
     * 根据ID查询通知
     */
    Notification selectById(@Param("id") Integer id);

    /**
     * 更新通知为已读状态
     */
    int updateReadStatus(@Param("id") Integer id, @Param("isRead") Boolean isRead);

    /**
     * 更新用户所有通知为已读状态
     */
    int updateAllReadStatus(@Param("userId") Integer userId);

    /**
     * 逻辑删除通知
     */
    int deleteById(@Param("id") Integer id);

    /**
     * 插入通知记录
     */
    int insert(Notification notification);
}

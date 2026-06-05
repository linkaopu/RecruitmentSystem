package com.lin.server.service;

import com.lin.common.result.PageResult;
import com.lin.pojo.entity.Notification;

import java.util.List;

/**
 * 通知服务接口
 */
public interface NotificationService {

    /**
     * 获取所有通知列表（不分页）
     */
    List<Notification> getNotifications(Integer userId);

    /**
     * 分页获取用户通知列表
     */
    PageResult<Notification> getNotifications(Integer userId, Integer page, Integer pageSize);

    /**
     * 标记通知为已读
     */
    void markAsRead(Integer id, Integer userId);

    /**
     * 标记所有通知为已读
     */
    void markAllAsRead(Integer userId);

    /**
     * 删除通知
     */
    void deleteNotification(Integer id, Integer userId);

    /**
     * 创建通知
     */
    void createNotification(Notification notification);
}

package com.lin.server.service.impl;

import com.lin.common.result.PageResult;
import com.lin.pojo.entity.Notification;
import com.lin.server.mapper.NotificationMapper;
import com.lin.server.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 通知服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationMapper notificationMapper;

    @Override
    public List<Notification> getNotifications(Integer userId) {
        List<Notification> list = notificationMapper.selectAllByUserId(userId);
        log.info("获取用户所有通知列表，userId: {}, 数量: {}", userId, list.size());
        return list;
    }

    @Override
    public PageResult<Notification> getNotifications(Integer userId, Integer page, Integer pageSize) {
        // 计算偏移量
        int offset = (page - 1) * pageSize;

        // 查询通知列表
        List<Notification> list = notificationMapper.selectByUserId(userId, offset, pageSize);

        // 查询总数
        Long total = notificationMapper.countByUserId(userId);

        log.info("获取用户通知列表，userId: {}, page: {}, pageSize: {}, total: {}", userId, page, pageSize, total);

        return PageResult.of(page, pageSize, total, list);
    }

    @Override
    @Transactional
    public void markAsRead(Integer id, Integer userId) {
        // 检查通知是否存在且属于当前用户
        Notification notification = notificationMapper.selectById(id);
        if (notification == null) {
            throw new IllegalArgumentException("通知不存在");
        }

        if (!userId.equals(notification.getUserId())) {
            throw new IllegalArgumentException("无权操作此通知");
        }

        notificationMapper.updateReadStatus(id, true);

        log.info("标记通知为已读，ID: {}, userId: {}", id, userId);
    }

    @Override
    @Transactional
    public void markAllAsRead(Integer userId) {
        notificationMapper.updateAllReadStatus(userId);
        log.info("标记用户所有通知为已读，userId: {}", userId);
    }

    @Override
    @Transactional
    public void deleteNotification(Integer id, Integer userId) {
        // 检查通知是否存在且属于当前用户
        Notification notification = notificationMapper.selectById(id);
        if (notification == null) {
            throw new IllegalArgumentException("通知不存在");
        }

        if (!userId.equals(notification.getUserId())) {
            throw new IllegalArgumentException("无权操作此通知");
        }

        notificationMapper.deleteById(id);

        log.info("删除通知，ID: {}, userId: {}", id, userId);
    }

    @Override
    @Transactional
    public void createNotification(Notification notification) {
        Notification newNotification = Notification.builder()
                .userId(notification.getUserId())
                .title(notification.getTitle())
                .content(notification.getContent())
                .type(notification.getType())
                .isRead(0)
                .isDelete(0)
                .createdAt(LocalDateTime.now())
                .build();

        notificationMapper.insert(newNotification);

        log.info("创建通知成功，userId: {}, type: {}", notification.getUserId(), notification.getType());
    }
}

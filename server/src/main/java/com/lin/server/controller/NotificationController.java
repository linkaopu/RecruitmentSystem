package com.lin.server.controller;

import com.lin.common.result.PageResult;
import com.lin.common.result.Result;

import com.lin.common.util.BaseContextUtil;
import com.lin.pojo.entity.Notification;
import com.lin.server.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * 通知控制器
 */
@Slf4j
@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
@Tag(name = "通知模块", description = "通知管理相关接口")
public class NotificationController {

    private final NotificationService notificationService;

    /**
     * 获取通知列表（分页）
     */
    @GetMapping
    @Operation(summary = "获取通知列表", description = "分页获取当前用户的通知列表")
    public Result<PageResult<Notification>> getNotifications(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer page,
            @Parameter(description = "每页数量") @RequestParam(defaultValue = "10") Integer pageSize) {
        Integer userId = BaseContextUtil.getUserId();
        log.info("获取通知列表，userId: {}, page: {}, pageSize: {}", userId, page, pageSize);
        PageResult<Notification> pageResult = notificationService.getNotifications(userId, page, pageSize);
        return Result.success(pageResult);
    }

    /**
     * 标记通知为已读
     */
    @PutMapping("/{id}/read")
    @Operation(summary = "标记通知为已读", description = "将指定通知标记为已读状态")
    public Result<Void> markAsRead(@Parameter(description = "通知ID") @PathVariable Integer id) {
        Integer userId = BaseContextUtil.getUserId();
        log.info("标记通知为已读，ID: {}, userId: {}", id, userId);
        notificationService.markAsRead(id, userId);
        return Result.success("标记已读成功", null);
    }

    /**
     * 标记所有通知为已读
     */
    @PutMapping("/read-all")
    @Operation(summary = "标记所有通知为已读", description = "将当前用户所有通知标记为已读")
    public Result<Void> markAllAsRead() {
        Integer userId = BaseContextUtil.getUserId();
        log.info("标记所有通知为已读，userId: {}", userId);
        notificationService.markAllAsRead(userId);
        return Result.success("全部标记已读成功", null);
    }

    /**
     * 删除通知
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除通知", description = "删除指定通知")
    public Result<Void> deleteNotification(@Parameter(description = "通知ID") @PathVariable Integer id) {
        Integer userId = BaseContextUtil.getUserId();
        log.info("删除通知，ID: {}, userId: {}", id, userId);
        notificationService.deleteNotification(id, userId);
        return Result.success("删除通知成功", null);
    }
}

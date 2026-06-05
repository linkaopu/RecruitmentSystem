package com.lin.server.service.impl;

import com.lin.common.result.PageResult;
import com.lin.common.util.BaseContextUtil;
import com.lin.pojo.entity.Favorite;
import com.lin.pojo.vo.FavoriteVO;
import com.lin.server.mapper.FavoriteMapper;
import com.lin.server.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 收藏服务实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteMapper favoriteMapper;

    @Override
    public List<FavoriteVO> getFavorites() {
        Integer userId = BaseContextUtil.getUserId();
        if (userId == null) {
            throw new IllegalArgumentException("用户未登录");
        }

        List<FavoriteVO> list = favoriteMapper.selectAllFavoritesByUserId(userId);

        log.info("用户 {} 查询所有收藏列表，数量: {}", userId, list.size());

        return list;
    }

    @Override
    public PageResult<FavoriteVO> getFavorites(Integer page, Integer pageSize) {
        // 从ThreadLocal中获取当前用户ID
        Integer userId = BaseContextUtil.getUserId();
        if (userId == null) {
            throw new IllegalArgumentException("用户未登录");
        }

        // 计算偏移量
        int offset = (page - 1) * pageSize;

        // 查询收藏列表
        List<FavoriteVO> list = favoriteMapper.selectFavoritesByUserId(userId, offset, pageSize);

        // 查询总数
        Long total = favoriteMapper.countByUserId(userId);

        log.info("用户 {} 查询收藏列表，page: {}, pageSize: {}, total: {}", userId, page, pageSize, total);

        return PageResult.of(page, pageSize, total, list);
    }

    @Override
    @Transactional
    public void addFavorite(Integer jobId) {
        // 从ThreadLocal中获取当前用户ID
        Integer userId = BaseContextUtil.getUserId();
        if (userId == null) {
            throw new IllegalArgumentException("用户未登录");
        }

        // 检查是否已经收藏
        Favorite existing = favoriteMapper.selectByUserIdAndJobId(userId, jobId);
        if (existing != null) {
            log.warn("用户 {} 已经收藏过职位 {}", userId, jobId);
            throw new IllegalArgumentException("已经收藏过该职位");
        }

        // 创建收藏记录
        Favorite favorite = Favorite.builder()
                .userId(userId)
                .jobId(jobId)
                .createdAt(LocalDateTime.now())
                .build();

        favoriteMapper.insert(favorite);

        log.info("用户 {} 收藏职位 {} 成功", userId, jobId);
    }

    @Override
    @Transactional
    public void removeFavorite(Integer jobId) {
        // 从ThreadLocal中获取当前用户ID
        Integer userId = BaseContextUtil.getUserId();
        if (userId == null) {
            throw new IllegalArgumentException("用户未登录");
        }

        int rows = favoriteMapper.deleteByUserIdAndJobId(userId, jobId);
        if (rows == 0) {
            log.warn("用户 {} 取消收藏职位 {} 失败，未找到收藏记录", userId, jobId);
            throw new IllegalArgumentException("未找到收藏记录");
        }

        log.info("用户 {} 取消收藏职位 {} 成功", userId, jobId);
    }

    @Override
    public boolean checkFavorite(Integer jobId) {
        // 从ThreadLocal中获取当前用户ID
        Integer userId = BaseContextUtil.getUserId();
        if (userId == null) {
            throw new IllegalArgumentException("用户未登录");
        }

        boolean isFavorite = favoriteMapper.checkIsFavorite(userId, jobId);

        log.debug("用户 {} 检查职位 {} 是否收藏: {}", userId, jobId, isFavorite);

        return isFavorite;
    }
}

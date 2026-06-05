package com.lin.server.service;

import com.lin.common.result.PageResult;
import com.lin.pojo.vo.FavoriteVO;

import java.util.List;

/**
 * 收藏服务接口
 */
public interface FavoriteService {

    /**
     * 获取所有收藏列表（不分页）
     */
    List<FavoriteVO> getFavorites();

    /**
     * 分页获取收藏列表
     */
    PageResult<FavoriteVO> getFavorites(Integer page, Integer pageSize);

    /**
     * 添加收藏
     */
    void addFavorite(Integer jobId);

    /**
     * 取消收藏
     */
    void removeFavorite(Integer jobId);

    /**
     * 检查是否已收藏
     */
    boolean checkFavorite(Integer jobId);
}

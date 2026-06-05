package com.lin.server.controller;

import com.lin.common.result.PageResult;
import com.lin.common.result.Result;
import com.lin.pojo.vo.FavoriteVO;

import com.lin.server.service.FavoriteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 收藏控制器
 */
@Slf4j
@RestController
@RequestMapping("/favorites")
@RequiredArgsConstructor
@Tag(name = "收藏模块", description = "职位收藏相关接口")
public class FavoriteController {

    private final FavoriteService favoriteService;

    /**
     * 获取收藏列表（分页）
     */
    @GetMapping
    @Operation(summary = "获取收藏列表", description = "分页获取当前用户的收藏列表")
    public Result<PageResult<FavoriteVO>> getFavorites(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer page,
            @Parameter(description = "每页数量") @RequestParam(defaultValue = "10") Integer pageSize) {
        log.info("获取收藏列表，page: {}, pageSize: {}", page, pageSize);
        PageResult<FavoriteVO> pageResult = favoriteService.getFavorites(page, pageSize);
        return Result.success(pageResult);
    }

    /**
     * 添加收藏
     */
    @PostMapping
    @Operation(summary = "添加收藏", description = "收藏某个职位")
    public Result<Void> addFavorite(@Parameter(description = "职位ID") @RequestBody Map<String, Integer> params) {
        Integer jobId = params.get("jobId");
        log.info("添加收藏，jobId: {}", jobId);
        favoriteService.addFavorite(jobId);
        return Result.success("收藏成功", null);
    }

    /**
     * 取消收藏
     */
    @DeleteMapping("/{jobId}")
    @Operation(summary = "取消收藏", description = "取消收藏某个职位")
    public Result<Void> removeFavorite(
            @Parameter(description = "职位ID") @PathVariable Integer jobId) {
        log.info("取消收藏，jobId: {}", jobId);
        favoriteService.removeFavorite(jobId);
        return Result.success("取消收藏成功", null);
    }

    /**
     * 检查是否已收藏
     */
    @GetMapping("/check/{jobId}")
    @Operation(summary = "检查是否已收藏", description = "检查当前用户是否已收藏某个职位")
    public Result<Map<String, Boolean>> checkFavorite(
            @Parameter(description = "职位ID") @PathVariable Integer jobId) {
        log.info("检查是否已收藏，jobId: {}", jobId);
        boolean isFavorite = favoriteService.checkFavorite(jobId);
        Map<String, Boolean> result = new HashMap<>();
        result.put("isFavorite", isFavorite);
        return Result.success(result);
    }
}

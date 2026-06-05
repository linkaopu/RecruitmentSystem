package com.lin.server.mapper;

import com.lin.pojo.entity.Favorite;
import com.lin.pojo.vo.FavoriteVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 收藏Mapper
 */
@Mapper
public interface FavoriteMapper {

    /**
     * 分页查询收藏列表（包含职位详情）
     */
    List<FavoriteVO> selectFavoritesByUserId(@Param("userId") Integer userId,
                                              @Param("offset") Integer offset,
                                              @Param("pageSize") Integer pageSize);

    /**
     * 查询所有收藏列表（不分页）
     */
    List<FavoriteVO> selectAllFavoritesByUserId(@Param("userId") Integer userId);

    /**
     * 查询收藏总数
     */
    Long countByUserId(@Param("userId") Integer userId);

    /**
     * 根据用户ID和职位ID查询收藏
     */
    Favorite selectByUserIdAndJobId(@Param("userId") Integer userId, @Param("jobId") Integer jobId);

    /**
     * 插入收藏
     */
    int insert(Favorite favorite);

    /**
     * 逻辑删除收藏
     */
    int deleteByUserIdAndJobId(@Param("userId") Integer userId, @Param("jobId") Integer jobId);

    /**
     * 检查是否已收藏
     */
    boolean checkIsFavorite(@Param("userId") Integer userId, @Param("jobId") Integer jobId);
}

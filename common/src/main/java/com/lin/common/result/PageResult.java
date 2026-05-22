package com.lin.common.result;

import lombok.Data;

/**
 * 分页返回结果
 */
@Data
public class PageResult<T> {
    
    /**
     * 当前页码
     */
    private Integer pageNum;
    
    /**
     * 每页数量
     */
    private Integer pageSize;
    
    /**
     * 总记录数
     */
    private Long total;
    
    /**
     * 总页数
     */
    private Integer totalPages;
    
    /**
     * 数据列表
     */
    private java.util.List<T> list;
    
    public PageResult() {
    }
    
    public PageResult(Integer pageNum, Integer pageSize, Long total, java.util.List<T> list) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.total = total;
        this.list = list;
        this.totalPages = (int) Math.ceil((double) total / pageSize);
    }
    
    /**
     * 构建分页结果
     */
    public static <T> PageResult<T> of(Integer pageNum, Integer pageSize, Long total, java.util.List<T> list) {
        return new PageResult<>(pageNum, pageSize, total, list);
    }
}

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
    private Integer page;
    
    /**
     * 每页数量
     */
    private Integer pageSize;
    
    /**
     * 总记录数
     */
    private Long total;
    
    /**
     * 数据列表
     */
    private java.util.List<T> list;
    
    public PageResult() {
    }
    
    public PageResult(Integer page, Integer pageSize, Long total, java.util.List<T> list) {
        this.page = page;
        this.pageSize = pageSize;
        this.total = total;
        this.list = list;
    }
    
    /**
     * 构建分页结果
     */
    public static <T> PageResult<T> of(Integer page, Integer pageSize, Long total, java.util.List<T> list) {
        return new PageResult<>(page, pageSize, total, list);
    }
}

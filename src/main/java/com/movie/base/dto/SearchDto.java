package com.movie.base.dto;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class SearchDto {
    
    /**
     * 返回条目总数
     */
    private Integer size;
    
    /**
     * 偏移量
     */
    private Long from;
    
    /**
     * 页面数量
     */
    private Long pageSize;
    
    /**
     * 当前页
     */
    private Integer currentPage;
    
    /**
     * 总数
     */
    private Long total;
    
    private List<Map<String, Object>> hits;
}

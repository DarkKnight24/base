package com.movie.base.utils;

import java.util.List;

import com.github.pagehelper.IPage;

import lombok.Data;

@Data
public class Page<T> implements IPage {
    
    private Integer currentPage = 1;
    
    private Integer pageSize = 10;
    
    private long total;
    
    private int pages;
    
    private String orderBy;
    
    private List<T> list;
    
    @Override
    public Integer getPageNum() {
        return this.currentPage;
    }
    
    @Override
    public Integer getPageSize() {
        return this.pageSize;
    }
    
    @Override
    public String getOrderBy() {
        return this.orderBy;
    }
}

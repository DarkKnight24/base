package com.movie.base.utils;

import com.github.pagehelper.IPage;
import lombok.Data;

import java.util.List;

@Data
public class Page<T> implements IPage {

    private Integer pageNum = 1;
    private Integer pageSize = 10;
    private long total;
    private int pages;
    private String orderBy;
    private List<T> list;

    @Override
    public Integer getPageNum() {
        return this.pageNum;
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

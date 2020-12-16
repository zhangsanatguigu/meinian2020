package com.atguigu.service;

import com.atguigu.entity.PageResult;
import com.atguigu.pojo.Setmeal;

public interface SetmealService {
    void add(Integer[] travelgroupIds, Setmeal setmeal);

    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);
}

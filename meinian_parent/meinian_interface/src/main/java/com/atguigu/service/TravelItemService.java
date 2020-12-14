package com.atguigu.service;

import com.atguigu.entity.PageResult;
import com.atguigu.pojo.TravelItem;

public interface TravelItemService {
    void add(TravelItem travelItem);

    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    void deleteById(Integer id);

    TravelItem getById(Integer id);

    void edit(TravelItem travelItem);
}

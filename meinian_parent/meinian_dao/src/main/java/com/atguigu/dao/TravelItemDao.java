package com.atguigu.dao;

import com.atguigu.pojo.TravelItem;
import com.github.pagehelper.Page;

public interface TravelItemDao {
    void add(TravelItem travelItem);

    Page<TravelItem> findPage(String queryString);

    void deleteById(Integer id);

    long findCountByTravelItemItemId(Integer id);

    TravelItem getById(Integer id);

    void edit(TravelItem travelItem);
}

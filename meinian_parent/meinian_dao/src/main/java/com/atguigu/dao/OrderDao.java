package com.atguigu.dao;

import com.atguigu.pojo.Order;
import java.util.List;
import java.util.Map;

public interface OrderDao {

    List<Order> findByCondition(Order order);

    void add(Order order);

    Map findById4Detail(Integer id);

    Integer getTodayOrderNumber(String today);

    Integer getTodayVisitsNumber(String today);

    Integer getThisWeekAndMonthOrderNumber(Map paramWeekOrder);

    Integer getThisWeekAndMonthVisitsNumber(Map paramWeekVisits);

    List<Map<String, Object>> findHotSetmeal();
}

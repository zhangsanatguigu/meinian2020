package com.atguigu.dao;

import com.atguigu.pojo.OrderSetting;

import java.util.Date;

public interface OrderSettingDao {
    void add(OrderSetting orderSetting);

    long findCountByOrderDate(Date orderDate);

    void editNumberByOrderDate(OrderSetting orderSetting);
}

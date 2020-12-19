package com.atguigu.dao;

import com.atguigu.pojo.Order;
import java.util.List;

public interface OrderDao {

    List<Order> findByCondition(Order order);

    void add(Order order);
}

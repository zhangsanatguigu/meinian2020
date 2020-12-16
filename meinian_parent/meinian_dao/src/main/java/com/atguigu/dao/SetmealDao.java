package com.atguigu.dao;

import com.atguigu.pojo.Setmeal;

import java.util.Map;

public interface SetmealDao {

    void setSetmealAndTravelGroup(Map<String, Integer> map);

    void add(Setmeal setmeal);
}

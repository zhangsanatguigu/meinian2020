package com.atguigu.dao;

import com.atguigu.pojo.Setmeal;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface SetmealDao {

    void setSetmealAndTravelGroup(Map<String, Integer> map);

    void add(Setmeal setmeal);

    Page<Setmeal> findPage(String queryString);

    List<Setmeal> findAll();
}

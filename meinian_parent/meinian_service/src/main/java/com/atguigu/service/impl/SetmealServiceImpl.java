package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.SetmealDao;
import com.atguigu.pojo.Setmeal;
import com.atguigu.service.SetmealService;

import com.atguigu.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    SetmealDao setmealDao;

    @Override
    public void add(Integer[] travelgroupIds, Setmeal setmeal) {
        setmealDao.add(setmeal);

        if(travelgroupIds!=null && travelgroupIds.length > 0){
            setSetmealAndTravelGroup(setmeal.getId(),travelgroupIds);
        }
    }

    private void setSetmealAndTravelGroup(Integer id, Integer[] travelgroupIds) {
        for (Integer travelgroupId : travelgroupIds) {
            Map<String,Integer> map = new HashMap<>();
            map.put("travelgroupId",travelgroupId);
            map.put("setmealId",id);
            setmealDao.setSetmealAndTravelGroup(map);
        }
    }
}

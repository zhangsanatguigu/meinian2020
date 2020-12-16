package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.SetmealDao;
import com.atguigu.entity.PageResult;
import com.atguigu.pojo.Setmeal;
import com.atguigu.service.SetmealService;

import com.atguigu.service.SetmealService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
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

    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        PageHelper.startPage(currentPage,pageSize);
        Page<Setmeal> page = setmealDao.findPage(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }
}

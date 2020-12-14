package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.TravelGroupDao;
import com.atguigu.entity.PageResult;
import com.atguigu.pojo.TravelGroup;
import com.atguigu.service.TravelGroupService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Service(interfaceClass = TravelGroupService.class)
public class TravelGroupServiceImpl implements TravelGroupService {

    @Autowired
    TravelGroupDao travelGroupDao;

    @Override
    public void add(TravelGroup travelGroup, Integer[] travelItemIds) {
        travelGroupDao.add(travelGroup);
        setTravelGroupAndTravelItem(travelGroup.getId(),travelItemIds);
    }

    private void setTravelGroupAndTravelItem(Integer travelGroupId, Integer[] travelItemIds) {
        if(travelItemIds!=null && travelItemIds.length>0){
            for (Integer travelItemId : travelItemIds) {
                Map<String,Integer> map = new HashMap<String,Integer>();
                map.put("travelGroupId",travelGroupId);
                map.put("travelItemId",travelItemId);
                travelGroupDao.setTravelGroupAndTravelItem(map);
            }
        }
    }

    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {

        PageHelper.startPage(currentPage,pageSize);

        Page<TravelGroup> page = travelGroupDao.findPage(queryString);

        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public TravelGroup findById(Integer id) {
        TravelGroup travelGroup = travelGroupDao.findById(id);
        return travelGroup;
    }

    @Override
    public List<Integer> findTravelItemIdByTravelgroupId(Integer id) {
        return travelGroupDao.findTravelItemIdByTravelgroupId(id);
    }

    @Override
    public void edit(TravelGroup travelGroup, Integer[] travelItemIds) {
        travelGroupDao.edit(travelGroup);
        travelGroupDao.deleteTravelGroupAndTravelItemByTravelGroupId(travelGroup.getId());
        setTravelGroupAndTravelItem(travelGroup.getId(),travelItemIds);
    }
}

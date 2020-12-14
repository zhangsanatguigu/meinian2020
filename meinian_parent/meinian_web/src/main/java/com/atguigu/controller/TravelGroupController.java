package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.entity.Result;
import com.atguigu.pojo.TravelGroup;
import com.atguigu.service.TravelGroupService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/travelGroup")
public class TravelGroupController {

    @Reference
    TravelGroupService travelGroupService;

    @RequestMapping("/add")
    public Result add (@RequestBody TravelGroup travelGroup, Integer[] travelItemIds){
        travelGroupService.add(travelGroup,travelItemIds);
        return new Result(true, MessageConstant.ADD_TRAVELGROUP_SUCCESS);
    }

    @PostMapping("/findPage")
    public PageResult findPage(@RequestBody  QueryPageBean queryPageBean){
        PageResult pageResult = travelGroupService.findPage(
                queryPageBean.getCurrentPage(),
                queryPageBean.getPageSize(),
                queryPageBean.getQueryString());
        return pageResult;
    }

    @GetMapping("/findById")
    public Result findById(Integer id){
        TravelGroup travelGroup = travelGroupService.findById(id);
        return new Result(true,MessageConstant.QUERY_TRAVELGROUP_SUCCESS,travelGroup);
    }

    @GetMapping("/findTravelItemIdByTravelgroupId")
    public List<Integer> findTravelItemIdByTravelgroupId(Integer id){
        List<Integer> list = travelGroupService.findTravelItemIdByTravelgroupId(id);
        return list ;
    }

    @RequestMapping("/edit")
    public Result edit(@RequestBody TravelGroup travelGroup,Integer[] travelItemIds){
        travelGroupService.edit(travelGroup,travelItemIds);
        return new Result(true,MessageConstant.EDIT_TRAVELGROUP_SUCCESS);
    }
}

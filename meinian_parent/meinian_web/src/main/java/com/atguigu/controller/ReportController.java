package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.entity.Result;
import com.atguigu.service.MemberService;
import com.atguigu.service.ReportService;
import com.atguigu.service.SetmealService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.channels.NonWritableChannelException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Reference
    MemberService memberService;

    @Reference
    SetmealService setmealService;

    @Reference
    ReportService reportService;

    @RequestMapping("/getMemberReport")
    public Result getMemberReport(){
        Map map = new HashMap();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,-12);

        List<String> list = new ArrayList();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        for (int i = 0; i < 12; i++) {
            calendar.add(Calendar.MONTH,1);
            list.add(format.format(calendar.getTime()));
        }

        map.put("months",list);

        List<Integer> memberCounts = memberService.findMemberCountByMonth(list);

        map.put("memberCount",memberCounts);

        return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,map);
    }

    @RequestMapping("/getSetmealReport")
    public Result getSetmealReport(){
        Map<String,Object> map = new HashMap<String,Object>();

        List<String> setmealNames = new ArrayList<String>();

        List<Map<String,Object>> setmealCount = setmealService.findSetmealCount();
        for (Map<String, Object> m : setmealCount) {
            String name = (String)m.get("name");
            setmealNames.add(name);
        }
        map.put("setmealNames",setmealNames); // ['直接访问', '邮件营销', '联盟广告', '视频广告', '搜索引擎']
        map.put("setmealCount",setmealCount); // [{value: 335, name: '直接访问'},{value: 310, name: '邮件营销'}]

        return new Result(true,MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS,map) ;
    }

    /** 运营统计数据*/
    @RequestMapping("/getBusinessReportData")
    public Result getBusinessReportData(){
        try {
            Map<String,Object> data = reportService.getBusinessReportData();
            return new Result(true,MessageConstant.GET_BUSINESS_REPORT_SUCCESS,data) ;
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }
    }
}

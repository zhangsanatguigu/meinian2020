package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.entity.Result;
import com.atguigu.service.MemberService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Reference
    MemberService memberService;

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

}

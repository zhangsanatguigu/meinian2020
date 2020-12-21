package com.atguigu.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.MemberDao;
import com.atguigu.dao.OrderDao;
import com.atguigu.service.ReportService;
import com.atguigu.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service(interfaceClass = ReportService.class)
@Transactional
public class ReportServiceImpl implements ReportService {

    @Autowired
    MemberDao memberDao;

    @Autowired
    OrderDao orderDao;

    /**
     * 运营统计数据
     * reportDate:null, // 日期
     * todayNewMember :0, // 新增会员数
     * totalMember :0,// 总会员数
     * thisWeekNewMember :0,// 本周新增会员数
     * thisMonthNewMember :0,// 本月新增会员数
     * todayOrderNumber :0,// 今日预约数
     * todayVisitsNumber :0,// 今日出游数
     * thisWeekOrderNumber :0,// 本周预约数
     * thisWeekVisitsNumber :0,// 本周出游数
     * thisMonthOrderNumber :0,// 本月预约数
     * thisMonthVisitsNumber :0,// 本月出游数
     * hotSetmeal :[
     * {name:'海南7天6晚游套餐',setmeal_count:200,proportion:0.222},
     * {name:'深圳3天2晚游套餐',setmeal_count:200,proportion:0.222}
     * ]
     */
    @Override
    public Map<String, Object> getBusinessReportData() {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            //  -- 今天新增会员数
            //
            //  -- 总会员数
            //  -- 本周新增会员数(>=本周的周一的日期)
            //  -- 本月新增会员数(>=本月的第一天的日期)
            //  -------------------------------------------------------------------------------
            //  -- 今日预约数
            //  -- 今日已出游数
            //  -- 本周预约数(>=本周的周一的日期 <=本周的周日的日期)
            //  -- 本周已出游数
            //          -- 本月预约数(>=每月的第一天的日期 <=每月的最后一天的日期)
            //  -- 本月已出游数

            //  -- 热门套餐

            // 日期工具类

            //1:当前时间
            String today = DateUtils.parseDate2String(DateUtils.getToday());
            //2:本周（周一）
            String weekMonday = DateUtils.parseDate2String(DateUtils.getThisWeekMonday());

            //3:本周（周日）
            String weekSunday = DateUtils.parseDate2String(DateUtils.getSundayOfThisWeek());

            //4:本月（1号）
            String monthFirst = DateUtils.parseDate2String(DateUtils.getFirstDay4ThisMonth());

            //5:本月（31）
            String monthLast = DateUtils.parseDate2String(DateUtils.getLastDay4ThisMonth());

            //今日新增会员数
            Integer todayNewMember = memberDao.getTodayNewMember(today);
            //总会员数
            Integer totalMember = memberDao.getTotalMember();

            //本周新增会员数
            Integer thisWeekNewMember = memberDao.getThisWeekAndMonthNewMember(weekMonday);

            //本月新增会员数
            Integer thisMonthNewMember = memberDao.getThisWeekAndMonthNewMember(monthFirst);

            //今日预约数
            Integer todayOrderNumber = orderDao.getTodayOrderNumber(today);

            //今日出游数
            Integer todayVisitsNumber = orderDao.getTodayVisitsNumber(today);

            //本周预约数
            Map paramWeekOrder = new HashMap();
            paramWeekOrder.put("begin",weekMonday);
            paramWeekOrder.put("end",weekSunday);
            Integer thisWeekOrderNumber = orderDao.getThisWeekAndMonthOrderNumber(paramWeekOrder);

            //本周出游数
            Map paramWeekVisits = new HashMap();
            paramWeekVisits.put("begin",weekMonday);
            paramWeekVisits.put("end",weekSunday);
            Integer thisWeekVisitsNumber = orderDao.getThisWeekAndMonthVisitsNumber(paramWeekVisits);

            //本月预约数
            Map paramMonthOrder = new HashMap();
            paramMonthOrder.put("begin",monthFirst);
            paramMonthOrder.put("end",monthLast);
            Integer thisMonthOrderNumber = orderDao.getThisWeekAndMonthOrderNumber(paramMonthOrder);

            //本月出游数
            Map paramMonthVisits = new HashMap();
            paramMonthVisits.put("begin",monthFirst);
            paramMonthVisits.put("end",monthLast);
            Integer thisMonthVisitsNumber = orderDao.getThisWeekAndMonthVisitsNumber(paramMonthVisits);

            //热门套餐
            List<Map<String, Object>> hotSetmeal = orderDao.findHotSetmeal();

            map.put("reportDate", today);
            map.put("todayNewMember", todayNewMember);
            map.put("totalMember", totalMember);
            map.put("thisWeekNewMember", thisWeekNewMember);
            map.put("thisMonthNewMember", thisMonthNewMember);
            map.put("todayOrderNumber", todayOrderNumber);
            map.put("todayVisitsNumber", todayVisitsNumber);
            map.put("thisWeekOrderNumber", thisWeekOrderNumber);
            map.put("thisWeekVisitsNumber", thisWeekVisitsNumber);
            map.put("thisMonthOrderNumber", thisMonthOrderNumber);
            map.put("thisMonthVisitsNumber", thisMonthVisitsNumber);
            map.put("hotSetmeal", hotSetmeal);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}

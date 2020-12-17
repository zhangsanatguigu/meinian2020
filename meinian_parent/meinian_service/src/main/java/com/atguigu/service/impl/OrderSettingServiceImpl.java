package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.OrderSettingDao;
import com.atguigu.pojo.OrderSetting;
import com.atguigu.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    OrderSettingDao orderSettingDao;

    @Override
    public void add(List<OrderSetting> orderSettingList) {
        for (OrderSetting orderSetting : orderSettingList) {
            long count = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
            if(count>0){
                orderSettingDao.editNumberByOrderDate(orderSetting);
            }
            orderSettingDao.add(orderSetting);
        }
    }


    @Override
    public List<Map> getOrderSettingByMonth(String date) {
        System.out.println("date = " + date);
        String dateBegin = date + "-1";
        String dateEnd = date + "-31";
        Map<String,String> map = new HashMap<String,String>();
        map.put("dateBegin",dateBegin);
        map.put("dateEnd",dateEnd);
        List<OrderSetting> list = orderSettingDao.getOrderSettingByMonth(map);
        List<Map> data = new ArrayList<>();
        for (OrderSetting orderSetting : list) {
            Map<String,Object> orderSettingMap = new HashMap<>(); //{ date: 1, number: 120, reservations: 1 }
            orderSettingMap.put("date",orderSetting.getOrderDate().getDate());
            orderSettingMap.put("number",orderSetting.getNumber());
            orderSettingMap.put("reservations",orderSetting.getReservations());
            data.add(orderSettingMap);
        }
        return data;
    }
}

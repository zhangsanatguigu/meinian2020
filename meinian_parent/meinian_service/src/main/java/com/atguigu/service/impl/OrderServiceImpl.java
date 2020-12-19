package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.constant.MessageConstant;
import com.atguigu.dao.MemberDao;
import com.atguigu.dao.OrderDao;
import com.atguigu.dao.OrderSettingDao;
import com.atguigu.entity.Result;
import com.atguigu.pojo.Member;
import com.atguigu.pojo.Order;
import com.atguigu.pojo.OrderSetting;
import com.atguigu.service.OrderService;
import com.atguigu.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = OrderService.class)
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderSettingDao orderSettingDao;

    @Autowired
    MemberDao memberDao;

    @Autowired
    OrderDao orderDao;


    @Override
    public Result order(Map map) throws Exception {

        // 1、检查用户所选择的预约日期是否已经提前进行了预约设置，如果没有设置则无法进行预约
        String orderDate = (String) map.get("orderDate");
        Date date = DateUtils.parseString2Date(orderDate);
        OrderSetting orderSetting = orderSettingDao.findByOrderDate(date);
        if (orderSetting == null) {
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        } else {
            // 2、检查用户所选择的预约日期是否已经约满，如果已经约满则无法预约
            int number = orderSetting.getNumber();
            int reservations = orderSetting.getReservations();
            if (reservations >= number) {
                return new Result(false, MessageConstant.ORDER_FULL);
            }
        }

        // 3、检查用户是否重复预约（同一个用户在同一天预约了同一个套餐），如果是重复预约则无法完成再次预约
        // 4、检查当前用户是否为会员，如果是会员则直接完成预约，如果不是会员则自动完成注册并进行预约
        String telephone = (String) map.get("telephone");
        Member member = memberDao.findByTelephone(telephone);
        Integer setmealId = Integer.parseInt((String)map.get("setmealId"));
        if (member != null) {
            Integer memberId = member.getId();
            Order order = new Order(memberId,date,null,null,setmealId);
            List<Order> list = orderDao.findByCondition(order);
            if(list!=null && list.size()>0){
                return new Result(false,MessageConstant.HAS_ORDERED);
            }
        }else{
            member = new Member();
            member.setName((String)map.get("name"));
            member.setSex((String) map.get("sex"));
            member.setIdCard((String) map.get("idCard"));
            member.setPhoneNumber((String) map.get("telephone"));
            member.setRegTime(new Date());
            memberDao.add(member);
        }

        // 5、预约成功，更新当日的已预约人数
        //5.1 更新当日的已预约人数
        orderSetting.setReservations(orderSetting.getReservations()+1);
        orderSettingDao.editReservationsByOrderDate(orderSetting);

        //5.2 预约成功
        Order order = new Order();
        order.setMemberId(member.getId());
        order.setSetmealId(setmealId);
        order.setOrderDate(date);
        order.setOrderType((String) map.get("orderType"));
        order.setOrderStatus(Order.ORDERSTATUS_NO);
        orderDao.add(order);

        return new Result(true,MessageConstant.ORDER_SUCCESS,order);
    }
}

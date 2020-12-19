package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.constant.RedisMessageConstant;
import com.atguigu.entity.Result;
import com.atguigu.pojo.Order;
import com.atguigu.service.OrderService;
import com.atguigu.utils.SMSUtils;
import org.apache.zookeeper.server.quorum.FastLeaderElection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    JedisPool jedisPool;

    @Reference
    OrderService orderService;

    /**
     * 旅游预约
     * @param map
     * @return
     */
    @RequestMapping("/submit")
    public Result order(@RequestBody Map map){
        System.out.println("map = " + map);

        //1.检验手机验证码
        String telephone = (String) map.get("telephone");
        String validateCode = (String) map.get("validateCode");

        String telcodeInRedis = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_ORDER);

        if(telcodeInRedis==null || !telcodeInRedis.equals(validateCode)){
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
        Result result = null;

        try {
            //2.设置预约类型
            map.put("orderType", Order.ORDERTYPE_WEIXIN);

            //3.开始预约
            result = orderService.order(map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ORDER_FAIL);
        }

        if(result.isFlag()){
            //4.预约成功后，给用户手机发送预约成功短信
            //短信通知：预约时间，预约人，预约地点，预约事项等信息
            //String orderDate = (String) map.get("orderDate");
            //SMSUtils.sendShortMessage(telephone,orderDate);
        }

        //5.返回结果
        return result;
    }
}

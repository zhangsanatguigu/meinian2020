package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.service.HelloService;
import org.springframework.transaction.annotation.Transactional;

//@Service(loadbalance = "random")
@Service(interfaceClass = HelloService.class)
//@Service
@Transactional
public class HelloServiceImpl implements HelloService {
    @Override
    // @Transactional
    public String sayHello(String name) {
        System.out.println("this = " + this);
        System.out.println("this = " + this.getClass());
        return "8083 Hello,"+name;
    }
}

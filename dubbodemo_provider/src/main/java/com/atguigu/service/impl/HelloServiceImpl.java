package com.atguigu.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.service.HelloService;
import org.springframework.transaction.annotation.Transactional;

//在使用注解式dubbo开发的过程中，忽然发现Service上只要有@transactional注解或者是配置的事务切面时，该Service不能被dubbo发布。
//@Service(loadbalance = "random")
@Service(interfaceClass = HelloService.class)
//@Service
//@Transactional //默认，Spring容器生成JDK动态代理，增加事务切面。proxy-target-class="true"指定通过Cglib生成代理对象。
public class HelloServiceImpl implements HelloService {
    @Override
    // @Transactional
    public String sayHello(String name) {
        System.out.println("this = " + this);
        System.out.println("this = " + this.getClass());
        return "8083 Hello,"+name;
    }
}

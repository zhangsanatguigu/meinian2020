package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/demo")
public class HelloController {

    //@Autowired
    //@Reference
    //@Reference(loadbalance = "random")
    @Reference(loadbalance = "roundrobin")
    HelloService helloService;

    @ResponseBody
    @RequestMapping("/hello")
    public String sayHello(String name){
        System.out.println("helloService = " + helloService);
        System.out.println("helloService = " + helloService.getClass());
        String result = helloService.sayHello(name);
        System.out.println("result = " + result);
        return result;
    }
}

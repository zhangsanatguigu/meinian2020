package com.atguigu.service;

import com.atguigu.pojo.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserService implements UserDetailsService {

    //模拟数据库中的用户数据
    static Map<String, User> map =   new HashMap<String, User>();

    static {
        com.atguigu.pojo.User user1 =  new com.atguigu.pojo.User();
        user1.setUsername("admin");
        user1.setPassword("$2a$10$IIzBTSMW7rSSFF7fAUTjyO8CPL2CKAUHA1dOzBvKNLy1qwagDj/pu");
        user1.setTelephone("123");

        com.atguigu.pojo.User user2 =  new com.atguigu.pojo.User();
        user2.setUsername("zhangsan");
        user2.setPassword("$2a$10$yQF1gPy6EMws.PJ2rnnsaOa9l/LlrBpBTJi7k85E4oNIissAddUcu");
        user2.setTelephone("321");

        map.put(user1.getUsername(),user1);
        map.put(user2.getUsername(),user2);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("username = " + username);
        User user = map.get(username);
        if(user==null){
            return null ; //数据库无此用户，返回null即可
        }

        //String password = "{noop}"+user.getPassword();
        String password = user.getPassword();

        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
        list.add(new SimpleGrantedAuthority("add"));
        list.add(new SimpleGrantedAuthority("delete"));
        list.add(new SimpleGrantedAuthority("ROLE_ADMIN"));


        return new org.springframework.security.core.userdetails.User(username,password,list);
    }
}

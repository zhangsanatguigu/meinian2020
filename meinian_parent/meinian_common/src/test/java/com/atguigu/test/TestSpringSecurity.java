package com.atguigu.test;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TestSpringSecurity {

    // SpringSecurity加盐加密
    @Test
    public void testSpringSecurity(){
//        $2a$10$IIzBTSMW7rSSFF7fAUTjyO8CPL2CKAUHA1dOzBvKNLy1qwagDj/pu
//        $2a$10$yQF1gPy6EMws.PJ2rnnsaOa9l/LlrBpBTJi7k85E4oNIissAddUcu

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String s = encoder.encode("123");
        System.out.println(s);
        String s1 = encoder.encode("123");
        System.out.println(s1);

        // 进行判断
        boolean b = encoder.matches("123", "$2a$10$IIzBTSMW7rSSFF7fAUTjyO8CPL2CKAUHA1dOzBvKNLy1qwagDj/pu");
        System.out.println(b);
    }

}

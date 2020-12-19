package com.atguigu.service;

import com.atguigu.pojo.Member;

public interface MemberService {
    void add(Member member);

    Member findByTelephone(String telephone);
}

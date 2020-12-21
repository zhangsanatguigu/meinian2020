package com.atguigu.dao;

import com.atguigu.pojo.Member;

public interface MemberDao {
    Member findByTelephone(String telephone);

    void add(Member member);

    Integer findMemberCountByBeforeDate(String regTime);

    Integer getTodayNewMember(String today);

    Integer getTotalMember();

    Integer getThisWeekAndMonthNewMember(String date);
}

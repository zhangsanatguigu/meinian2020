package com.atguigu.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.MemberDao;
import com.atguigu.pojo.Member;
import com.atguigu.service.MemberService;
import com.atguigu.utils.DateUtils;
import com.atguigu.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberDao memberDao;

    @Override
    public void add(Member member) {
        if(member.getPassword()!=null){
            member.setPassword(MD5Utils.md5(member.getPassword()));
        }
        memberDao.add(member);
    }

    @Override
    public Member findByTelephone(String telephone) {
        return memberDao.findByTelephone(telephone);
    }

    @Override
    public List<Integer> findMemberCountByMonth(List<String> list) {
        List<Integer> memberCounts = new ArrayList<>();
        if(list!=null && list.size()>0){
            for (String month : list) {
                String regTime = DateUtils.getLastDayOfMonth(month);
                Integer count = memberDao.findMemberCountByBeforeDate(regTime);
                memberCounts.add(count);
            }
        }
        return memberCounts;
    }
}

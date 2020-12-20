package com.atguigu.dao;

import com.atguigu.pojo.Role;

import java.util.List;

public interface RoleDao {
    List<Role> findRoleByUserId(Integer userId);
}

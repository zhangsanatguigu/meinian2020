package com.atguigu.dao;

import com.atguigu.pojo.Permission;

import java.util.List;

public interface PermissionDao {
    List<Permission> findPermissionByRoleId(Integer roleId);
}

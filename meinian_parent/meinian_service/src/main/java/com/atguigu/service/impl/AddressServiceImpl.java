package com.atguigu.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.AddressDao;
import com.atguigu.entity.PageResult;
import com.atguigu.entity.QueryPageBean;
import com.atguigu.pojo.Address;
import com.atguigu.service.AddressService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = AddressService.class)
@Transactional
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressDao addressDao;

    @Override
    public List<Address> findAll() {
        return addressDao.findAll();
    }

    @Override
    public void addAddress(Address address) {
        addressDao.addAddress(address);
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        Page<Address> page = addressDao.selectByCondition(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void deleteById(Integer id) {
        addressDao.deleteById(id);
    }

}

package com.atguigu.job;

import com.atguigu.constant.RedisConstant;
import com.atguigu.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.Set;

public class ClearImgJob {

    @Autowired
    JedisPool jedisPool;

    /**
     * 清理七牛云上的图片，这些图片并不在数据库中存在
     */
    public void clearImg(){
        Set<String> sdiff = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        for (String fileName : sdiff) {
            System.out.println("fileName = " + fileName);
            QiniuUtils.deleteFileFromQiniu(fileName);
            jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES,fileName);
        }

    }
}

package com.atguigu.test;

import com.atguigu.utils.QiniuUtils;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.junit.Test;

import java.io.*;

public class TestQiniu {

    //@Test
    public  void testUpload() {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Zone.zone2());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = "6wXsRKQW6dprHho6hIOwM7oCY_MITYrhTNKuJzfi";
        String secretKey = "xEocjgAGkTts3msIWmcL16J6BfMwomfmP5c84zcX";
        String bucket = "atguigumeinian";
        //如果是Windows情况下，格式是 D:\\qiniu\\test.png
        String localFilePath = "D:\\temp\\90\\jjy94.jpg";
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
    }

    //@Test
    public void testDelete(){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Zone.zone2());
        //...其他参数参考类注释
        String accessKey = "6wXsRKQW6dprHho6hIOwM7oCY_MITYrhTNKuJzfi";
        String secretKey = "xEocjgAGkTts3msIWmcL16J6BfMwomfmP5c84zcX";
        String bucket = "atguigumeinian";
        String key = "FspfyEyKfuHZ0kcbXRIc5T9YhCax";
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.delete(bucket, key);
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
        }
    }

    //@Test
    public void test2() throws IOException {
        //QiniuUtils.upload2Qiniu("D:\\temp\\90\\jjy94.jpg","jjy94.jpg");

        //QiniuUtils.deleteFileFromQiniu("jjy94.jpg");

        File file = new File("D:\\temp\\90\\jjy94.jpg");
        //init array with file length
        byte[] bytesArray = new byte[(int) file.length()];
        FileInputStream fis = new FileInputStream(file);
        fis.read(bytesArray); //read file into bytes[]
        QiniuUtils.upload2Qiniu(bytesArray,"jjy94.jpg");
    }
}

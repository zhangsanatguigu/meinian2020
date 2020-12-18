package com.atguigu.test;

import com.atguigu.utils.SMSUtils;
import com.atguigu.utils.ValidateCodeUtils;
import org.junit.Test;

public class TestSMS {

    @Test
    public void testSMS() throws Exception {
        Integer code = ValidateCodeUtils.generateValidateCode(6);
        SMSUtils.sendShortMessage("18600025821",String.valueOf(code));
        System.out.println("===========================" );
    }
}

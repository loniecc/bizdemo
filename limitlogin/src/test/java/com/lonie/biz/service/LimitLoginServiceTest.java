package com.lonie.biz.service;

import com.lonie.biz.LimitLoginApplication;
import com.lonie.biz.common.redis.RedisStringComponent;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author huzeming Created time 2020/3/17 : 9:08 下午 Desc:
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LimitLoginApplication.class)
public class LimitLoginServiceTest {

    @Autowired
    private LimitLoginService mLimitLoginService;

    @Autowired
    private RedisStringComponent mRedisStringComponent;

    @Test
    public void testLogin() {

        for (int i = 0; i < 5; i++) {
            System.out.println(mLimitLoginService.login(1, "qwe").getResultMsg());
        }

    }

    @Test
    public void testRedisPing() {
        System.out.println(mRedisStringComponent.incr("test"));
        System.out.println(mRedisStringComponent.getValue("test"));
    }
}

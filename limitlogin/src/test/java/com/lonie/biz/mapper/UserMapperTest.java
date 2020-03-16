package com.lonie.biz.mapper;

import com.lonie.biz.bizdemo.BizdemoApplication;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author huzeming Created time 2020/3/16 : 12:24 上午 Desc:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = BizdemoApplication.class)
public class UserMapperTest {

    @Autowired
    private UserMapper mUserMapper;

    @Test
    public void testQueryAllUser() {
        System.out.println(mUserMapper.queryAllUser());
    }
}

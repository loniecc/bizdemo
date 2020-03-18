import com.lonie.biz.CommonApplication;
import com.lonie.biz.common.redis.RedisStringComponent;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author huzeming Created time 2020/3/16 : 1:22 下午 Desc:
 */



@RunWith(SpringRunner.class)
@SpringBootTest(classes = CommonApplication.class)
public class TestRedis {

    @Autowired
    private RedisStringComponent mJedisPool;

    @Autowired
    private RedisStringComponent mRedisStringComponent;


    @Test
    public void testRedisPing(){

        System.out.println(mJedisPool.getValue("test"));

    }
}

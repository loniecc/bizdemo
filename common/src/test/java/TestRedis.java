import com.lonie.biz.bizdemo.BizdemoApplication;
import com.lonie.biz.common.redis.RedisStringComponent;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import redis.clients.jedis.JedisPool;

/**
 * @author huzeming Created time 2020/3/16 : 1:22 下午 Desc:
 */



@RunWith(SpringRunner.class)
@SpringBootTest(classes = BizdemoApplication.class)
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

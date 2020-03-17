package com.lonie.biz.common.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author huzeming Created time 2020/3/16 : 1:28 下午 Desc:
 */

@Component
public class RedisCommonComponent {

    @Autowired
    private JedisPool mJedisPool;

    public void returnResource(JedisPool jedisPool, Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

    public boolean keyExist(String key) {
        Jedis jedis = null;
        boolean exist = false;

        try {
            jedis = mJedisPool.getResource();
            exist = jedis.exists(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            returnResource(mJedisPool, jedis);
        }
        return exist;
    }



    public long delKey(String key) {

        Jedis jedis = null;
        long result = 0;

        try {
            jedis = mJedisPool.getResource();
            result = jedis.del(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            returnResource(mJedisPool, jedis);
        }
        return result;
    }


}

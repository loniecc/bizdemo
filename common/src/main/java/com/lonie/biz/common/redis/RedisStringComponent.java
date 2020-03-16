package com.lonie.biz.common.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author huzeming Created time 2020/3/16 : 1:25 下午 Desc:
 */

@Component
public class RedisStringComponent {

    @Autowired
    private JedisPool mJedisPool;

    public String getValue(String key) {

        Jedis jedis = null;
        String result = "";

        try {
            jedis = mJedisPool.getResource();
            result = jedis.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            RedisCommonComponent.returnResource(mJedisPool, jedis);
        }
        return result;
    }

    public boolean setValue(String key, String value, int expireAfterSecond) {
        Jedis jedis = null;
        try {
            jedis = mJedisPool.getResource();
            String result = jedis.setex(key, expireAfterSecond, value);
            return "ok".equalsIgnoreCase(result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            RedisCommonComponent.returnResource(mJedisPool, jedis);
        }
        return false;
    }

}

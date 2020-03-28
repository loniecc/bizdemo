package com.lonie.biz.common.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.SetParams;

/**
 * @author huzeming Created time 2020/3/26 : 5:20 下午 Desc:
 */

@Component
public class RedisLockComponent {

    @Autowired
    private JedisPool mJedisPool;

    public boolean tryLock(String key, String uniqueId, int seconds) {

        Jedis jedis = null;
        try {
            jedis = mJedisPool.getResource();
            SetParams params = new SetParams();
            params.nx();
            params.ex(seconds);
            Object result = jedis.set(key, uniqueId, params);
            if ("OK".equals(result)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();

            return false;

        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return false;
    }

    public boolean releaseLock(String key, String uniqueId) {
        String luaScript = "if redis.call('get', KEYS[1]) == ARGV[1] then " +
            "return redis.call('del', KEYS[1]) else return 0 end";

        Jedis jedis = null;
        try {
            jedis = mJedisPool.getResource();
            return jedis.eval(
                luaScript,
                Collections.singletonList(key),
                Collections.singletonList(uniqueId)
            ).equals(1L);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

}

package com.lonie.biz.common.redis;

import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author huzeming Created time 2020/3/16 : 1:28 下午 Desc:
 */

@Component
public class RedisCommonComponent {

    public static void returnResource(JedisPool jedisPool, Jedis jedis) {
        if (jedis != null) {
            jedisPool.returnResource(jedis);
        }
    }

}
